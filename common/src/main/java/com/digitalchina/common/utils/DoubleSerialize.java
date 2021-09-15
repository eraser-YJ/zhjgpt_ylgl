package com.digitalchina.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * <Double类型转换(自带两位小数)>
 * 
 * @author lihui
 * @since 2019年11月8日
 */
public class DoubleSerialize extends JsonSerializer<Double> {
	private DecimalFormat df = new DecimalFormat("##0.00");

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		if (value != null) {
			gen.writeString(df.format(value));
		}
	}
}
