package io.flatf.infra.transport;

import io.flatf.common.config.Configurator;

public interface TransportConfig extends Configurator {

	String connectionInfo();

}
