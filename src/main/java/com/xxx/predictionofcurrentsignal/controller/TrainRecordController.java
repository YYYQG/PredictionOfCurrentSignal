package com.xxx.predictionofcurrentsignal.controller;

import com.xxx.predictionofcurrentsignal.po.TrainDTO;
import com.xxx.predictionofcurrentsignal.service.TrainRecordService;
import com.xxx.predictionofcurrentsignal.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class TrainRecordController {

    @Autowired
    private TrainRecordService trainRecordService;

    @PostMapping("/trainRecord")
    public ResponseEntity getAllFileByPage(@RequestBody TrainDTO trainDTO,Authentication authentication) {
        Map result = trainRecordService.getAlTrainByPage(trainDTO.getFileName(), trainDTO.getStart(), trainDTO.getEnd(), trainDTO.getPageSize(), trainDTO.getPage(), (String) authentication.getPrincipal());
        return ResponseEntity.success(result);
    }

    @GetMapping("/trains/count")
    public ResponseEntity getFileCount(Authentication authentication){
        Map<String,Integer> result = trainRecordService.getTrainCountByName((String)authentication.getPrincipal());
        return ResponseEntity.success(result);
    }
}
