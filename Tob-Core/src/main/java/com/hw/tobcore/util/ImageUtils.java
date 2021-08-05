package com.hw.tobcore.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @Description: 图像处理类
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2019/12/10 16:41
 */
@Slf4j
public class ImageUtils {


    /**
     * imgArr 转 BufferedImage
     *
     * @param imgArr
     * @return
     */
    public static BufferedImage convert2BufferedImage(byte[] imgArr) {
        try {
            if (imgArr == null || imgArr.length == 0) {
                log.error("[convert2BufferedImage error ] imgArr=null");
                return null;
            }
            InputStream in = new ByteArrayInputStream(imgArr); // 将b作为输入流；
            return ImageIO.read(in);
        } catch (Exception e) {
            log.error("[convert2BufferedImage error ] errMsg={}", e.getMessage());
            return null;
        }
    }

    /**
     * BufferedImage 转指定格式的base64 字符串
     *
     * @param image
     * @param format
     * @param size
     * @return
     */
    public static byte[] convert2bytes(BufferedImage image, String format, int size) {
        try {
            if (image == null || StringUtils.isBlank(format) || size == 0) {
                log.error("[convert2bytes error ] image={} , format={} , size={}", image, format, size);
                return null;
            }
            ByteArrayOutputStream bout = new ByteArrayOutputStream(size);
            ImageIO.write(image, format, bout);
            return bout.toByteArray();
        } catch (Exception e) {
            log.error("[convert2bytes error ] errMsg={}", e.getMessage());
            return null;
        }
    }

    public static void save(byte[] image, String outPath) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(image);
            BufferedImage bi1 = ImageIO.read(bais);
            File file = new File(outPath);// 可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", file);// 不管输出什么格式图片，此处不需改动
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
