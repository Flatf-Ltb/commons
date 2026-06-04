package io.flatf.common.codec;

/**
 * 用于标识信息的识别信息
 *
 * @author yellow013
 */
public interface Envelope {

    int getCode();

    int getVersion();

}