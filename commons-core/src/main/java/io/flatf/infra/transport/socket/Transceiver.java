package io.flatf.infra.transport.socket;

import io.flatf.infra.transport.api.Receiver;
import io.flatf.infra.transport.api.Sender;

public interface Transceiver<T> extends Receiver {

	Sender<T> getSender();

	boolean startSend();

}
