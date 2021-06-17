package com.mango.makingfriend.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mango.makingfriend.model.Praise;
import com.mango.makingfriend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mango.makingfriend.dao.CommentsDao;
import com.mango.makingfriend.model.Comments;
import com.mango.makingfriend.service.CommentsService;

@Service
@Slf4j
public class CommentServiceImpl implements CommentsService {
	
	private CommentsDao commentsDao;
	
	
	

	public CommentsDao getCommentsDao() {
		return commentsDao;
	}


  
    @Autowired
	public void setCommentsDao(CommentsDao commentsDao) {
		this.commentsDao = commentsDao;
	}




	@Override
	public int saveComments(Comments comments) {

	Comments comments1=	commentsDao.save(comments);

		if(comments1!=null){
			return 1;


		}

		return 0;
		
		//return commentsDao.saveComments(comments);
	}



	@Override
	public List<Comments> getComments(int mid) {
	  List<Object> objects=commentsDao.getComments(mid);

		List<Comments> comments=new ArrayList<Comments>();

		if(objects!=null) {
			for (Object o : objects) {
				Comments comment=new Comments();
				User user=new User();
				Object[] users = (Object[])o;
				comment.setCid(String.valueOf(users[0])==null?0:Integer.parseInt(String.valueOf(users[0])));
				comment.setCotent(String.valueOf(users[1]));
				user.setNick(String.valueOf(users[2]));
				user.setHxid(String.valueOf(users[3])==null?0:Integer.parseInt(String.valueOf(users[3])));
				comment.setUser(user);
				comments.add(comment);

			}
		}

		return comments;
	}



	@Override
	public int deleteComment(int cid) {


		Comments comments=commentsDao.getOne(cid);
		int mid=comments.getMonets().getMid();
		try {
			commentsDao.deleteComment(cid);
		}catch (Exception e){

			log.error( e.getMessage());
			return 0;


		}

	
		return mid;
	}
	
	

}
