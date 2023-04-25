package com.pdmi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ArrayUitl {
	public static void intDataToFileOut(int[][] sparseArray, File file) {
        FileWriter out = null;
        try {
            out = new FileWriter(file);
            //二维数组按行存入到文件中
            for (int i = 0; i < sparseArray.length; i++) {
                for (int j = 0; j < sparseArray.length; j++) {
                    //将每个元素转换为字符串
                    String content = String.valueOf(sparseArray[i][j]) + "";
                    out.write(content + "\t");
                }
                out.write("\r\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public static int[][] getSparseArrayFromFile(String path){
        //将稀疏矩阵从文件中读取出来
        BufferedReader bufferedReader = null;
        //为保存的数组分配空间
        int[][] datas=new int[1024][1903];
        try {

            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(path)));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            int i=0;
            //按行读取
            while((line = bufferedReader.readLine() )!= null){
                if(null != line){
                    //将按行读取的字符串按空格分割，得到一个string数组
                    String[] strings = line.split("\\t");
                    //依次转换为int类型存入到分配好空间的数组中
                    for(int k = 0;k<strings.length;k++){
                        datas[i][k] = Integer.valueOf(strings[k]);
                    }
                    //行数加1
                    i++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回读取的二维数组
        return datas;    
}
}
