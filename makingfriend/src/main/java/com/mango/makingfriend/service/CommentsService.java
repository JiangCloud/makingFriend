package com.mango.makingfriend.service;

import java.util.List;

import com.mango.makingfriend.model.Comments;

public interface CommentsService {
	public int saveComments(Comments comments);
	public List<Comments> getComments(int mid);
	
    public int deleteComment(int cid);

}
