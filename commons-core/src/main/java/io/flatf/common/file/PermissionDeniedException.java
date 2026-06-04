package io.flatf.common.file;

import java.io.File;
import java.io.Serial;

public class PermissionDeniedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4465231636091844730L;

    public PermissionDeniedException(File file) {
        this(file.getAbsolutePath());
    }

    public PermissionDeniedException(String filepath) {
        super("The current user does not have permission to access -> " + filepath);
    }

}
