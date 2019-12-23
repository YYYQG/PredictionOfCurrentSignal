package com.xxx.predictionofcurrentsignal.service.Impl;

import com.google.common.primitives.Doubles;
import com.xxx.predictionofcurrentsignal.NeuralNet.NeuralNet;
import com.xxx.predictionofcurrentsignal.NeuralNet.util.Chart;
import com.xxx.predictionofcurrentsignal.NeuralNet.util.Data;
import com.xxx.predictionofcurrentsignal.dao.entity.FileRecord;
import com.xxx.predictionofcurrentsignal.dao.entity.TrainRecord;
import com.xxx.predictionofcurrentsignal.dao.entity.User;
import com.xxx.predictionofcurrentsignal.dao.repository.FileRecordRepository;
import com.xxx.predictionofcurrentsignal.dao.repository.TrainRecordRepository;
import com.xxx.predictionofcurrentsignal.dao.repository.UserRepository;
import com.xxx.predictionofcurrentsignal.service.ModelService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ModelServiceImpl implements ModelService {

    private static final String FOLDER="D:\\train_data";

    @Autowired
    private FileRecordRepository fileRecordRepository;
    @Autowired
    private TrainRecordRepository trainRecordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NeuralNet neuralNet;

    @Override
    public Map<String,Object> trainModel(String[] selectedItems, Integer number,Authentication authentication) throws IOException {
        //(String)authentication.getPrincipal()
        Map<String,Object> result = new HashMap<>();
        FileRecord[] fileRecords = fileRecordRepository.findDistinctByUidIn(selectedItems);
        Data[] datas = new Data[fileRecords.length];
        List<double[][]> input = new ArrayList<>();
        List<double[][]> output = new ArrayList<>();
        double[][] inputData = null;
        double[][] outputData = null;
        for(int i=0;i<fileRecords.length;i++){
            datas[i]=new Data(fileRecords[i].getFileNameSystem());
            Map<String, double[][]> data = datas[i].rawData2Matrix2(datas[i]);
            input.add(Data.normalize(data.get("input"),Data.NormalizationTypesENUM.MAX_MIN_EQUALIZED));
            output.add(Data.normalize(data.get("output"),Data.NormalizationTypesENUM.MAX_MIN_EQUALIZED));
        }
        for (int i=0;i<fileRecords.length;i++) {
            inputData= (double[][]) ArrayUtils.addAll(inputData,input.get(i));
            outputData= (double[][]) ArrayUtils.addAll(outputData,output.get(i));
        }
        double average = Data.average(outputData)[0];
        double standardDeviation = Data.standardDeviation(outputData)[0];
        neuralNet.setAverage(average);
        neuralNet.setStandardDeviation(standardDeviation);
        neuralNet.setMaxEpochs(number);
        neuralNet.setTrainSet(inputData);
        neuralNet.setRealMatrixOutputSet(outputData);
        //neuralNet.getListOfMSE().clear();
        neuralNet=neuralNet.trainNet(neuralNet);
        ArrayList<Double> mse = neuralNet.getListOfMSE();
        double[][] estimatedOutput=Data.denormalize(outputData,neuralNet.getNetOutputValues(neuralNet),Data.NormalizationTypesENUM.MAX_MIN_EQUALIZED);
        User user = userRepository.findUserByName((String)authentication.getPrincipal());
        double da = NumberUtils.min(Doubles.toArray(mse));
        TrainRecord trainRecord = new TrainRecord(0,user.getId(),LocalDateTime.now(),selectedItems[0],da,null);
        trainRecordRepository.save(trainRecord);
        result.put("mse",mse);
        result.put("estimatedOutput",estimatedOutput);
        result.put("realOutput",outputData);
        return result;
    }

    public Map<String,Object> predict(String uid) throws IOException {
        Map<String,Object> result = new HashMap<>();
        Map<Integer,Double> error = null;
        FileRecord fileRecord = fileRecordRepository.findFileRecordByUid(uid);
        Data data = new Data(fileRecord.getFileNameSystem());
        double[][] inputData = data.rawData2Matrix(data);
        neuralNet.setTrainSet(Data.normalize(inputData,Data.NormalizationTypesENUM.MAX_MIN_EQUALIZED));
        neuralNet.setRealMatrixOutputSet(new double[inputData.length][1]);
        double average = neuralNet.getAverage();
        double standardDeviation = neuralNet.getStandardDeviation();
        double[][] outputData = neuralNet.getNetOutputValues(neuralNet);
        error = anomalyDetermination(outputData,average,standardDeviation);
        result.put("realOutput",outputData);
        result.put("error",error);
        return result;
    }

    public Map<Integer,Double> anomalyDetermination(double[][] data,double average,double standardDeviation){

        Map<Integer,Double> result = new HashMap<>();
        for (int i=0;i<data.length;i++){
            if(Math.abs(data[i][0]-average)>3*standardDeviation){
                result.put(i,data[i][0]);
            }
        }
        return result;
    }
}
