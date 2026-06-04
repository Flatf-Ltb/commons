package io.flatf.common.character;

import java.io.File;
import java.nio.file.FileSystems;

public interface Separator {

    /**
     * 行分割符
     */
    String LINE_SEPARATOR = System.lineSeparator();

    /**
     * 文件分割符
     */
    String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();

    /**
     * 路径分割符
     */
    String PATH_SEPARATOR = File.pathSeparator;

    /**
     * String = '-';
     */
    String LINE = "-";

    /**
     * String = ' ';
     */
    String BLANK = " ";

}
