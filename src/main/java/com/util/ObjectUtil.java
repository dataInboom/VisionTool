package com.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class ObjectUtil {
	/**����תbyte[]
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static byte[] StrToBytes(String str){
			return str.getBytes(); 
	}
	/**byte[]ת����
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String bytesToObject(byte[] bytes){
		return new String(bytes);
	}
}
