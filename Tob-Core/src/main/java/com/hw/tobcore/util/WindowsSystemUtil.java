package com.hw.tobcore.util;


import com.iristar.center.entity.base.SystemMonitor;
import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/7/6 9:25
 */
public class WindowsSystemUtil {
    /**
     * windows 已验证
     *
     * @return
     */
    public static String getMemery() {
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
        // 总的物理内存+虚拟内存
        // long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();
        // 物理内存（内存条）
        long physicalMemorySize = osmxb.getTotalPhysicalMemorySize();
        System.out.println("物理内存：" + physicalMemorySize / 1024.0 / 1024.0 / 1024.0 + " G");
        // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
        System.out.println("总  的  内 存" + Math.round(physicalMemorySize / 1024.0 / 1024.0 / 1024.0) + " G");
        System.out.println(
                "使用的内存" + Math.round((physicalMemorySize - freePhysicalMemorySize) / 1024.0 / 1024.0 / 1024.0) + " G");
        Double compare = (Double) (1 - freePhysicalMemorySize * 1.0 / physicalMemorySize) * 100;
        String str = "内存已使用:" + compare.intValue() + "%";
        return str;
    }

    /**
     * windows 已验证
     *
     * @param systemMonitor
     * @return
     */
    public static SystemMonitor getDisk(SystemMonitor systemMonitor) {
        // 操作系统
        List<String> list = new ArrayList<String>();
        for (char c = 'A'; c <= 'Z'; c++) {
            String dirName = c + ":/";
            char c1 = systemMonitor.getPath().charAt(0);
            File win = new File(dirName);
            if (win.exists()) {

                double total = win.getTotalSpace() / 1024.0 / 1024.0 / 1024.0;
                double free = win.getFreeSpace() / 1024.0 / 1024.0 / 1024.0;
                // 保留一位小数
                total = Double.valueOf(String.valueOf(total).substring(0, String.valueOf(total).indexOf(".") + 2));
                free = Double.valueOf(String.valueOf(free).substring(0, String.valueOf(free).indexOf(".") + 2));
                Double compare = (Double) (1 - free * 1.0 / total) * 100;
                String str = c + ":盘  总量:" + total + "G 剩余  " + free + "G 已使用 " + compare.intValue() + "%";
                list.add(str);
                if (c1 == c) {
                    systemMonitor.setFileSystem(dirName);
                    systemMonitor.setCapacity(total + "G");
                    systemMonitor.setAvailable(free + "G");
                    systemMonitor.setPerUsed(compare.intValue() + "%");
                    systemMonitor.setBeyThreshold(compare >= systemMonitor.useRate ? true : false);
                    return systemMonitor;
                }
            }
        }
        return systemMonitor;
    }

}
