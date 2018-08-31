
package com.fosun.xms.rest.interceptor;
import com.fosun.framework.base.util.CommonUtil;
import com.fosun.framework.base.util.JSONResult;
import com.fosun.framework.base.util.JSONUtil;
import com.fosun.framework.base.util.PostStrUtil;
import com.fosun.xms.constant.XMSErrorCodeEnum;
import com.fosun.xms.dto.TokenDto;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.jdbc.core.metadata.PostgresCallMetaDataProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.HashMap;


@Component
public class MyInterceptor implements HandlerInterceptor {


	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		System.out.println(httpServletRequest.getRequestURI());
		if ( "/error".equals(httpServletRequest.getRequestURI()) )
		{
			return true;
		}

//		从header中得到token
		String token = httpServletRequest.getHeader("token");


		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		JSONObject object = new JSONObject();
		object.put("appKey", "2");
		object.put("token", token);

		RestTemplate restTemplate= new RestTemplate();
		HttpEntity requestEntity = new HttpEntity(object.toString(), headers);

		URI uri = new URI("9500", "172.16.100.83","/v1.0/xms/authentication/refreshToken");
		System.out.println(uri);

		System.out.println(object.toString());
		ResponseEntity<String> responseEntity  = restTemplate.postForEntity(uri, requestEntity, String.class);

		String resStr = responseEntity.getBody();


		String doPost = null;
		if (null == doPost || "".equals(doPost)) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", 10000);
			jsonObject.put("msg", "未登录");
			jsonObject.put("data", null);
			JSONResult jsonResult = new JSONResult().fail(XMSErrorCodeEnum.NONE_LOGIN.getCode(),XMSErrorCodeEnum.NONE_LOGIN.getMessage());

			writeJsonByFilter(jsonObject, httpServletResponse);
			return false;
		}

		return false;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle...................");
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
		System.out.println("afterCompletion..................");
	}

	public void writeJsonByFilter(JSONObject object, HttpServletResponse res) {
		res.setContentType("text/json; charset=utf-8");
		try {
			System.out.println(object.toString());
			PrintWriter pWriter = res.getWriter();
			pWriter.write(object.toString());
			res.getWriter().write("it is hello3");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



}