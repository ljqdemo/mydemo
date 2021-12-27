package com.ljq.mydemo.util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.io.File;
import java.util.Objects;

public class FileUtils {
    public FileUtils() {
    }

    public static boolean isExist(String path) {
        if (null != path && !"".equals(path.trim())) {
            File targetFile = new File(path);
            return targetFile.exists();
        } else {
            return false;
        }
    }

    public static long getFileLength(String path) {
        if (null != path && !"".equals(path.trim())) {
            File targetFile = new File(path);
            return targetFile.length();
        } else {
            return 0L;
        }
    }

    public static String formatSize(long size) {
        if (size < 1024L) {
            return size + "B";
        } else {
            size /= 1024L;
            if (size < 1024L) {
                return size + "KB";
            } else {
                size /= 1024L;
                if (size < 1024L) {
                    size *= 100L;
                    return size / 100L + "." + size % 100L + "MB";
                } else {
                    size = size * 100L / 1024L;
                    return size / 100L + "." + size % 100L + "GB";
                }
            }
        }
    }

    public static Integer getFileSizeKb(Integer fileSize) {
        return Objects.isNull(fileSize) ? 0 : fileSize / 1024;
    }

    public static String replaceStr(String content) {
        if (Objects.isNull(content)) {
            return content;
        } else {
            int i = content.indexOf("service_provider=");
            if (i > 0) {
                String substring = content.substring(i);
                int index = substring.indexOf("=") + 1;
                if (index == substring.length()) {
                    return content;
                } else {
                    String substring1 = substring.substring(index, substring.indexOf(";"));
                    return content.replace(substring1, "");
                }
            } else {
                return content;
            }
        }
    }
}
