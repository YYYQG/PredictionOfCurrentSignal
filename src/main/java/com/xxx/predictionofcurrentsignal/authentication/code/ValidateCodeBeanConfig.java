/**
 * 
 */
package com.xxx.predictionofcurrentsignal.authentication.code;

import com.xxx.predictionofcurrentsignal.authentication.sms.DefaultSmsCodeSender;
import com.xxx.predictionofcurrentsignal.authentication.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhailiang
 *
 */
@Configuration
public class ValidateCodeBeanConfig {
	

	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}

}
