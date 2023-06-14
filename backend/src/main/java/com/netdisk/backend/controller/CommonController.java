package com.netdisk.backend.controller;


import com.netdisk.backend.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传下载
 */
@RestController
public class CommonController {

    @Value("${file.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
//    file 必须喝前端保持一致
    @PostMapping("/common/upload/")
    public R<String> upload(MultipartFile file) throws IOException {

//        原始文件名
        String originalFilename = file.getOriginalFilename();
//        文件名后缀  从 "." 开始截取
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

//        使用uuid 生成文件名称 防止文件名重复
        String fileName = UUID.randomUUID().toString() + suffix;

        File dir = new File(basePath);

        if(!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basePath + fileName));
        }catch (IOException e){
            e.printStackTrace();
        }

        return R.success(fileName);
    }

    @GetMapping("/common/download/")
    public void download(String name, HttpServletResponse response){

        try {
            //        输入流
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //        输出流
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
