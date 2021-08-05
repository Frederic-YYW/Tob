package com.hw.tobcore.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.*;

public class FileUtil {
    private static Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    public static boolean fileExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    public static void appendFile(String filename, String line) {
        try {
            FileWriter dstWriter = new FileWriter(filename, true);
            dstWriter.write(line);
            dstWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to append file : " + filename);
            e.printStackTrace();
        }
    }

    public static void write(String filename, String line, boolean isAppend) {
        try {
            FileWriter dstWriter = new FileWriter(filename, isAppend);
            dstWriter.write(line);
            dstWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to append file : " + filename);
            e.printStackTrace();
        }
    }

    public static void write(String filename, byte[] bytes) throws IOException {

        File file = new File(filename);
        if (!file.exists()) {
        }else {
            delFile(filename);
        }
        file.createNewFile();
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(bytes);
        fop.flush();
        fop.close();
    }

    /**
     * 文件转化为字节数组
     *
     * @EditTime 2007-8-13 上午11:45:28
     */
    public static byte[] read(String fileName) throws IOException {

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

    public static long appendRandomFile(String filename, byte[] bytes) throws FileNotFoundException, IOException {
        long fileLen = 0;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(filename, "rw");
            fileLen = randomAccessFile.length();
            randomAccessFile.seek(fileLen);
            randomAccessFile.write(bytes);
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            System.out.print("File : " + filename + " not found.");
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            System.out.print("File : " + filename + " io error.");
            e.printStackTrace();
            throw e;
        }

        return fileLen;
    }


    /**
     * 创建文件
     *
     * @param pathStr  路径
     * @param fileName 文件名字
     * @return File实例
     */
    public static File createFile(String pathStr, String fileName) {
        File path = new File(pathStr);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(pathStr + "/" + fileName);
        if (file.exists()) {
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex != -1) {
                file = new File(pathStr + "/" + fileName.substring(0, dotIndex) + System.currentTimeMillis()
                        + fileName.substring(dotIndex, fileName.length()));
            } else {
                file = new File(pathStr + "/" + fileName + System.currentTimeMillis());
            }
        }
        return file;
    }

    /**
     * 创建目录
     * 支持创建多级目录
     *
     * @param folderPath
     */
    public static void newFolder(String folderPath) {
        try {
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
            }
        } catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }


    /**
     * 删除文件夹
     *
     * @return boolean
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹

        } catch (Exception e) {
            System.out.println("删除文件夹操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path String 文件夹路径 如 c:/fqf
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
            }
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean flag = false;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                flag = true;
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
        return flag;

    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        boolean flag = false;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
            flag = true;
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }
        return flag;
    }


    /**
     * 复制整个文件夹内容
     *
     * @param oldPath   String 原文件路径 如：c:/fqf
     * @param newPath   String 复制后路径 如：f:/fqf/ff
     * @param filterDir String    过滤目录名称
     * @return boolean
     */
    public static boolean copyFolder(String oldPath, String newPath, String filterDir) {
        boolean flag = false;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    if (!file[i].equals(filterDir)) {
                        copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i], filterDir);
                    }
                }
            }
            flag = true;
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }
        return flag;
    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath String 如：c:/fqf.txt
     * @param newPath String 如：d:/fqf.txt
     */
    public static boolean moveFile(String oldPath, String newPath) {
        boolean flag = copyFile(oldPath, newPath);
        if (flag) {
            delFile(oldPath);
        }
        return flag;

    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath String 如：c:/fqf.txt
     * @param newPath String 如：d:/fqf.txt
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath, null);
        delFolder(oldPath);

    }


    public static void delFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else {
                File[] files = file.listFiles();
                for (File f : files) {
                    delFile(f);
                }
                file.delete();
            }
        }
    }

    public static void delFile(String path) {
        delFile(new File(path));
    }

    /**
     * copy 文件
     *
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }


    /**
     * @return
     * @throws Exception
     */
    public static File getFile(String fileName, String propertyName) throws Exception {
        String filePath = null;
        if (propertyName != null && !"".equals(propertyName)) {
            filePath = System.getProperty(propertyName);
        }
        File file = null;

        if (filePath == null || "".equals(filePath)) {
            URL url = FileUtil.class.getClassLoader().getResource(propertyName + fileName);
            if (url == null) {
                LOG.error("failed to find file: " + propertyName + fileName);
                throw new FileNotFoundException(fileName + " not found!");
            }
            System.out.println("url: " + url.getPath());
            file = new File(url.getPath());
        } else {
            filePath = filePath.endsWith("/") ? filePath.concat(fileName)
                    : filePath.concat("/").concat(fileName);
            file = new File(filePath);
        }
        return file;
    }


    public static String getFilePath(String fileName, String propertyName) throws Exception {
        String filePath = null;
        if (propertyName != null && !"".equals(propertyName)) {
            filePath = System.getProperty(propertyName);
        }

        if (filePath == null || "".equals(filePath)) {

            URL url = FileUtil.class.getClassLoader().getResource(propertyName + fileName);
            if (url == null) {
                throw new FileNotFoundException(fileName + " not found!");
            }
            filePath = url.getPath();
        } else {
            filePath = filePath.endsWith("/") ? filePath.concat(fileName)
                    : filePath.concat("/").concat(fileName);
        }
        return filePath;
    }

    public static String getFileDir(String fileName, String propertyName) throws Exception {
        String filePath = null;
        if (propertyName != null && !"".equals(propertyName)) {
            filePath = System.getProperty(propertyName);
        }

        if (filePath == null || "".equals(filePath)) {

            URL url = FileUtil.class.getClassLoader().getResource(propertyName + fileName);
            if (url == null) {
                throw new FileNotFoundException(fileName + " not found!");
            }
            filePath = url.getPath();
            filePath = filePath.replace(fileName, "");
        } else {
            filePath = filePath.endsWith("/") ? filePath.concat(fileName)
                    : filePath.concat("/").concat(fileName);
        }
        return filePath;
    }

    /**
     * @param file
     */
    public static String read(File file, String charset) {
        final byte[] content = read(file);
        return content == null ? "" : new String(content);
    }

    public static byte[] read(File file) {
        if (!(file.exists() && file.isFile())) {
            throw new IllegalArgumentException("The remote not exist or not a remote");
        }
        FileInputStream fis = null;
        byte[] content = null;
        try {
            fis = new FileInputStream(file);
            content = new byte[fis.available()];
            fis.read(content);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
                fis = null;
            }
        }
        return content;
    }

    public static byte[] read(MultipartFile file) {
        byte[] content = null;
        try {
            InputStream inputStream = file.getInputStream();
            content = new byte[inputStream.available()];
            inputStream.read(content);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return content;
    }


    /**
     * 将saveProperties保存为文件
     *
     * @param filePath
     * @param parameterName
     * @param parameterValue
     */
    public static void saveProperties(String filePath, String parameterName, String parameterValue) {
        Properties prop = new Properties();
        try {
            InputStream fis = new FileInputStream(filePath);
            prop.load(fis);
            OutputStream fos = new FileOutputStream(filePath);
            prop.setProperty(parameterName, parameterValue);
            prop.store(fos, "Update '" + parameterName + "' value");
            fis.close();
        } catch (IOException e) {
            System.err.println("Visit " + filePath + " for updating " + parameterName + " value error");
        }

    }

    /**
     * 获取目录下所有文件和目录（不包含子目录）
     *
     * @param extArr
     * @return
     */
    public static List<Map<String, String>> listFile(String dir, String[] extArr) {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        File file = new File(dir);
        if (!file.exists()) {
            LOG.info("文件名称不存在!");
        } else {
            Map<String, String> hm = new HashMap<String, String>();
            if (file.isFile()) {
                if (extArr != null) {
                    for (int i = 0; i < extArr.length; i++) {
                        if (file.getName().toLowerCase().endsWith(extArr[i])) {// 文件格式
                            hm.put("fileName", file.getName());
                            hm.put("isDir", "false");
                            hm.put("path", file.getPath());
                            hm.put("size", file.length() + "");
                            hm.put("isDir", "false");
                            hm.put("modified", DateUtil.longToDate(file.lastModified()));
                            hm.put("fileDir", file.getParent());
                            mapList.add(hm);
                        }
                    }
                } else {
                    hm.put("fileName", file.getName());
                    hm.put("isDir", "false");
                    hm.put("path", file.getPath());
                    hm.put("size", file.length() + "");
                    hm.put("modified", DateUtil.longToDate(file.lastModified()));
                    hm.put("fileDir", file.getParent());
                    mapList.add(hm);

                }
            } else {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {

                    }
                    if (extArr != null) {
                        if (file.getName().toLowerCase().endsWith(extArr[i])) {// 文件格式
                            Map<String, String> hm_ = new HashMap<String, String>();
                            hm_.put("fileName", files[i].getName());
                            //为目录true;
                            hm_.put("isDir", "true");
                            hm_.put("path", files[i].getPath());
                            hm_.put("size", files[i].length() + "");
                            hm_.put("path", files[i].getPath());
                            hm_.put("modified", DateUtil.longToDate(files[i].lastModified()));
                            hm_.put("count", files[i].length() + "");
                            hm_.put("fileDir", files[i].getParent());
                            mapList.add(hm_);
                        }
                    } else {
                        Map<String, String> hm_ = new HashMap<String, String>();
                        hm_.put("fileName", files[i].getName());
                        //为目录true;
                        hm_.put("isDir", "true");
                        hm_.put("path", files[i].getPath());
                        hm_.put("size", files[i].length() + "");
                        hm_.put("path", files[i].getPath());
                        hm_.put("modified", DateUtil.longToDate(files[i].lastModified()));
                        hm_.put("count", files[i].length() + "");
                        hm_.put("fileDir", files[i].getParent());
                        mapList.add(hm_);
                    }

                }
            }
        }
        return mapList;
    }


    /**
     * 获取目录下文件和目录（包含子目录）
     *
     * @param path
     * @param extArr
     * @return
     */
    public static List<Map<String, String>> listFileAll(String path, String[] extArr) {
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        File file = new File(path);
        if (file.exists()) {
            mapList = listChild(file, extArr, mapList);
        }
        return mapList;
    }


    private static List<Map<String, String>> listChild(File file, String[] extArr, List<Map<String, String>> mapList) {
        if (!file.exists()) {
            LOG.info("文件名称不存在!");
        } else {
            Map<String, String> hm = new HashMap<String, String>();
            if (file.isFile()) {
                if (extArr != null) {
                    for (int i = 0; i < extArr.length; i++) {
                        if (file.getName().toLowerCase().endsWith(extArr[i])) {// 文件格式
                            hm.put("fileName", file.getName());
                            hm.put("isDir", "false");
                            hm.put("path", file.getPath());
                            hm.put("size", file.length() + "");
                            hm.put("modified", DateUtil.longToDate(file.lastModified()));
                            hm.put("fileDir", file.getParent());
                            mapList.add(hm);
                        }
                    }
                } else {
                    hm.put("fileName", file.getName());
                    hm.put("isDir", "false");
                    hm.put("path", file.getPath());
                    hm.put("size", file.length() + "");
                    hm.put("modified", DateUtil.longToDate(file.lastModified()));
                    hm.put("fileDir", file.getParent());
                    mapList.add(hm);
                }
            } else {
                File[] files = file.listFiles();
                hm.put("fileName", file.getName());
                //为目录true;
                hm.put("isDir", "true");
                hm.put("path", file.getPath());
                hm.put("size", file.length() + "");
                hm.put("path", file.getPath());
                hm.put("modified", DateUtil.longToDate(file.lastModified()));
                hm.put("count", files.length + "");
                hm.put("fileDir", file.getParent());
                mapList.add(hm);
                for (int i = 0; i < files.length; i++) {
                    listChild(files[i], extArr, mapList);
                }
            }
        }
        return mapList;
    }

    /**
     *    * 删除单个文件
     *    * @param filePath
     *    * 文件目录路径
     *    * @param fileName
     *    * 文件名称
     *    
     */
    public static void deleteFile(String filePath, String fileName) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    if (files[i].getName().equals(fileName)) {
                        files[i].delete();
                        return;
                    }
                }
            }
        }
    }

    /**
     * 流转byte数组
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }


    /**
     * 数据文件，返回文件内容
     *
     * @param path
     * @return
     */
    public static String readFile(String path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    public static void main(String[] args) throws Exception {

        FileUtil.appendRandomFile("./random.bin", "hello".getBytes());

    }

    // 自定义上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";

    //MultipartFile上传文件
    public static String getFileInfo(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
        String filePath = new String();
        String uploadPath = request.getSession().getServletContext().getRealPath("./") + UPLOAD_DIRECTORY;

        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        //判断文件是否为空
        if (!file.isEmpty()) {
            try {
                //文件的保存路径
                filePath = request.getSession().getServletContext().getRealPath("/") + UPLOAD_DIRECTORY + File.separator + file.getOriginalFilename();

                //转存文件
                file.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }//函数结束符


    //从指定路径下读取字符串
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
