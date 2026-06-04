package io.flatf.common.file.filter;

import io.flatf.common.file.FileUtil;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileFilter;

/**
 * Accepts a file based on its file extension against a list of one or more
 * acceptable file extensions.
 */
public class FileExtensionFilter implements FileFilter {

    private final boolean ignoreCase;
    private final String[] extensions;

    /**
     * Creates a new instance of a <code>FileExtensionMatcher</code> that will
     * perform a case-insensitive match to this file extension.
     *
     * @param extensions The file extension to match against a File
     * @throws IllegalArgumentException Thrown if the file extension is not
     *                                  formatted correctly such as containing a
     *                                  period.
     */
    public FileExtensionFilter(@Nonnull String... extensions) throws IllegalArgumentException {
        this(true, extensions);
    }

    /**
     * Creates a new instance of a <code>FileExtensionMatcher</code> that will
     * perform a match to this array of file extensions.
     *
     * @param ignoreCase If true the extension must match the case of the
     *                   provided extension, otherwise a case-insensitive match
     *                   will occur.
     * @param extensions The array of file extensions to match against a File
     * @throws IllegalArgumentException Thrown if the file extension is not
     *                                  formatted correctly such as containing a
     *                                  period.
     */
    public FileExtensionFilter(boolean ignoreCase, @Nonnull String... extensions) throws IllegalArgumentException {
        // check each extension
        for (String ext : extensions) {
            if (!FileUtil.isValidFileExtension(ext))
                throw new IllegalArgumentException("Invalid file extension " + ext + " cannot be matched");
        }
        this.ignoreCase = ignoreCase;
        this.extensions = extensions;
    }

    /**
     * Accepts a File by its file extension.
     *
     * @param file The file to match
     * @return True if the File matches this the array of acceptable file
     * extensions.
     */
    @Override
    public boolean accept(File file) {
        // extract this file's extension
        String fileExt = FileUtil.parseFileExtension(file.getName());

        // a file extension might not have existed
        if (fileExt == null)
            // if no file extension extracted, this definitely is not a match
            return false;

        // does it match our list of acceptable file extensions?
        for (String extension : extensions) {
            if (ignoreCase) {
                if (fileExt.equalsIgnoreCase(extension))
                    return true;
            } else {
                if (fileExt.equals(extension))
                    return true;
            }
        }

        // if we got here, then no match was found
        return false;
    }

}
