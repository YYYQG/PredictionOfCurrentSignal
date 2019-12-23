package com.xxx.predictionofcurrentsignal.service;

import java.util.Map;

public interface TrainRecordService {

    Map<String,Object> getAlTrainByPage(String fileName, String start, String end, Integer pageSize, Integer page,String name);
    Map<String,Integer> getTrainCountByName(String username);
}
