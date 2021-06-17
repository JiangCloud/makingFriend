package com.mango.makingfriend.service;

import java.util.List;



import com.mango.makingfriend.model.Monents;




public interface MonentsService {
	
	
	
	public List<Monents> getMonentInfo(int pageNum,int pageSize);
	public List<Monents> getOneMonentInfo(int UserId,int pageNum,int pageSize);
	public int saveMonents(Monents monents);
	public int deleteMonent(String mid);
	
	public List<Monents> findOneMonentInfo(int mid);
	public String getMonentImage(int mid);
	
	
	

}
