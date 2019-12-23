package com.xxx.predictionofcurrentsignal.service;

import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Map;

public interface ModelService {

    Map<String,Object> trainModel(String[] selectedItems, Integer number,Authentication authentication) throws IOException;
    Map<String,Object> predict(String uid) throws IOException;
}
