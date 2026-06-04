package io.flatf.common.file;

import io.flatf.common.datetime.pattern.TemporalPattern;
import io.flatf.common.datetime.pattern.impl.DatePattern;
import io.flatf.common.lang.Validator;
import org.apache.commons.io.IOUtils;
import org.eclipse.collections.impl.list.mutable.FastList;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.flatf.common.sys.SysProperties.JAVA_IO_TMPDIR;
import static io.flatf.common.sys.SysProperties.USER_HOME;

/**
 * Utility class for handling Files.
 * <p>
 * NOTE: Some code copied from the JXL project.
 */
public final class FileUtil {

    private FileUtil() {
        // do nothing
    }

    public static boolean mkdir(File file) throws PermissionDeniedException {
        Validator.nonNull(file, "file");
        try {
            if (file.isDirectory())
                return file.mkdirs();
            else
                return file.getParentFile().mkdirs();
        } catch (Exception e) {
            throw new PermissionDeniedException(file);
        }
    }

    /**
     * @param file File
     * @return File
     */
    public static File mkdirInTmp(@Nonnull File file) throws NullPointerException {
        Validator.nonNull(file, "file");
        return mkdirInTmp(file.getPath());
    }

    /**
     * @param path String
     * @return File
     */
    public static File mkdirInTmp(@Nonnull String path) throws NullPointerException, IllegalArgumentException {
        Validator.nonEmpty(path, "path");
        File file = new File(JAVA_IO_TMPDIR, path);
        if (file.mkdirs())
            return file;
        else
            //TODO
            throw new NullPointerException("");
    }

    /**
     * @param file File
     * @return File
     */
    public static File mkdirInHome(@Nonnull File file) {
        Validator.nonNull(file, "file");
        return mkdirInHome(file.getPath());
    }

    /**
     * @param path String
     * @return File
     */
    public static File mkdirInHome(@Nonnull String path) throws RuntimeException {
        Validator.nonEmpty(path, "path");
        File file = new File(USER_HOME, path);
        if (file.mkdirs() || file.exists())
            return file;
        throw new RuntimeException(new IllegalAccessException("path -> [" + path + "] access exception"));
    }

    /**
     * Tests whether the contents of two files equal each other by performing a
     * byte-by-byte comparison. Each byte must match each other in both files.
     *
     * @param file0 The file to compare
     * @param file1 The other file to compare
     * @return True if file contents are equal, otherwise false.
     * @throws IOException Thrown if there is an underlying IO error while attempt
     *                     to compare the bytes.
     */
    public static boolean equals(File file0, File file1) throws IOException {
        // file lengths must match
        if (file0.length() != file1.length())
            return false;
        try (InputStream is0 = new FileInputStream(file0);
             InputStream is1 = new FileInputStream(file1)) {
            return equals(is0, is1);
        }
    }

    private static boolean equals(InputStream is0, InputStream is1) throws IOException {
        int size = 1024;
        byte[] buf0 = new byte[size];
        byte[] buf1 = new byte[size];

        if (is0 == is1)
            return true;
        if (is0 == null || is1 == null)
            return false;

        int read0 = -1;
        int read1 = -1;

        do {
            int offset0 = 0;
            while (offset0 < size && (read0 = is0.read(buf0, offset0, size - offset0)) >= 0)
                offset0 += read0;

            int offset1 = 0;
            while (offset1 < size && (read1 = is1.read(buf1, offset1, size - offset1)) >= 0)
                offset1 += read1;

            if (offset0 != offset1)
                return false;

            if (offset0 != size) {
                Arrays.fill(buf0, offset0, size, (byte) 0);
                Arrays.fill(buf1, offset1, size, (byte) 0);
            }

            if (!Arrays.equals(buf0, buf1))
                return false;

        } while (read0 >= 0 && read1 >= 0);
        // both at EOF
        return true;
    }

    /**
     * Checks if the extension is valid. This method only permits letters, digits,
     * and an underscore character.
     *
     * @param extension The file extension to validate
     * @return True if it's valid, otherwise false
     */
    public static boolean isValidFileExtension(String extension) {
        for (int i = 0; i < extension.length(); i++) {
            char c = extension.charAt(i);
            if (!(Character.isDigit(c) || Character.isLetter(c) || c == '_'))
                return false;
        }
        return true;
    }

    /**
     * Parse the filename and return the file extension. For example, if the file is
     * "app.2006-10-10.log", then this method will return "log". Will only return
     * the last file extension. For example, if the filename ends with ".log.gz",
     * then this method will return "gz"
     *
     * @param filename String to a process containing the filename
     * @return The file extension (without a leading period) such as "gz" or "txt" or
     * null if none exists.
     */
    public static String parseFileExtension(String filename) {
        // if null, return null
        if (filename == null)
            return null;

        // find position of the last period
        int pos = filename.lastIndexOf('.');

        // did one exist or have any length?
        if (pos < 0 || (pos + 1) >= filename.length())
            return null;

        // parse extension
        return filename.substring(pos + 1);
    }

    /**
     * Finds all files (non-recursively) in a directory based on the FileFilter.
     * This method is slightly different from the JDK File.listFiles() version since
     * it throws an error if the directory does not exist or is not a directory.
     * Also, this method only finds files and will skip including directories.
     *
     * @param dir    File
     * @param filter FileFilter
     * @return File[]
     * @throws FileNotFoundException e
     */
    public static File[] findFiles(File dir, FileFilter filter) throws FileNotFoundException {
        if (!dir.exists())
            throw new FileNotFoundException("Directory [" + dir + "] does not exist.");
        if (!dir.isDirectory())
            throw new FileNotFoundException("File [" + dir + "] is not a directory.");

        // being matching process, create an array for returning results
        List<File> files = new FastList<>();

        // get all files in this directory
        File[] dirFiles = dir.listFiles();

        // were any files returned?
        if (dirFiles != null) {
            // loop through every file in the dir
            for (File file : dirFiles) {
                // only match files, not a directory
                if (file.isFile() && filter.accept(file)) {
                    // delegate matching to provided file matcher
                    files.add(file);
                }
            }
        }

        // based on filesystem, order of files not guaranteed -- sort now
        File[] r = files.toArray(new File[0]);
        Arrays.sort(r);
        return r;
    }

    /**
     * Get all the files (not dirs) under <CODE>dir</CODE>
     *
     * @param dir Directory to search.
     * @return all the files under <CODE>dir</CODE>
     */
    public static Set<File> getRecursiveFiles(File dir) {
        if
        (!dir.isDirectory()) {
            HashSet<File> one = new HashSet<>();
            one.add(dir);
            return one;
        } else {
            return recurseDir(dir);
        }
    }

    private static Set<File> recurseDir(File dir) {
        HashSet<File> c = new HashSet<>();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    c.addAll(recurseDir(file));
                } else {
                    c.add(file);
                }
            }
        }
        return c;
    }


    /*
     * public static boolean rmdir(File dir, boolean recursive) throws IOException {
     * // make sure this is a directory if (!dir.isDirectory()) { throw new
     * IOException("File " + dir + " is not a directory"); }
     *
     * File[] files = dir.listFiles();
     *
     * // are there files? if (files != null && files.length > 0 && !recursive) {
     * throw new IOException("Directory " + dir + " is not empty, cannot be
     * deleted"); }
     *
     * for (File file : files) { if (file.isDirectory()) { rmdir(file); } else {
     * file.delete(); } }
     *
     * // finally remove this directory return dir.delete(); }
     *
     * private static boolean rmdir(File dir) throws IOException { // make sure this
     * is a directory if (!dir.isDirectory()) { throw new IOException("File " + dir
     * + " is not a directory"); }
     *
     * File[] files = dir.listFiles();
     *
     * // are there files? if (files != null && files.length > 0 && !recursive) {
     * throw new IOException("Directory " + dir + " is not empty, cannot be
     * deleted"); }
     *
     * boolean success = true;
     *
     * for (File file : files) { if (file.isDirectory()) { success |= rmdir(file); }
     * else { success |= file.delete(); } }
     *
     * // was the recursive part okay?
     *
     * // finally remove this directory return dir.delete(); }
     */

    /*
     * Copy dest.length bytes from the inputStream into the dest bytearray.
     *
     * @param is
     * @param dest
     * @throws IOException
     */
    /*
     * public static void copy(InputStream is, byte[] dest) throws IOException { int
     * len = dest.length; int ofs = 0; while (len > 0) { int size = is.read(dest,
     * ofs, len); ofs += size; len -= size; } }
     */

    /**
     * Copy the source file to the target file.
     *
     * @param source The source file to copy from
     * @param target The target file to copy to
     * @throws FileAlreadyExistsException Thrown if the target file already exists.
     *                                    This exception is a subclass of
     *                                    IOException, so catching an IOException is
     *                                    enough if you don't care about this
     *                                    specific reason.
     * @throws IOException                Thrown if an error during the copy
     */
    public static void copy(File source, File target) throws IOException {
        copy(source, target, false);
    }

    /**
     * Copy the source file to the target file while optionally permitting an
     * overwriting to occur in case the target file already exists.
     *
     * @param source The source file to copy from
     * @param target The target file to copy to
     *               //@return True if an overwriting occurred, otherwise false.
     * @throws FileAlreadyExistsException Thrown if the target file already exists
     *                                    and an overwriting is not permitted. This
     *                                    exception is a subclass of IOException, so
     *                                    catching an IOException is enough if you
     *                                    don't care about this specific reason.
     * @throws IOException                Thrown if an error during the copy
     */
    public static void copy(File source, File target, boolean overwrite)
            throws IOException {
        // check if the target file already exists
        if (target.exists() && !overwrite) {
            // if overwrite is not allowed, throw an exception
            throw new FileAlreadyExistsException("Target file : [" + target.getName() + "] already exists");
        }
        // proceed with copy
        try (final FileInputStream fis = new FileInputStream(source);
             final FileOutputStream fos = new FileOutputStream(target)) {
            fis.getChannel().transferTo(0, source.length(), fos.getChannel());
            fos.flush();
        }
    }

    /**
     * @param sources Set<File>
     * @param dir     File
     * @throws IOException ioe
     */
    public static void copy(@Nonnull Set<File> sources, File dir) throws IOException {
        Validator.nonNull(sources, "sources");
        for (File source : sources) {
            File target = new File(dir, source.getName());
            copy(source, target);
        }
    }

    /**
     * Copy the contents of is to os.
     *
     * @param is    InputStream
     * @param os    OutputStream
     * @param buf   Can be null
     * @param close If true, is closed after the copy.
     * @throws IOException ioe
     */
    public static void copy(InputStream is, OutputStream os, byte[] buf, boolean close) throws IOException {
        int len;
        if (buf == null)
            buf = new byte[4096];

        while ((len = is.read(buf)) > 0)
            os.write(buf, 0, len);

        os.flush();
        if (close)
            IOUtils.close(is);
    }

    /**
     * @param data byte[]
     * @param file File
     * @throws IOException ioe
     */
    public static void flush(byte[] data, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
            fos.flush();
        }
    }

    /**
     * Read <CODE>f</CODE> and return as byte[]
     *
     * @param file File
     * @return bytes from <CODE>f</CODE>
     * @throws IOException ioe
     */
    public static byte[] load(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return load(fis, false);
        }
    }

    /**
     * Copy the contents of is to the returned byte array.
     *
     * @param is    InputStream
     * @param close If true, InputStream is closed after the copy.
     * @throws IOException ioe
     */
    public static byte[] load(InputStream is, boolean close) throws IOException {
        try (final ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            copy(is, os, null, close);
            return os.toByteArray();
        }
    }

    /**
     * Class to compare Files by their embedded DateTimes.
     */
    public static class FileNameDateTimeComparator implements Comparator<File> {

        private final TemporalPattern<?> pattern;
        private final ZoneId zoneId;

        /**
         * Creates a default instance where the pattern is "yyyy-MM-dd" and the default
         * timezone of UTC.
         */
        public FileNameDateTimeComparator() {
            this(null, null);
        }

        public FileNameDateTimeComparator(TemporalPattern<?> pattern, ZoneId zoneId) {
            this.pattern = (pattern == null) ? DatePattern.YYYY_MM_DD : pattern;

            if (zoneId == null)
                this.zoneId = ZoneOffset.UTC;
            else
                this.zoneId = zoneId;
        }

        public int compare(File file0, File file1) {
            // extract datetime from both files
            ZonedDateTime zdt0 = ZonedDateTime.of(LocalDateTime.parse(file0.getName(), pattern.getFormatter()), zoneId);
            ZonedDateTime zdt1 = ZonedDateTime.of(LocalDateTime.parse(file1.getName(), pattern.getFormatter()), zoneId);
            // compare these two
            return zdt0.compareTo(zdt1);
        }
    }

}
