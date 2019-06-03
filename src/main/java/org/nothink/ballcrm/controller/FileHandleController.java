package org.nothink.ballcrm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class FileHandleController {
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${file.downloadPath}")
    private String dowloadPath;


    @GetMapping("/pages/upload")
    public String getUploadPage() {
        return "upload";
    }

    /**
     * 单文件上传处理
     */
    @PostMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("fileName") MultipartFile file) {
        boolean flag = saveFile(file);
        if (!flag) {
            return "保存失败！";
        } else {
            return "保存成功！";
        }
    }


    /**
     * 多文件上传处理
     */
    @PostMapping("multifileUpload")
    @ResponseBody
    public String multifileUpload(HttpServletRequest request) {

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("fileName");
        if (files.isEmpty()) {
            return "无文件上传！";
        }
        for (MultipartFile file : files) {
            boolean flag = saveFile(file);
            if (!flag)
                return "保存失败！";
        }
        return "保存成功！";
    }

    /**
     * 保存单个文件处理
     */
    private boolean saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return true;
        }
        String fileName = file.getOriginalFilename();
        long size = file.getSize();
        System.out.println(fileName + "-->" + size);
        //随机文件名
        String[] a = fileName.split("\\.");
        fileName = a[a.length - 1];
        fileName = UUID.randomUUID().toString() + "." + fileName;
        //今天的保存路径
        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String today = fdate.format(new Date());
        String savePath = uploadFolder + "/" + today;
        //目标访问路径
        String visitPath = dowloadPath + "/" + today + "/" + fileName;
        System.out.println("下载路径为：" + visitPath);

        File dest = new File(savePath + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            //父目录不存在则新建父目录
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
