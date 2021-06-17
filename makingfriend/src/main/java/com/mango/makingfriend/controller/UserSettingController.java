package com.mango.makingfriend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mango.makingfriend.model.Version;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mango.makingfriend.model.Message;

@Controller
@RequestMapping(value="userSetting")
public class UserSettingController {
	
	@RequestMapping(value="getVersion" )
	public @ResponseBody
    Message getVersion(){
		
		Message<Version> message=new Message<Version>();

        message.setRet_code(200);
		message.setRet_msg("success");
		List<Version> versions=new ArrayList<Version>();

		Version version=new Version();
		version.setVersion("1.0.1");
        version.setDownUrl("http://193.112.117.238:8080/makingfriend/androidApp/app-debug.apk");
        version.setUploeDate(new Date());
		versions.add(version);
		message.setRet_data(versions);
	
		return message;
	
		
		
	}
	
	
	
	
	

}
