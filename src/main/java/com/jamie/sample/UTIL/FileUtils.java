package com.jamie.sample.UTIL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static String upload(String uploadPath, String originalName, byte[] fileData)throws Exception{

        String savedPath = calcPath(uploadPath);

        File target = new File(uploadPath + savedPath, originalName);

        FileCopyUtils.copy(fileData, target);

        return savedPath + File.separator + originalName;
    }

    private static String calcPath(String uploadPath){

        Calendar cal = Calendar.getInstance();

        String yearPath = File.separator+cal.get(Calendar.YEAR);

        String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);

        String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

        makeDir(uploadPath, yearPath, monthPath, datePath);

        logger.info(datePath);

        return datePath;
    }

    private static void makeDir(String uploadPath, String... paths){

        if(new File(paths[paths.length-1]).exists()){
            return;
        }

        for (String path : paths) {

            File dirPath = new File(uploadPath + path);

            if(! dirPath.exists() ){
                dirPath.mkdir();
            }
        }
    }
}
