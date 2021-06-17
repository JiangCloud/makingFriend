package com.mango.makingfriend.service.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.mango.makingfriend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mango.makingfriend.dao.PraiseDao;
import com.mango.makingfriend.model.Praise;
import com.mango.makingfriend.service.PraiseService;

@Service
@Slf4j
public class PraiseServiceImp implements PraiseService{
	
	private PraiseDao praiseDao;
	
	

	public PraiseDao getPraiseDao() {
		return praiseDao;
	}


    @Autowired
	public void setPraiseDao(PraiseDao praiseDao) {
		this.praiseDao = praiseDao;
	}



	@Override
	public int savePraise(Praise praise) {




		try {
			Praise 	p  = praiseDao.save(praise);
			if( p!=null){
				return 1;

			}
		} catch (Exception e) {
			log.error("保存点赞失败"+e.getMessage());
			e.printStackTrace();
			return 0;
		}


		return 1;
	}


	@Override
	public List<Praise> getPraises(int mid) {

	  List<Object> objects=praiseDao.getPraises(mid);

	  List<Praise> praises=new ArrayList<Praise>();

		if(objects!=null) {
			for (Object o : objects) {
                Praise praise=new Praise();
				User user=new User();
				Object[] users = (Object[]) o;
				praise.setPid(String.valueOf(users[0])==null?0:Integer.parseInt(String.valueOf(users[0])));
				user.setNick(String.valueOf(users[1]));
				user.setHxid(String.valueOf(users[2])==null?0:Integer.parseInt(String.valueOf(users[2])));
				praise.setUser(user);
				praises.add(praise);

			}
		}




	
		return praises;
	}


	@Override
	public int deletePraise(int pid) {
		//Praise praise=new Praise();
		//praise.setPid(pid);
        Praise praise=praiseDao.getOne(pid);
        int mid=praise.getMonets().getMid();
		try {
			praiseDao.delete(praise);
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;

		}
		return mid;

	}

}
