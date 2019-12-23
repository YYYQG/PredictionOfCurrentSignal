package com.xxx.predictionofcurrentsignal.service;

import com.xxx.predictionofcurrentsignal.dao.entity.FileRecord;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface FileRecordService {
    void saveFileRecord(MultipartFile file, Authentication authentication, String uid,String type) throws IOException;
    void findFileRecord(String[] selectedItems);
    Map<String,Object> getAllFileByPage(String fileName,String start,String end,Integer pageSize, Integer page);
    void deleteFileByFileName(String fileNameSystem);
    Map<String,Integer> getFileCountByName(String username);
    Map<String,Object> getAllFileByName(String fileName,String start,String end,Integer pageSize, Integer page,String username);
}
