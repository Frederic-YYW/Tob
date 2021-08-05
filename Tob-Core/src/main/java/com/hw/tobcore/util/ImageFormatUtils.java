package com.hw.tobcore.util;

import com.iristar.center.cv.enu.ImageFormat;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/12/14 16:33
 */
public class ImageFormatUtils {

    public static ImageFormat getImageFormart(String format) {
        if (format.equalsIgnoreCase("bmp")) {
            return ImageFormat.BMP;
        } else if (format.equalsIgnoreCase("jpg") || format.equalsIgnoreCase("jpeg")) {
            return ImageFormat.JPEG;
        } else if (format.equalsIgnoreCase("png")) {
            return ImageFormat.PNG;
        } else {
            return null;
        }
    }
}
