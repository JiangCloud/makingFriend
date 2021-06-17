package com.mango.makingfriend.dao;

import java.util.List;

import com.mango.makingfriend.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface CommentsDao extends JpaRepository<Comments,Integer> {

	//public int saveComments(Comments comments);

	//@Query( value = "select cid,cotent,creat_time,user_id,nick from comments  inner join  user  on user_id=hxid  where mid=?1",nativeQuery = true)


    @Query("select c.cid,c.cotent,user.nick,user.hxid from Comments c left join User user on c.user.hxid=user.hxid   where c.monets.mid=?1")
	 List<Object> getComments(int mid);

	@Query(value = "delete from comments where cid=?1", nativeQuery = true)
	@Modifying
	@Transactional
    void deleteComment(int cid);

}
