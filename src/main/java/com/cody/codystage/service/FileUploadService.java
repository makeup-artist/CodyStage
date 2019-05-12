package com.cody.codystage.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.cody.codystage.common.constants.CodyConstants;
import com.cody.codystage.common.constants.ResConstants;
import com.cody.codystage.common.exception.ServiceException;
import com.cody.codystage.utils.FileTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @Classname FileUploadService
 * @Description TODO
 * @Date 2019/5/11 16:27
 * @Created by ZQ
 */
@Service
@Slf4j
public class FileUploadService {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;


    public String uploadFile(String bucketName, MultipartFile multipartFile){
        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String key= UUID.randomUUID().toString();
        String url= bucketName + "." + endpoint + "/" + key;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        InputStream inputStream1 = null;
        InputStream inputStream2 = null;
        try {
            inputStream1 = multipartFile.getInputStream();
            inputStream2 = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1213,ResConstants.HTTP_RES_CODE_1213_VALUE);
        }

        String fileType = FileTypeUtil.getFileType(inputStream1);
//        try {
//            bufferedInputStream.reset();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if(bucketName.equals(CodyConstants.IMAGE_BUCKETNAME)){
            if("jpg".equals(fileType)){
                objectMetadata.setContentType("image/jpeg");
            }else if("png".equals(fileType)){
                objectMetadata.setContentType("image/png");
            }else if("gif".equals(fileType)){
                objectMetadata.setContentType("image/gif");
            }else{
                throw new ServiceException(ResConstants.HTTP_RES_CODE_1212,ResConstants.HTTP_RES_CODE_1212_VALUE);
            }
        }else if(bucketName.equals(CodyConstants.VIDEO_BUCKETNAME)){
            if("mp4".equals(fileType)){
                objectMetadata.setContentType("video/mp4");
            }else if("mp3".equals(fileType)){
                objectMetadata.setContentType("video/mp3");
            }else{
                throw new ServiceException(ResConstants.HTTP_RES_CODE_1212,ResConstants.HTTP_RES_CODE_1212_VALUE);
            }
        }

        try {
            client.putObject(bucketName,key, inputStream2,objectMetadata);
            log.info("upload {} file",fileType);
        } catch (Exception e) {
            throw new ServiceException(ResConstants.HTTP_RES_CODE_1211,ResConstants.HTTP_RES_CODE_1211_VALUE);
        }finally {
            client.shutdown();
        }

        return url;

    }
}
