package io.flatf.common.config;

import static io.flatf.common.lang.ThrowsUtil.throwsUnsupportedOperation;

public final class ConfigStorage {

    private ConfigStorage() {
        throwsUnsupportedOperation("[ConfigStorage] cannot be instantiated");
    }

}
