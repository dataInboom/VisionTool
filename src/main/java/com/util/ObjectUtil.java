package com.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class ObjectUtil {
	/**对象转byte[]
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static byte[] StrToBytes(String str){
			return str.getBytes(); 
	}
	/**byte[]转对象
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String bytesToObject(byte[] bytes){
		return new String(bytes);
	}
}
