package com.xxx.predictionofcurrentsignal.authentication.code;

import com.xxx.predictionofcurrentsignal.po.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeRepository {

    void save(ServletWebRequest request, ValidateCode validateCode);
    ValidateCode get(ServletWebRequest request);
    void remove(ServletWebRequest request);

}
