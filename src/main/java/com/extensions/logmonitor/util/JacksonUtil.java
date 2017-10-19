package com.extensions.logmonitor.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * @author caoliang
 * @date 2017年2月22日
 */
public class JacksonUtil {
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static final String obj2Str(Object o) {
        try {
        	if(o == null){
        		return null;
        	}
            return mapper.writeValueAsString(o);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final void writeObj(OutputStream out, Object value) throws JsonGenerationException,
            JsonMappingException, IOException {
        mapper.writeValue(out, value);
    }

    public static final <T> T str2Obj(String s, Class<T> valueType) throws JsonParseException, JsonMappingException,
            IOException {
        JavaType javaType = getJavaType(valueType, null);
        return mapper.readValue(s, javaType);
    }

    @SuppressWarnings("deprecation")
	protected static JavaType getJavaType(Type type, Class<?> contextClass) {
        return (contextClass != null) ? mapper.getTypeFactory().constructType(type, contextClass) : mapper
                .constructType(type);
    }

    public static final <T> T str2Obj(String s, TypeReference<T> valueType) throws JsonParseException,
            JsonMappingException, IOException {
        return mapper.readValue(s, valueType);
    }

    public static final <T> List<T> str2List(String s, Class<T> valueType) throws JsonParseException,
            JsonMappingException, IOException {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, valueType);
        return mapper.readValue(s, javaType);
    }

    public static final <T> T readObj(InputStream in, Class<T> valueType) throws JsonParseException,
            JsonMappingException, IOException {
        return mapper.readValue(in, valueType);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T readObj(InputStream in, JavaType valueType) throws JsonParseException,
            JsonMappingException, IOException {
        return (T) mapper.readValue(in, valueType);
    }

}
