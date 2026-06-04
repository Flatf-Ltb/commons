package io.flatf.common.param;

import io.flatf.common.param.Params.ValueType;

/**
 * 
 * @author yellow013
 */
public interface ParamKey {

	int getParamId();

	String getParamName();

	ValueType getValueType();

}
