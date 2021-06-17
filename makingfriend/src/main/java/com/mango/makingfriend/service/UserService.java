package com.mango.makingfriend.service;

import java.util.List;

import com. mango.makingfriend.model.User;

public interface UserService {
	 int findbyNameOrphone(String telPhone);
	 String findbyUseId(String userId);
	 int add(User user);
	 User findById(int id);
	//public Users findByIdAndPassword(int id,String password);
	//public Users findByName(String username);
	 User login(String username,String password);
	//public List<Users> findByPower(int power);
	//public List<Users> findAll();
	 List<User> findAll(String page,String size);
	 int updateBackImage(String  backImageUrl ,String userId,String avatarUrl);
	 User getUserInfo(String userId);
	
	 User findUserInfo(String fxid);
	 List<User> findUserInfoes(List<String> fxid);
	 User getUserInfoByQueryString(String queryString);
}
