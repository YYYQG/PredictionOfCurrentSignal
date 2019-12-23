package com.xxx.predictionofcurrentsignal.NeuralNet.current;

import com.xxx.predictionofcurrentsignal.NeuralNet.NeuralNet;
import com.xxx.predictionofcurrentsignal.NeuralNet.learn.Training;
import com.xxx.predictionofcurrentsignal.NeuralNet.util.Chart;
import com.xxx.predictionofcurrentsignal.NeuralNet.util.Data;

import java.io.IOException;
import java.util.ArrayList;

public class current {

	public static void main(String args[]){
		
		Data weatherDataInput  = new Data( "inmet_13_14_input.csv");
		Data weatherDataOutput = new Data("inmet_13_14_output.csv");
		
		Data weatherDataInputTestRNA  = new Data( "inmet_13_14_input.csv");
		Data weatherDataOutputTestRNA = new Data("inmet_13_14_output.csv");
		
		Data.NormalizationTypesENUM NORMALIZATION_TYPE = Data.NormalizationTypesENUM.MAX_MIN_EQUALIZED;
		
		try {
			double[][] matrixInput   = weatherDataInput.rawData2Matrix( weatherDataInput );
			double[][] matrixOutput  = weatherDataOutput.rawData2Matrix( weatherDataOutput );
			
			double[][] matrixInputTestRNA  = weatherDataOutput.rawData2Matrix( weatherDataInputTestRNA );
			double[][] matrixOutputTestRNA = weatherDataOutput.rawData2Matrix( weatherDataOutputTestRNA );
			
			double[][] matrixInputNorm  = weatherDataInput.normalize(matrixInput, NORMALIZATION_TYPE);
			double[][] matrixOutputNorm = weatherDataOutput.normalize(matrixOutput, NORMALIZATION_TYPE);
			
			double[][] matrixInputTestRNANorm = weatherDataOutput.normalize(matrixInputTestRNA, NORMALIZATION_TYPE);
			double[][] matrixOutputTestRNANorm = weatherDataOutput.normalize(matrixOutputTestRNA, NORMALIZATION_TYPE);
			
			NeuralNet n1 = new NeuralNet();
			n1 = n1.initNet(3, 1, 5, 1);
			
			n1.setTrainSet( matrixInputNorm );
			n1.setRealMatrixOutputSet( matrixOutputNorm );
			
			n1.setMaxEpochs(1000);
			n1.setTargetError(0.00001);
			n1.setLearningRate(0.5);
			n1.setTrainType(Training.TrainingTypesENUM.BACKPROPAGATION);
			n1.setActivationFnc(Training.ActivationFncENUM.SIGLOG);
			n1.setActivationFncOutputLayer(Training.ActivationFncENUM.LINEAR);
			
			NeuralNet n1Trained;
			
			n1Trained = n1.trainNet(n1);
			

			//ERROR:
			Chart c1 = new Chart();
			c1.plotXYData(n1.getListOfMSE().toArray(), "MSE Error", "Epochs", "MSE Value");
			
			//TRAINING:
			double[][] matrixOutputRNA  	  = n1Trained.getNetOutputValues(n1Trained);
			double[][] matrixOutputRNADenorm  = new Data().denormalize(matrixOutput, matrixOutputRNA, NORMALIZATION_TYPE);
			
			ArrayList<double[][]> listOfArraysToJoin = new ArrayList<double[][]>();
			listOfArraysToJoin.add( matrixOutput );
			listOfArraysToJoin.add( matrixOutputRNADenorm );
			
			double[][] matrixOutputsJoined = new Data().joinArrays(listOfArraysToJoin);
			
			Chart c2 = new Chart();
			c2.plotXYData(matrixOutputsJoined, "Real x Estimated - Training Data", "Weather Data", "Temperature (Celsius)", Chart.ChartPlotTypeENUM.COMPARISON);
			
			//TEST:
			n1Trained.setTrainSet( matrixInputTestRNANorm );
			n1Trained.setRealMatrixOutputSet( matrixOutputTestRNANorm );;
			
			double[][] matrixOutputRNATest  	  = n1Trained.getNetOutputValues(n1Trained);
			double[][] matrixOutputRNADenormTest  = matrixOutputRNATest;
			
			ArrayList<double[][]> listOfArraysToJoinTest = new ArrayList<double[][]>();
			listOfArraysToJoinTest.add( matrixOutputTestRNA );
			listOfArraysToJoinTest.add( matrixOutputRNADenormTest );
			
			double[][] matrixOutputsJoinedTest = new Data().joinArrays(listOfArraysToJoinTest);
			
			Chart c3 = new Chart();
			c3.plotXYData(matrixOutputsJoinedTest, "Real x Estimated - Test Data", "Weather Data", "Temperature (Celsius)", Chart.ChartPlotTypeENUM.COMPARISON);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
