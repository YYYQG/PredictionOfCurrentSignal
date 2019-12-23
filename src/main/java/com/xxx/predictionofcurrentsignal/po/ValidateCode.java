package com.xxx.predictionofcurrentsignal.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCode {

    private String Code;
    private LocalDateTime expireTime;

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
