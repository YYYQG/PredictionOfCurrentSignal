package com.xxx.predictionofcurrentsignal.controller;

import com.xxx.predictionofcurrentsignal.service.FileRecordService;
import com.xxx.predictionofcurrentsignal.service.ModelService;
import com.xxx.predictionofcurrentsignal.util.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
public class ModelController {

    @Autowired
    private ModelService modelService;
    @Autowired
    private FileRecordService fileRecordService;
    @PostMapping("/model")
    public ResponseEntity train(String[] selectedItems, Integer number,Authentication authentication) throws IOException {
        Map<String,Object> result=null;
        result=modelService.trainModel(selectedItems, number,authentication);
        return ResponseEntity.success(result);
    }


    @PostMapping("/predict")
    public ResponseEntity predict(String uid) throws IOException {
        Map<String,Object> result= modelService.predict(uid);
        return ResponseEntity.success(result);
    }

}
