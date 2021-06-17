package com.mango.makingfriend.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.mango.makingfriend.dao.CommentsDao;
import com.mango.makingfriend.model.Comments;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mango.makingfriend.dao.MonentsDao;
import com.mango.makingfriend.model.Monents;
import com.mango.makingfriend.service.MonentsService;

@Service
public class MonentsServiceImp implements MonentsService {
	
	
	public MonentsDao monentsDao;

	@Autowired
	public CommentsDao commentsDao;

	
	
	


	public MonentsDao getMonentsDao() {
		return monentsDao;
	}


   @Resource
	public void setMonentsDao(MonentsDao monentsDao) {
		this.monentsDao = monentsDao;
	}

	@Override
	public List<Monents> getMonentInfo(int pageNum, int pageSize) {

		Pageable pageable= PageRequest.of(pageNum,pageSize);

		Page<Monents> page=monentsDao.getMonentInfo( pageable);

		
		return page.getContent();
	}


	@Override
	public int saveMonents(Monents monents) {
		
		try {
			monentsDao.save(monents);
		} catch (Exception e) {
			
			e.printStackTrace();
			return 0;
		
		}
		return 1;
	}


	@Override
	public int deleteMonent(String mid) {


       if(!StringUtils.isEmpty(mid)){
           Monents monents=new Monents();
           monents.setMid(Integer.valueOf(mid));
           monentsDao.delete(monents);
         //  Comments comments=new Comments();
          // comments.setMonets(monents);
           //commentsDao.delete(monents);


       }

       // List<Comments> comments=new ArrayList<Comments>();

	    //Iterable<Comments> iterable=new Iterable<Comments>()


        //commentsDao.delete();


	
		return 1;
	}

    /****
     * 根据userId 获取朋友圈数据
     * @param userId 用户
     * @param pageNum  页数
     * @param pageSize 每页显示数
     * @return List<Monents>
     */
	@Override
	public List<Monents> getOneMonentInfo(int userId,int pageNum, int pageSize) {

        Pageable pageable= PageRequest.of(pageNum,pageSize);
        Page<Monents> page=monentsDao.getOneMonentInfo(userId, pageable);

		return   page.getContent();
	}


	@Override
	public List<Monents> findOneMonentInfo(int mid) {
		
		return monentsDao.findOneMonentInfo(mid);
	}


	@Override
	public String getMonentImage(int mid) {
		
		return monentsDao.getMonentImage(mid);
	}
	


	

}
