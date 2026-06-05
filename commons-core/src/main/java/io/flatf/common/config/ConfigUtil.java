package io.flatf.common.config;

import javax.annotation.Nonnull;
import java.io.File;

import static io.flatf.common.sys.SysProperties.USER_DIR_FILE;
import static io.flatf.common.sys.SysProperties.USER_HOME_CONFIG_FOLDER;
import static io.flatf.common.sys.SysProperties.USER_HOME_FILE;

/**
 * @author yellow013
 */
public final class ConfigUtil {

    private ConfigUtil() {
    }

    public static File findConfigFileAtHome(@Nonnull final String filename) {
        File file = new File(filename);
        if (file.isAbsolute() && file.isFile()) {
            return file;
        }
        File[] candidates = {
                new File(USER_HOME_CONFIG_FOLDER, filename),
                new File(USER_HOME_FILE, filename),
                new File(USER_DIR_FILE, filename)
        };
        for (File candidate : candidates) {
            if (candidate.isFile()) {
                return candidate;
            }
        }
        return null;
    }

}
