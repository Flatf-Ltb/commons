package io.flatf.common.param;

import io.flatf.common.param.Params.ValueType;

/**
 * Inner Key type
 */
public interface JointKey extends ParamKey {

    int key0();

    int key1();

    ValueType getValueType();

}