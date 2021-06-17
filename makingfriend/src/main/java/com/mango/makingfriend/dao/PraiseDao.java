package com.mango.makingfriend.dao;

import java.util.List;

import com.mango.makingfriend.model.Praise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PraiseDao extends JpaRepository<Praise,Integer> {


	// @Query
     //int savePraise(Praise praise);
    //@Query(value = "select p.pid,p.user_id,u.nick,p.creat_time from praise p inner  join user  u on p.user_id=u.hxid where p.mid=?1",nativeQuery = true)


    @Query("select p.pid,user.nick,user.hxid  from Praise p left join  User user on p.user.hxid=user.hxid where p.monets.mid=?1")
	 List<Object> getPraises(int mid) ;

	//@Query
	// int deletePraise(int pid);

}
