package com.hw.tobcore.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtil {
    /**
     * 文件转化为字节数组
     * @EditTime 2007-8-13 上午11:45:28
     */
    public static byte[] getBytesFromFile(String  fileName) throws IOException{

        File f = new File(fileName);
        FileInputStream stream = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = stream.read(b)) != -1) {
            out.write(b, 0, n);
        }
        stream.close();
        out.close();
        return out.toByteArray();

    }

    /**
     * 把字节数组保存为一个文件
     * @EditTime 2007-8-13 上午11:45:56
     */
    public static File getFileFromBytes(byte[] b, String outputFile)throws IOException {
        BufferedOutputStream stream = null;
        File file = null;
        file = new File(outputFile);
        FileOutputStream fstream = new FileOutputStream(file);
        stream = new BufferedOutputStream(fstream);
        stream.write(b);
        if(stream != null) {
            stream.close();
        }

        return file;
    }

    /**
     * 从字节数组获取对象
     * @EditTime 2007-8-13 上午11:46:34
     */
    public static Object getObjectFromBytes(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }

    /**
     * 从对象获取一个字节数组
     * @EditTime 2007-8-13 上午11:46:56
     */
    public static byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }

    /**
     * 对象存储到文件
     */

    public static File saveObjectToFile(Object object, String outputFile) throws IOException{
        File file = new File(outputFile);
        FileOutputStream fstream = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fstream);
        oos.writeObject(object);
        oos.flush();
        oos.close();

        return file;
    }

    public static Object getObjectFromFile(String filename)throws IOException,ClassNotFoundException{

        FileInputStream inputStream = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        Object o = ois.readObject();

        return  o;
    }

    public static void main(String[] args) throws Exception {

        List<String> testList = new ArrayList<String>();
        testList.add("a");
        testList.add("b");

        SerializeUtil.saveObjectToFile(testList, "./test.data");
        Object o = SerializeUtil.getObjectFromFile("./test.data");
        List list = (List)o;
        for(int i=0;i<list.size();i++) {
            System.out.println(list.get(i));
        }

    }
}
