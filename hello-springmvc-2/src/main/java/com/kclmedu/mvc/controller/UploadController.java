package com.kclmedu.mvc.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadController {

    private static final Logger log = Logger.getLogger(UploadController.class);
    private final String UPLOAD_DIR = "upload_dir";

    @RequestMapping(value = "/upload")
    public String upload(MultipartFile uploadFile, Model model,
                            HttpSession session)
                                throws IOException {
        log.debug("-- 上传的文件名："+uploadFile.getOriginalFilename());
        log.debug("-- 文件大小： "+uploadFile.getSize());
        //
        String originalFilename = uploadFile.getOriginalFilename();
        int pos = originalFilename.lastIndexOf(".");
        //原文件后缀名
        String suffix = originalFilename.substring(pos);
        //确定好服务端保存文件的具体位置
        ServletContext context = session.getServletContext();
        String realpath = context.getRealPath(UPLOAD_DIR);
        //产生一个uuid式的随机文件名
        String uuid = UUID.randomUUID().toString();
        //构建目标的完整文件名
        String fullname = realpath+ File.separator+uuid+suffix;
        log.debug("** 目标文件名"+fullname);
        //利用IO流进行读写，此处可以利用 commons-io中的工具类来快速完成
        FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), new File(fullname));
        log.debug("-- 把上传的源文件COPY到目标文件完成....");
        //把一个信息绑定到model中，以便在前端可以显示
        model.addAttribute("originalFilename",originalFilename);
        model.addAttribute("server_file_name",fullname);
        model.addAttribute("filesize", uploadFile.getSize());
        //
        return "forward:/common/upload.jsp"; //不经过视图解析器
    }

    @RequestMapping(value = "/todownload")
    public String toDownloadCenter(Model model,HttpSession session) throws Exception {
        //获取应用上下文
        ServletContext context = session.getServletContext();
        String realpath = context.getRealPath(UPLOAD_DIR);
        //获取目前已上传的文件夹中所有的文件
        File dir = new File(realpath);
        if(dir != null) {
            File[] files = dir.listFiles();
            //
            System.out.println(files);
            //
            model.addAttribute("files",files);
        } else {
            throw new RuntimeException("出问题了...");
        }
        return "forward:/common/download.jsp";
    }

    /***********
     * 下载：通过设置响应内容为具体的文件类型
     * @throws Exception
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取应用上下文
        ServletContext context = request.getServletContext();
        String realpath = context.getRealPath(UPLOAD_DIR);
        log.debug("要下载的文件名："+id);
        //找到指定的文件
        File parent = new File(realpath);
        File target = new File(parent, id);
        //
        if(target.isFile()) {
            //根据文件的类型不同，设置不同的响应内容类型
            if(id.endsWith(".zip")) {
                log.debug("-- 文件内容是 zip 格式，应响头为：application/zip");
                response.setContentType("application/zip");
            } else if(id.endsWith(".jpg") || id.endsWith(".jpeg")) {
                log.debug("-- 文件内容是图片格式，应响头为：image/jpeg");
                response.setContentType("image/jpeg");
            } else {
                log.debug("-- 设置成通用的格式：application/octet-stream ");
                response.setContentType("application/octet-stream");
            }
            //这个头可以让浏览器弹出保存框
            response.setHeader("Content-disposition","attachment;filename=\""+id+"\"");
            //            //把此文件以流的方式输出到客户端
            try(FileInputStream fis = new FileInputStream(target);) {
                //利用commons-io的工具类来完成读写的COPY
                IOUtils.copy(fis, response.getOutputStream());
                //刷新缓存
                response.flushBuffer();
                log.debug("下载完成");
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.debug("下载的文件："+id);
    }
}
