package io.flatf.infra.serialization;

import io.flatf.common.epoch.EpochUnit;

public abstract class BinaryMsg<T extends BinaryMsg<T>> {

    protected long sequence;
    protected long epoch;
    protected EpochUnit epochUnit = EpochUnit.MILLIS;
    protected int envelope;
    protected int version = 1;
    protected ContentType contentType;
    protected byte[] content;

    protected abstract T returnThis();

    public long getSequence() {
        return sequence;
    }

    public T setSequence(long sequence) {
        this.sequence = sequence;
        return returnThis();
    }

    public long getEpoch() {
        return epoch;
    }

    public T setEpoch(long epoch) {
        this.epoch = epoch;
        return returnThis();
    }

    public EpochUnit getEpochUnit() {
        return epochUnit;
    }

    public T setEpochUnit(EpochUnit epochUnit) {
        this.epochUnit = epochUnit;
        return returnThis();
    }

    public int getEnvelope() {
        return envelope;
    }

    public T setEnvelope(int envelope) {
        this.envelope = envelope;
        return returnThis();
    }

    public int getVersion() {
        return version;
    }

    public T setVersion(int version) {
        this.version = version;
        return returnThis();
    }

    public ContentType getContentType() {
        return contentType;
    }

    public T setContentType(ContentType contentType) {
        this.contentType = contentType;
        return returnThis();
    }

    public byte[] getContent() {
        return content;
    }

    public T setContent(byte[] content) {
        this.content = content;
        return returnThis();
    }

}
