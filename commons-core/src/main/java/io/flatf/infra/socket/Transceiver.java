package io.flatf.infra.socket;

import io.flatf.infra.api.Receiver;
import io.flatf.infra.api.Sender;

public interface Transceiver<T> extends Receiver {

	Sender<T> getSender();

	boolean startSend();

}
