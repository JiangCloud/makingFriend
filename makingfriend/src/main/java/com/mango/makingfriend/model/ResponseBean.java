package com.mango.makingfriend.model;

import java.util.List;

public class ResponseBean extends BaseMessage  {
	

	private List<PraiseVo> praises;
	private List<CommentsVo> comment;
	

	public List<PraiseVo> getPraises() {
		return praises;
	}
	public void setPraises(List<PraiseVo> praises) {
		this.praises = praises;
	}
	public List<CommentsVo> getComment() {
		return comment;
	}
	public void setComment(List<CommentsVo> comment) {
		this.comment = comment;
	}

	
	
	

}
