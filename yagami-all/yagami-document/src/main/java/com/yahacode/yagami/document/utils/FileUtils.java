package com.yahacode.yagami.document.utils;

import com.yahacode.yagami.base.common.DateUtils;
import com.yahacode.yagami.base.common.PropertiesUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {

    public static String getExtension(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension;
    }

    public static String getStorageUrl(String fileName) {
        String storageFileName = UUID.randomUUID().toString() + "_" + fileName;
        int hashcode = fileName.hashCode();
        int dir1 = hashcode & 0xf;
        // int dir2 = (hashcode & 0xf0) >> 4;
        String url = "/" + DateUtils.getDateStr() + "/" + dir1;
        String localFolder = getLocalStorage() + url;
        File file = new File(localFolder);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fullUrl = url + "/" + storageFileName;
        return fullUrl;
    }

    public static String getLocalStorage() {
        return PropertiesUtils.getSysConfig("local.storage");
    }

    public static String getMd5(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
            return md5;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
