package com.xxx.predictionofcurrentsignal.authentication.social.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

@Component("/connect/weixin")
public class ConnectedView extends AbstractView {

	@Autowired
	private ObjectMapper objectMapper;
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=UTF=8");
		Map<String,String> result = new HashMap<>();
		if (model.get("connections") == null) {
			result.put("message","解绑成功!");
			response.getWriter().write(objectMapper.writeValueAsString(result));
		} else {
			result.put("message","绑定成功!");
			response.getWriter().write(objectMapper.writeValueAsString(result));
		}
		
	}

}