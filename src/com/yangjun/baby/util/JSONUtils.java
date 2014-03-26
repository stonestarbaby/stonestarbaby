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
     * ����json���ͽڵ������ؽڵ�
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
     * JsonNodeת��ΪJava���Ͷ��󣬿����Ǹ������ͣ��˷�����Ϊǿ���÷�������������
     * 
     * @param <T>
     * @param node JsonNode
     * @param tr TypeReference,����: new TypeReference< List<FamousUser> >(){}
     * @return List�����б�
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
