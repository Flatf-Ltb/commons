package io.flatf.common.file;

import io.flatf.common.lang.Validator;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import io.flatf.common.serialization.specific.BytesSerializer;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.flatf.common.character.Charsets.UTF8;
import static io.flatf.common.character.Separator.LINE_SEPARATOR;
import static io.flatf.common.sys.SysProperties.JAVA_IO_TMPDIR_FILE;

/**
 * Use FileChannel
 *
 * @author yellow013
 */
public final class FileChannelWriter {

    private static final Logger log = Log4j2LoggerFactory.getLogger(FileChannelWriter.class);

    private FileChannelWriter() {
    }

    /**
     * @param lines  : written data
     * @param target : written target file
     * @return File
     * @throws NullPointerException npe
     * @throws IOException          ioe
     */
    public static File write(Collection<String> lines, @Nonnull final File target)
            throws NullPointerException, IOException {
        return write(lines, UTF8, target, 8192, true);
    }

    /**
     * @param lines  : written data
     * @param target : written target file
     * @param append : append the file end
     * @return File
     * @throws NullPointerException npe
     * @throws IOException          ioe
     */
    public static File write(Collection<String> lines, @Nonnull File target, boolean append)
            throws NullPointerException, IOException {
        return write(lines, UTF8, target, 8192, append);
    }

    /**
     * @param lines    : Written data
     * @param charset  : Value charset
     * @param target   : Written target file
     * @param capacity : Buffer capacity
     * @param append   : Is append to the file end
     * @return File
     * @throws NullPointerException npe
     * @throws IOException          ioe
     */
    public static File write(Collection<String> lines, @Nonnull Charset charset, @Nonnull File target,
                             int capacity, boolean append) throws NullPointerException, IOException {
        return write(lines,
                line -> line.endsWith(LINE_SEPARATOR) ? line.getBytes(charset)
                        : (line + LINE_SEPARATOR).getBytes(charset),
                target, capacity, append);
    }

    /**
     * @param <T>        T type
     * @param data       : Written data
     * @param serializer : Serialization function
     * @param target     : Written target file
     * @param capacity   : Buffer capacity
     * @param append     : Is append to the file end
     * @return File
     * @throws NullPointerException npe
     * @throws IOException          ioe
     */
    public static <T> File write(Collection<T> data, @Nonnull BytesSerializer<T> serializer,
                                 File target, int capacity, boolean append)
            throws NullPointerException, IOException {
        Validator.nonNull(target, "target");
        if (capacity < 128)
            capacity = 4096;
        File parentFile = target.getParentFile();
        if (!parentFile.exists()) {
            boolean succeed = parentFile.mkdirs();
            log.debug("mkdir -> {} is {}", parentFile, succeed);
        }
        if (!target.exists()) {
            boolean succeed = target.createNewFile();
            log.debug("create File -> {} is {}", parentFile, succeed);
        }
        if (CollectionUtils.isNotEmpty(data)) {
            try (RandomAccessFile raFile = new RandomAccessFile(target, "rw")) {
                if (append) {
                    // Seek to end
                    raFile.seek(raFile.length());
                }
                try (FileChannel channel = raFile.getChannel()) {
                    // Allocate [capacity] direct buffer
                    ByteBuffer buffer = ByteBuffer.allocateDirect(capacity);
                    // for (int i = 0; i < data.size(); i++) {
                    for (T t : data) {
                        byte[] bytes = serializer.serialize(t);
                        if (bytes == null || bytes.length == 0) {
                            continue;
                        }
                        if (bytes.length < capacity) {
                            // [bytes#length] < [capacity], Only need to write once
                            flipAndChannelWrite(buffer.put(bytes), channel);
                        } else {
                            // Count writes
                            int count = bytes.length / capacity;
                            int offset = 0;
                            for (int r = 0; r < count; r++) {
                                // Write from the last [offset], Write length is buffer [capacity]
                                flipAndChannelWrite(buffer.put(bytes, offset = r * capacity, capacity), channel);
                            }
                            // Remaining data
                            int remaining = bytes.length % capacity;
                            if (remaining > 0) {
                                // Write from the last [offset], Write length is remaining
                                flipAndChannelWrite(buffer.put(bytes, offset += capacity, remaining), channel);
                            }
                        }
                    }
                    channel.force(true);
                }
            }
        }
        return target;
    }

    /**
     * @param buffer  ByteBuffer
     * @param channel FileChannel
     * @throws IOException ioe
     */
    private static void flipAndChannelWrite(ByteBuffer buffer, FileChannel channel) throws IOException {
        // Flip buffer to output
        buffer.flip();
        // Loop write
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        // Clear buffer to input
        buffer.clear();
    }

    public static void main(String[] args) {

        long nanoTime0 = System.nanoTime();

        List<String> lines = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < 100; j++) {
                builder.append(j);
                builder.append(',');
            }
            builder.append(LINE_SEPARATOR);
            lines.add(builder.toString());
        }

        try {
            write(lines, UTF8, new File(JAVA_IO_TMPDIR_FILE, "test.csv"), 2048, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long nanoTime1 = System.nanoTime();

        System.out.println((nanoTime1 - nanoTime0) / 1000);

    }

}
