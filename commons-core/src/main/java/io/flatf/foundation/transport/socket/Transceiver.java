package io.flatf.foundation.transport.socket;

import io.flatf.foundation.transport.api.Receiver;
import io.flatf.foundation.transport.api.Sender;

public interface Transceiver<T> extends Receiver {

	Sender<T> getSender();

	boolean startSend();

}
