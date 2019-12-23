package com.xxx.predictionofcurrentsignal.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "train_record")
public class TrainRecord {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "train_time")
    private LocalDateTime trainTime;

    @Column(name = "file_uid")
    private String fileUid;

    @Column(name = "mse")
    private double mse;

    @OneToOne
    @JoinColumn(name="file_uid",referencedColumnName = "uid",insertable=false,updatable = false)
    private FileRecord fileRecord;

}
