package io.flatf.common.sys;

import java.io.File;

public enum SysDirectory {

    IO_TMPDIR(SysProperties.JAVA_IO_TMPDIR),

    USER_HOME(SysProperties.USER_HOME),

    USER_DIR(SysProperties.USER_DIR),

    ;

    private final String path;
    private final File file;

    SysDirectory(String path) {
        this.path = path;
        this.file = new File(path);
    }

    public String path() {
        return path;
    }

    public File file() {
        return file;
    }

}
