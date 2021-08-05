package com.hw.tobcore.util;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5Util {
	
	private static char[] hexDigits={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	
	public static String md5(String str){
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if( md5 != null ){
			md5.update(str.getBytes());
			byte[] encodeBytes = md5.digest();
			char encodeStr[] = new char [encodeBytes.length * 2];
			int k = 0;
			for( int i = 0 ; i < encodeBytes.length ; i++ ){
				byte encodeByte = encodeBytes[i];
				encodeStr[k++] = hexDigits[encodeByte >> 4 & 0xf];
				encodeStr[k++] = hexDigits[encodeByte & 0xf];
			}
			return new String(encodeStr);
		}
		return null;
	}

    public static String md5(File file){
        try {
            return  md5(new FileInputStream(file));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String md5(InputStream inputStream) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(md5 != null && inputStream != null) {
            try {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer, 0, 1024)) != -1) {
                    md5.update(buffer, 0, length);
                }
                return new String(Hex.encodeHex(md5.digest()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String hmacMd5(String str, String key) throws Exception {
        String HMAC_MD5_NAME = "HmacMD5";
        SecretKeySpec sk = new SecretKeySpec(key.getBytes(), HMAC_MD5_NAME);
        Mac mac;
        try {
            mac = Mac.getInstance(HMAC_MD5_NAME);
            mac.init(sk);
            return bytes2Hex(mac.doFinal(str.getBytes())).toString();
        } catch (NoSuchAlgorithmException e) {
            throw new TransformerException(e);
        } catch (InvalidKeyException e) {
            throw new TransformerException(e);
        }
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

	
	public static void main(String[] args) throws Exception {
//		String str = "com.mysql.jdbc.Driver#jdbc:mysql://127.0.0.1:3306/datagather_db#root#root";
		System.out.println(MD5Util.md5("hongxing"));
		//a05ddb7b9a968ef7cc428128a2d72521

//        File file = new File("1.jpg");
//        System.out.println(md5(file));

//        System.out.println(getRandomString(5));
    }
	
}
