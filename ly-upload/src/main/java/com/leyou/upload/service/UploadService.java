package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.exception.LyException;
import com.leyou.common.exception.LyExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/06-18:01
 * hello everyone
 */

@Slf4j
@Service
public class UploadService {

    private static final List<String> ALLOW_TYPES = Arrays.asList("image/jpeg","image/png","image/bmp");
    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    public String upload(HttpServletRequest request, MultipartFile file){

        try {
            if( !ALLOW_TYPES.contains(file.getContentType()) ){
                throw new LyException("不是上传的类型");
            }

            if(null == ImageIO.read(file.getInputStream())){
                throw new LyException("不是上传的类型");
            }

            //String basePath = request.getSession().getServletContext().getRealPath("/images/");
            //String basePath = "D:\\Xu2\\springBoot乐优商城\\我的代码\\leyou\\images\\";
            //File dest = new File(basePath,file.getOriginalFilename());
            //file.transferTo(dest);
            String extend = org.apache.commons.lang3.StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extend, null);
            String returnPath = "http://image.leyou.com/" + storePath.getFullPath();

            return returnPath;
        } catch (IOException e) {
            //上传失败
            log.error(e.getMessage());
            throw new LyException(LyExceptionEnum.FILE_UPLOAD_FAILED);
        }

    }

}
