package com.xxx.predictionofcurrentsignal.controller;

import com.xxx.predictionofcurrentsignal.dao.entity.FileRecord;
import com.xxx.predictionofcurrentsignal.po.FileDTO;
import com.xxx.predictionofcurrentsignal.service.FileRecordService;
import com.xxx.predictionofcurrentsignal.util.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@Slf4j
public class FileController {

    @Autowired
    private FileRecordService fileRecordService;

    @PostMapping("/upload")
    public ResponseEntity uploadFile(MultipartFile file, Authentication authentication, String uid, String type) throws IOException {
        fileRecordService.saveFileRecord(file, authentication, uid, type);
        return ResponseEntity.success();
    }

    @PostMapping("/files")
    public ResponseEntity getAllFileByPage(@RequestBody FileDTO fileDTO) {
        Map result = fileRecordService.getAllFileByPage(fileDTO.getFileName(), fileDTO.getStart(), fileDTO.getEnd(), fileDTO.getPageSize(), fileDTO.getPage());
        return ResponseEntity.success(result);
    }

    @PostMapping("/file")
    public ResponseEntity getAllFileByName(@RequestBody FileDTO fileDTO,Authentication authentication) {
        Map result = fileRecordService.getAllFileByName(fileDTO.getFileName(), fileDTO.getStart(), fileDTO.getEnd(), fileDTO.getPageSize(), fileDTO.getPage(),(String)authentication.getPrincipal());
        return ResponseEntity.success(result);
    }

    @GetMapping("/file/{fileName}")
    public void download(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {

        try (InputStream inputStream = new FileInputStream(new File("D:\\train_data", fileName));
             OutputStream outputStream = response.getOutputStream();) {
            //response.setContentType("application/x-download");
            response.setContentType("application/octet-stream; charset=utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }

    }

    @DeleteMapping("/file/{fileNameSystem}")
    public ResponseEntity deleteFileByName(@PathVariable String fileNameSystem){
        fileRecordService.deleteFileByFileName(fileNameSystem);
        return ResponseEntity.success();
    }

    @GetMapping("/files/count")
    public ResponseEntity getFileCount(Authentication authentication){
        Map<String,Integer> result = fileRecordService.getFileCountByName((String)authentication.getPrincipal());
        return ResponseEntity.success(result);
    }

}
