package com.xxx.predictionofcurrentsignal.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "file_record")
@NamedEntityGraph(name = "user",attributeNodes ={@NamedAttributeNode("user")} )
public class FileRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "size")
    private String size;
    @Column(name = "file_name_system")
    private String fileNameSystem;
    @Column(name = "upload_time")
    private LocalDateTime uploadTime;
    @Column(name = "uid")
    private String uid;
    @Column(name = "type")
    private String type;
    @OneToOne
    @JoinColumn(name="user_id",referencedColumnName = "id",insertable=false,updatable = false)
    private User user;

}
