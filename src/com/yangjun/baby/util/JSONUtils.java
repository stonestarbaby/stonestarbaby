package com.yangjun.baby.util;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

public class JSONUtils {
	private static  ObjectMapper mapper=new ObjectMapper();
	/**
     * 根据json串和节点名返回节点
     * 
     * @param json
     * @param nodeName
     * @return
     */
    public static JsonNode getNode(String json, String nodeName) {
        JsonNode node = null;
        try {
            node =mapper.readTree(json);
            return node.get(nodeName);
        } catch (JsonProcessingException e) {
        } catch (IOException e) {
        }
        return node;
    }
 
    /**
     * JsonNode转换为Java泛型对象，可以是各种类型，此方法最为强大。用法看测试用例。
     * 
     * @param <T>
     * @param node JsonNode
     * @param tr TypeReference,例如: new TypeReference< List<FamousUser> >(){}
     * @return List对象列表
     */
    public static <T> T jsonNode2GenericObject(JsonNode node, TypeReference<T> tr) {
        if (node == null || "".equals(node)) {
            return null;
        } else {
            try {
                return (T) mapper.readValue(node, tr);
            } catch (Exception e) {
            }
        }
        return null;
    }
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
    	return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses); 
    } 
}
