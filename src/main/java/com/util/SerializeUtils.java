package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtils {
	private static final Logger log = LoggerFactory.getLogger(SerializeUtils.class);

	/**
	 * 序列化
	 * */
	public static byte[] serialize(Object object) {
	    ObjectOutputStream oos = null;
	    ByteArrayOutputStream baos = null;
	    try {
	        // 序列化
	        baos = new ByteArrayOutputStream();
	        oos = new ObjectOutputStream(baos);
	        oos.writeObject(object);
	        byte[] bytes = baos.toByteArray();
	        return bytes;
	    } catch (Exception e) {
			log.error("SerializeUtils serialize error: {}", e.getStackTrace());
	    }
	    return null;
	}
	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
	    ByteArrayInputStream bais = null;
	    try {
	        // 反序列化
	        bais = new ByteArrayInputStream(bytes);
	        ObjectInputStream ois = new ObjectInputStream(bais);
	        return  ois.readObject();
	    } catch (Exception e) {
			log.error("SerializeUtils unserialize error: {}", e.getStackTrace());

	    }
	    return null;
	}
}
