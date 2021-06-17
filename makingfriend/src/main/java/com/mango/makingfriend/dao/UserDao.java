package com.mango.makingfriend.dao;

import java.util.List;

import com. mango.makingfriend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
	
	//public Users save(Users user);

	@Query("select count(user) from User user where user.tel=?1")
	 int findbyNameOrphone(String  telPhone);

	@Query("select user.backgroundImage from User user where user.hxid=?1")
	  String findbyUseId(int userId);


	//public void delete(int id);
	
	//public Users update(Users user);
	
	//public Users updatePerson(Users user);

	
	//public Users loadById(int hxid);


	@Query("from User u where u.id=?1 and u.password=?2")
	 User loadByIdAndPassword(int id,String passwrod);


	@Query("from User u where u.username=?1")
	 User loadByName(String username);
	
	@Query("from User u where u.username=?1 and u.password=?2")
	 User loadByNameAndPwd(String username,String password);

	@Query("from User u where u.power=?1")
	  List<User> loadByPower(int power);

	@Query("from User")
	 List<User> findAll();


	//public List<Users> findAll(String page ,String size);

	@Query("update User u set u.backgroundImage=?1 where u.hxid=?2")
    @Modifying
    @Transactional
	 void updateBackImage(String backImageUrl ,int userId );


	@Query("update User u set u.avatar=?1 where u.hxid=?2")
	@Modifying
	@Transactional
	void updateAvatar(String avatarUrl ,int userId );



	@Query("select user.hxid,user.fxid,user.tel,user.userSig,user.sex,user.avatar,user.province,user.city,user.nick from User user where user.fxid=?1")
	List<Object>  getUserInfo(String fxid);
	
	//public Users findUserInfo(String fxid);
    @Query("select user.fxid,user.nick,user.avatar from User user where user.fxid=?1")
    List<Object> findUserInfo(String fxid);


	@Query("select user.fxid,user.nick,user.avatar from User user where user.fxid=?1 or user.nick=?1")
	List<Object> getUserInfoByQueryString(String queryString);

}
