package com.mango.makingfriend.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mango.makingfriend.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import com. mango.makingfriend.service.UserService;

import com.mango.makingfriend.util.Usersign;
import com.mango.makingfriend.util.VOPOConverter;
import org.springframework.web.multipart.MultipartFile;

@Api("UserController")
@Controller
@RequestMapping(value ="user")
public class UserController {

	private UserService userService;

	@Autowired
	private Usersign usersign;

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	@RequestMapping(value = "phonelogin")
	public @ResponseBody
	Message login(User User, HttpServletRequest request,
				  HttpServletResponse response) {
		
		//String path=request.getServletContext().getRealPath("/");


		User userOne = userService.login(User.getUsername(), User.getPassword());
		
		Message<AppInfo> meg = new Message<AppInfo>();
		if (userOne != null) {
			// 获取用户签名
			// String sign=Usersign.getUsersig(userOne.getId());
			String sign = usersign.getUsersig(userOne.getUsername());
			
			List appInfos=new ArrayList();
		
			//AppInfo appinfo=new AppInfo();		
			
			AppInfo appinfo= VOPOConverter.copyProperties(userOne, AppInfo.class);	

			appinfo.setUserSig(sign);
			appinfo.setIdentifier(userOne.getUsername());		
			appInfos.add(appinfo);
			meg.setRet_code(200);
			meg.setRet_msg("success");
			meg.setRet_data(appInfos);

		} else {
			meg.setRet_code(120);
			meg.setRet_msg("fail");

		}
		return meg;

	}
	
	@ApiOperation("根据fxId获取用户信息")
	@ApiImplicitParam(name = "fxId",required = true,dataType = "String")
	@RequestMapping(value="getUserInfo",method = RequestMethod.POST)
	public @ResponseBody
    ReturnMessage getUserInfo(String fxId){
		ReturnMessage returnMessage =new ReturnMessage();
		
	    User user=userService.getUserInfo(fxId);
	    if(user!=null&& StringUtils.isNotEmpty(user.getFxid())){
	    	
	    	 UserVo userVo= VOPOConverter.copyProperties(user, UserVo.class);
	    	 
	    	// userVo.setProvince("湖南省");
	    	// userVo.setCity("江华");
	    	 returnMessage.setUser(userVo);
	    	 returnMessage.setRet_code(200);
	    	 returnMessage.setRet_msg("success");
	    	 
	    	 return returnMessage;
    	 
       }
	    returnMessage.setRet_code(500);
	    returnMessage.setRet_msg("用户信息不存在！");
		return returnMessage;
		
		
		
	}
	
	
	
	@RequestMapping(value="findUserInfo")
	public  @ResponseBody
	ResponseMessage findUserInfo(String fxId){
		
		ResponseMessage<UserVo> responseMessage=new ResponseMessage<UserVo>();
		User user=userService.findUserInfo(fxId);
		if(user!=null&&StringUtils.isNotEmpty(user.getFxid())){
			
		    UserVo userVo= VOPOConverter.copyProperties(user, UserVo.class);
            responseMessage.setRet_code(200);
            responseMessage.setRet_msg("success");
            responseMessage.setRet_data(userVo);
	    	 
	    	 return responseMessage;
	    	 
		}
        responseMessage.setRet_code(500);
        responseMessage.setRet_msg("用户信息不存在。。。");
		return responseMessage;
		
		

	}


    @PostMapping(value="getUserInfoByQueryString")
    @ResponseBody
    public ResponseMessage getUserInfoByQueryString(String queryString){

        ResponseMessage<UserVo> responseMessage=new ResponseMessage<UserVo>();
        User user=userService.getUserInfoByQueryString(queryString);
        if(user!=null&&StringUtils.isNotEmpty(user.getFxid())){
            UserVo userVo= VOPOConverter.copyProperties(user, UserVo.class);
            responseMessage.setRet_code(200);
            responseMessage.setRet_msg("success");
            responseMessage.setRet_data(userVo);

            return responseMessage;

        }
        responseMessage.setRet_code(500);
        responseMessage.setRet_msg("未查找到用户信息");
        return responseMessage;





    }




    @RequestMapping(value="findUserInfos")
    public  @ResponseBody
	ResponseMessage findUserInfoes(@RequestParam(value = "fxids",required = true)  List<String> fxids){

        ResponseMessage<List<UserVo>> responseMessage =new ResponseMessage<List<UserVo>>();
        List<User> users=userService.findUserInfoes(fxids);
        if(users!=null&&users.size()>0){

            List<UserVo> userVo= VOPOConverter.mapList(users,UserVo.class);
            responseMessage.setRet_code(200);
            responseMessage.setRet_msg("success");
            responseMessage.setRet_data(userVo);

            return responseMessage;

        }
        responseMessage.setRet_code(500);
        responseMessage.setRet_msg("获取用户信息失败");
        return responseMessage;



    }
	

	
/*
	@RequestMapping("getUserInfoes")
	public @ResponseBody List<Users> getUsers(String page, String size) {

		List<Users> users = userService.findAll(page, size);

		return users;

	}*/
	
	/***
	 * 分页获取用户的信息
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("getPageUserInfo")
	public @ResponseBody List<ShowUserCard> getPageUserInfo(String page, String size) {

		List<User> users = userService.findAll(page, size);
		List<ShowUserCard> userCards=new ArrayList<ShowUserCard>();
		int j=0;
		for (int i = 0; i < users.size(); i++) {
			
	
				ShowUserCard card=new ShowUserCard();	
				card.setUserOne(users.get(i));
				card.setUserTwo(users.get(i++));
				card.setUserThree(users.get(i++));
				card.setUserFour(users.get(i++));				
				userCards.add(card);

				
			}

		return userCards;

	}


	@RequestMapping(value = "userRegister")
	public @ResponseBody
	Message UserRegister(User user) {
		Message message = new Message();
		if (user != null) {
			int count = userService.findbyNameOrphone(user.getTel());
			if (count > 0) {
				message.setRet_msg("该手机号码已注册");
				message.setRet_code(100);

			} else {
				int row=userService.add(user);
				if(row==1){
					message.setRet_msg("注册成功");
					message.setRet_code(200);

				}else {
					message.setRet_msg("注册失败");
					message.setRet_code(100);

				}


			}

		}
		return message;

	}

/*	@RequestMapping(value = "getHeadPicUrl")
	public void getHeadPicUrl(Users user, HttpServletRequest request,
			HttpServletResponse response) {

		String headPicUrl = userService.findbyName(user);
		try {

			PrintWriter out = response.getWriter();
			out.write(JSON.toJSONString(headPicUrl));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}*/

	@PostMapping(value = "uploadPic")
	public @ResponseBody
	Message uploadPic(
			@RequestParam("files") MultipartFile[] files,
			HttpServletRequest request) {
		Message message = new Message();
		String path = request.getSession().getServletContext()
				.getRealPath("/upload")
				+ File.separator;
		System.out.println(path);
		if (files != null && files.length > 0) {
			for (MultipartFile f : files) {
				if (f.isEmpty()) {
					continue;

				} else {
					String fileName = f.getOriginalFilename();// 真实文件 名
					System.out.println(f.getContentType());
					try {
						FileUtils.copyInputStreamToFile(f.getInputStream(),
								new File(path + fileName));

						message.setRet_code(200);
						message.setRet_msg("上传图片成功");
					} catch (IOException e) {
						message.setRet_code(0);
						message.setRet_msg("上传图片失败");
						e.printStackTrace();
					}

				}

			}

		}

		return message;

	}

}