package com.mango.makingfriend.service;

import java.util.List;

import com.mango.makingfriend.model.Praise;

public interface PraiseService {
	 int savePraise(Praise praise);
	 List<Praise> getPraises(int mid);
	 int deletePraise(int pid);
}
