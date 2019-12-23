package com.xxx.predictionofcurrentsignal.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    String fileName;
    String start;
    String end;
    Integer pageSize;
    Integer page;

}
