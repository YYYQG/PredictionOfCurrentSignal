package com.xxx.predictionofcurrentsignal.config;

import com.xxx.predictionofcurrentsignal.NeuralNet.NeuralNet;
import com.xxx.predictionofcurrentsignal.NeuralNet.learn.Training;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NeuralNetConfig {
    @Bean
    public NeuralNet neuralNet(){
        NeuralNet neuralNet = new NeuralNet();
        neuralNet = neuralNet.initNet(3, 1, 5, 1);
        neuralNet.setMaxEpochs(1000);
        neuralNet.setTargetError(0.00001);
        neuralNet.setLearningRate(0.5);
        neuralNet.setTrainType(Training.TrainingTypesENUM.BACKPROPAGATION);
        neuralNet.setActivationFnc(Training.ActivationFncENUM.SIGLOG);
        neuralNet.setActivationFncOutputLayer(Training.ActivationFncENUM.LINEAR);
        return neuralNet;
    }
}
