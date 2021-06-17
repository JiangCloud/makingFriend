package com.mango.makingfriend.util;



import com.github.dozermapper.core.DozerBeanMapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.mango.makingfriend.model.Monents;
import com.mango.makingfriend.model.ResponseBean;
import com.sun.org.apache.xerces.internal.xs.StringList;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class VOPOConverter {


    @Resource
   private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	/**
	 * 普通对象转换 比如: ADO -> AVO
	 * @param: [source 源对象, destinationClass 目标对象class]
	 * @return: T
	 * @author: zhuoli
	 * @date: 2018/9/30 18:13
	 */
	public static <T> T copyProperties(Object source, Class<T> destinationClass) {
		if (source == null) {
			return null;
		}
		return mapper.map(source, destinationClass);
	}

    public static  void   copyProperties(Object source, Object object) {
        mapper.map(source, object);
    }


    /**
	 * List转换 比如: List<ADO> -> List<AVO>
	 * @param: [sourceList 源对象List, destinationClass 目标对象class]
	 * @return: java.util.List<T>
	 * @author: zhuoli
	 * @date: 2018/9/30 18:14
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
		List<T> destinationList = new ArrayList();
		if (sourceList == null) {
			return destinationList;
		}
		for (Object sourceObject : sourceList) {
			if (sourceObject != null) {
				T destinationObject = mapper.map(sourceObject, destinationClass);
				destinationList.add(destinationObject);
			}
		}
		return destinationList;
	}

	public static void main(String[] args) {
		
		ResponseBean responseBean=new ResponseBean();
	
		
		Monents monent=new Monents();
		
		monent.setContent("hell");



	//	ResponseBean s = VOPOConverter.copyProperties(monent, ResponseBean.class);
		//System.out.println(s.getContent());
/*
		UserVo vo = new UserVo();
		vo.setAge(21);
		vo.setUsername("kmie");
		vo.setId(3);
		Users us = new Users();
		VOPOConverter.copyProperties(vo, us);
		System.out.println(us.getAge() + us.getUsername());
		UserVo s = VOPOConverter.copyProperties(us, UserVo.class);
		System.out.println(s.getAge() + s.getUsername());*/

	}


}
