package com.xxx.predictionofcurrentsignal.dao.repository;

import com.xxx.predictionofcurrentsignal.dao.entity.FileRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;


public interface FileRecordRepository extends JpaRepository<FileRecord,Integer>,JpaSpecificationExecutor {

    @EntityGraph(value = "user")
    public FileRecord[] findDistinctByUidIn(String[] selectedItems);
    @EntityGraph(value = "user")
    public FileRecord findFileRecordByUid(String uid);
    @EntityGraph(value = "user")
    public void deleteFileRecordByFileNameSystem(String fileNameSystem);
    public int countFileRecordByUploadTimeBetween(LocalDateTime start, LocalDateTime end);
    @EntityGraph(value = "user")
    Page<FileRecord> findAll( Specification var1, Pageable var2);
    int countFileRecordByUploadTimeBetweenAndUserId(LocalDateTime start, LocalDateTime end,int userId);
    int countFileRecordByUserId(int userId);
}
