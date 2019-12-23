package com.xxx.predictionofcurrentsignal.authentication.code;

import com.xxx.predictionofcurrentsignal.po.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {

    ValidateCode generate(ServletWebRequest request);

}
