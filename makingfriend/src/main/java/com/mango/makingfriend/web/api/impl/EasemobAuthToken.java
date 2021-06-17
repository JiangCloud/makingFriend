package com.mango.makingfriend.web.api.impl;


import com.mango.makingfriend.web.api.AuthTokenAPI;
import com.mango.makingfriend.web.api.comm.TokenUtil;
import org.springframework.stereotype.Service;

@Service
public class EasemobAuthToken implements AuthTokenAPI {

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
