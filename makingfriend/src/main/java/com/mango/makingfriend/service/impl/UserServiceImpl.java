package com.mango.makingfriend.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mango.makingfriend.dao.GroupDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mango.makingfriend.dao.UserDao;
import com. mango.makingfriend.service.UserService;
import com. mango.makingfriend.model.User;


@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private GroupDao groupDao;

	public int add(User user) {
	    try {
            User user1=userDao.save(user);
            if(user==null){

                return 0;
            }
            return 1;

        }catch (Exception e){
	       log.info("保存用户异常"+ e.getMessage());
			return 0;
        }



		
		 //this.userDao.save(user);
	}

	/*public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}*/

	public void delete(int id) {
		User user=new User();
		//user.setHxid(findById());

		// userDao.delete();

		//this.userDao.delete(id);
	}

/*	public Users update(Users user) {



		return this.userDao.update(user);
	}*/

	

	public User findById(int id) {

        User user =userDao.getOne(id);
		
		return user;
	}
	
	/*public Users findByIdAndPassword(int id,String password){
		
		return this.userDao.loadByIdAndPassword(id,password);
	}
	
	public Users findByName(String username){
		
		return this.userDao.loadByName(username);
		
	}*/
	
/*	@Transactional*/
	public User login(String username, String password) {
		
		return this.userDao.loadByNameAndPwd(username, password);
	}

/*
	@Override
	public Users updatePerson(Users user) {
		return userDao.updatePerson( user);
	}

	@Override
	public List<Users> findByPower(int power) {
		return userDao.loadByPower(power);
	}

	@Override
	public List<Users> findAll() {
		return userDao.findAll();
	}*/
	@Override
	public List<User> findAll(String page,String size) {
	        List<User> users=new ArrayList<User>();
	    if(!StringUtils.isEmpty(page)&&!StringUtils.isEmpty(size)){

            PageRequest pageRequest= PageRequest.of(Integer.parseInt(page),Integer.parseInt(size));
            Page<User> pages= userDao.findAll(pageRequest);
            users=pages.toList();
            return users;
        }

		return users;
	}

	@Override
	public int findbyNameOrphone( String phoneNum) {
	
		return userDao.findbyNameOrphone(phoneNum);
	}

	@Override
	public String findbyUseId(String userId) {

	    if(!StringUtils.isEmpty(userId)){

          return   userDao.findbyUseId(Integer.parseInt(userId));
	    }
		
          return "";
	}

	@Override
	public int updateBackImage( String backImageUrl,String userId,String avatarUrl) {


        try {
                if(StringUtils.isNotEmpty(userId)&&StringUtils.isNotEmpty(backImageUrl)){

                     userDao.updateBackImage( backImageUrl,Integer.parseInt(userId));
                    return 1;

                 }

                if(StringUtils.isNotEmpty(avatarUrl)&&StringUtils.isNotEmpty(userId)){
                    userDao.updateAvatar(avatarUrl,Integer.parseInt(userId));
                    return 1;
               }

        } catch (Exception e) {
            log.error(e.getMessage());
            return 0;
        }


        return 0;
	}

    /***
     *
     * @param userId
     * @return Users
     * 根据 userId获取用户信息
     */
	@Override
	public User getUserInfo(String userId) {

      List<Object> objects= userDao.getUserInfo(userId);
      User user=new User();
      if(objects!=null){
		  for(Object o:objects){

			  Object[] users=(Object[])o;
			  String hxid= String.valueOf(users[0]);
			 if(!StringUtils.isEmpty(hxid)){

				 user.setHxid(Integer.parseInt(hxid));
			 }
			  user.setFxid(String.valueOf(users[1]));
			  user.setTel(String.valueOf(users[2]));
			  user.setUserSig(String.valueOf(users[3]));

			  if(!StringUtils.isEmpty(String.valueOf(users[4]))){
				  user.setSex(Integer.parseInt(String.valueOf(users[4])));
			  }

			  user.setAvatar(String.valueOf(users[5]));
			  user.setProvince(String.valueOf(users[6]));
			  user.setCity(String.valueOf(users[7]));
			  user.setNick(String.valueOf(users[8]));

		  }


	  }
		return user;

           // user.setFxid(String.valueOf());



    // System.out.println("object[0]========"+object[0].toString());
           // user.setTel(String.valueOf(object[1]));

           // user.setUserSig(String.valueOf(object[2]));
           // user.setSex(String.valueOf(object[3]));
           // user.setAvatar(String.valueOf(object[4]));

		

	}

	/***
	 *
	 * @param  id
	 * @return Users
	 */

	@Override
	public User findUserInfo(String id) {
		List<Object> objects=new ArrayList<Object>();
		if(StringUtils.isNumeric(id)){
			objects=groupDao.findGroupInfo(id);

		}else {
			objects=userDao.findUserInfo(id);
		}

		User user=new User();
		if(objects!=null) {
			for (Object o : objects) {
				Object[] users = (Object[]) o;
				user.setFxid(String.valueOf(users[0]));
				user.setNick(String.valueOf(users[1]));
				user.setAvatar(String.valueOf(users[2]));

			}
		}

		return user;
	}

	@Override
	public List<User> findUserInfoes(List<String> fxids) {

		List<User> userList=new ArrayList<User>();

		for (String fxId:fxids){
			List<Object> objects=userDao.findUserInfo(fxId);

			if(objects!=null) {
				for (Object o : objects) {
					User user=new User();
					Object[] users = (Object[]) o;
					user.setFxid(String.valueOf(users[0]));
					user.setNick(String.valueOf(users[1]));
					user.setAvatar(String.valueOf(users[2]));
					userList.add(user);
				}

			}

		}
		return userList;
	}

	@Override
	public User getUserInfoByQueryString(String queryString) {


		List<Object> objects=userDao.getUserInfoByQueryString(queryString);
		User user=new User();
		if(objects!=null) {
			for (Object o : objects) {
				Object[] users = (Object[]) o;
				user.setFxid(String.valueOf(users[0]));
				user.setNick(String.valueOf(users[1]));
				user.setAvatar(String.valueOf(users[2]));

			}
		}

		return user;

	}

}
