package com.digitalchina.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * <Double类型转换(转成百分比后带两位小数)>
 * 
 * @author lihui
 * @since 2019年11月8日
 */
public class DoubleSerialize2 extends JsonSerializer<Double> {

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		if (value != null) {
			gen.writeString(String.format("%.2f%%", value * 100));
		}
	}
}
