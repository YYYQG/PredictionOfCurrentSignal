package com.xxx.predictionofcurrentsignal.dao.repository;

import com.xxx.predictionofcurrentsignal.dao.entity.TrainRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;

public interface TrainRecordRepository extends JpaRepository<TrainRecord,Integer>,JpaSpecificationExecutor<TrainRecord> {

        int countTrainRecordByTrainTimeBetweenAndUserId(LocalDateTime start, LocalDateTime end, int userId);
        int countTrainRecordByUserId(int userId);
}
