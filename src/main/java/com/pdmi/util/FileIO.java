package com.pdmi.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
	
	public static void main(String[] args){
		List<String> strs = FileIO.readLine("test321.txt");
		for(String str : strs){
			System.out.println(str.equals("BJ"));
		}
	}

	public static List<String> readLine(String fileName) {
		List<String> line = new ArrayList<String>();

		FileReader reader;
		try {
			reader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(reader);

			String str = null;

			while ((str = br.readLine()) != null) {
				line.add(str);
			}

			reader.close();
			return line;
		} catch (IOException e) {
			return null;
		}
	}

	public static String read(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			Long filelength = file.length();
			byte[] filecontent = new byte[filelength.intValue()];
			try {
				FileInputStream in = new FileInputStream(file);
				in.read(filecontent);
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new String(filecontent);
		} else {
			return null;
		}
	}

	public static void writeLine(String fileName, List<String> strs) {
		File file = new File(fileName);
		FileWriter fw = null;
		BufferedWriter writer = null;
		try {
			fw = new FileWriter(file);
			writer = new BufferedWriter(fw);
			for (int i = 0; i < strs.size(); i++) {
				String str = strs.get(i);
				writer.write(str);
				if (i != strs.size() - 1)
					writer.newLine();
			}
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void writeLine(String fileName, String str) {
		try {  
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件  
            FileWriter writer = new FileWriter(fileName, true);  
            writer.write(str);  
            writer.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}

	public static void write(String fileName, String str) {
		File file = new File(fileName);
		FileWriter fw = null;
		BufferedWriter writer = null;
		try {
			fw = new FileWriter(file);
			writer = new BufferedWriter(fw);
			writer.write(str);
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void clearFile(String fileName) {
		File file = new File(fileName);
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write("");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
