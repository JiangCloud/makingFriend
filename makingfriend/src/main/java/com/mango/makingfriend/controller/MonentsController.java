package com.mango.makingfriend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mango.makingfriend.model.Comments;
import com.mango.makingfriend.model.CommentsVo;
import com.mango.makingfriend.model.JsonBean;
import com.mango.makingfriend.model.Message;
import com.mango.makingfriend.model.Monents;
import com.mango.makingfriend.model.MonentsVo;
import com.mango.makingfriend.model.Praise;
import com.mango.makingfriend.model.PraiseVo;
import com.mango.makingfriend.model.ResponseMessage;
import com.mango.makingfriend.model.ResponseBean;
import com.mango.makingfriend.model.User;
import com.mango.makingfriend.service.CommentsService;
import com.mango.makingfriend.service.MonentsService;
import com.mango.makingfriend.service.PraiseService;
import com.mango.makingfriend.service.UserService;
import com.mango.makingfriend.util.VOPOConverter;

@Controller
public class MonentsController {

	private MonentsService monentsService;

	private UserService userService;
	
	private CommentsService commentsService;
	private PraiseService praiseService;
    private static Log log=	LogFactory.getLog(MonentsController.class);

	public UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MonentsService getMonentsService() {
		return monentsService;
	}
	
	
	

	public CommentsService getCommentsService() {
		return commentsService;
	}
	
	
   @Autowired
	public void setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
	}

	@Autowired
	public void setMonentsService(MonentsService monentsService) {
		this.monentsService = monentsService;
	}
	
	
	

	public PraiseService getPraiseService() {
		return praiseService;
	}
    @Autowired
	public void setPraiseService(PraiseService praiseService) {
		this.praiseService = praiseService;
	}

    /***
     * 
     * @param pageNum
     * @param pageSize
     * @param userId 用户id
     * @return JsonBean
     * 获取前二十条朋友有圈数据 
     */
	@RequestMapping(value = "getMonentInfo",method=RequestMethod.POST)
	public @ResponseBody JsonBean getMonentInfo(String pageNum, String pageSize,String userId) {
	
	
		JsonBean<MonentsVo> jsonBean = new JsonBean<MonentsVo>();
 
		if (!StringUtils.isEmpty(pageNum) && !StringUtils.isEmpty(pageSize)) {

			List<Monents> monents = monentsService.getMonentInfo(
					Integer.parseInt(pageNum), Integer.parseInt(pageSize));
			
			List<MonentsVo> monentsVoes=new ArrayList<MonentsVo>();

			if (monents.size() > 0 && monents != null) {

				for (Monents monent : monents) {

					MonentsVo monentsVo = VOPOConverter.copyProperties(monent,MonentsVo.class);
					monentsVo.setUsernick(monent.getUser().getNick());//呢称
					monentsVo.setUserId(monent.getUser().getHxid());//用户id
					monentsVo.setAvatar(monent.getUser().getAvatar());//用户头像
					monentsVo.setFxid(monent.getUser().getFxid());
					Set<Comments> comments = monent.getComments();
					Set<Praise> praises = monent.getPraise();
					Set<CommentsVo> CommentsVoes =new HashSet<CommentsVo>() ;
					Set<PraiseVo> PraiseVoes =new HashSet<PraiseVo>() ;
					
					//获取moment 对应的comment
					for (Comments comment : comments) {

						CommentsVo commentsVo = VOPOConverter.copyProperties(comment, CommentsVo.class);
						commentsVo.setUserId(comment.getUser().getHxid());
						commentsVo.setNickname(comment.getUser().getNick());
						commentsVo.setFxid(comment.getUser().getFxid());
						CommentsVoes.add(commentsVo);
					}
					//获取moment 对应的praises
					for (Praise praise : praises) {

						PraiseVo praiseVo = new PraiseVo();
						praiseVo.setPid(praise.getPid());
						praiseVo.setUserId((praise.getUser().getHxid()));
						praiseVo.setNickname(praise.getUser().getNick());
						praiseVo.setFxid(praise.getUser().getFxid());
						PraiseVoes.add(praiseVo);
					}
					
					
					monentsVo.setComment(CommentsVoes);
					monentsVo.setPraises(PraiseVoes);
					monentsVoes.add(monentsVo);
				}
				String backUrl=userService.findbyUseId(userId);
				if(StringUtils.isEmpty(backUrl)){
					backUrl="";
				}
				
				jsonBean.setRet_msg("success");
				jsonBean.setRet_code(200);
				jsonBean.setBackgroundImage(backUrl);//背景图url
				jsonBean.setRet_data(monentsVoes);
				jsonBean.setTime(new Date());
				
				return jsonBean;
			}

		}
		jsonBean.setRet_msg("出现未知错误");
		jsonBean.setRet_code(500);
		return jsonBean;

	}
	
	/***
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "fetchOtherTimeline")
	public @ResponseBody JsonBean getOneMonentInfo(String pageNum, String pageSize,String userId) {
	
	
		JsonBean<MonentsVo> jsonBean = new JsonBean<MonentsVo>();
 
		if (!StringUtils.isEmpty(pageNum) && !StringUtils.isEmpty(pageSize)&&!StringUtils.isEmpty(userId)) {

			List<Monents> monents = monentsService.getOneMonentInfo(
					Integer.parseInt(userId),Integer.parseInt(pageNum), Integer.parseInt(pageSize));
			
			List<MonentsVo> monentsVoes=new ArrayList<MonentsVo>();

			if ( monents!= null) {

				for (Monents monent : monents) {

					MonentsVo monentsVo = VOPOConverter.copyProperties(monent,MonentsVo.class);
					monentsVo.setUsernick(monent.getUser().getNick());//呢称
					monentsVo.setUserId(monent.getUser().getHxid());//用户id
					monentsVo.setAvatar(monent.getUser().getAvatar());//用户头像
					monentsVo.setFxid(monent.getUser().getFxid());
					Set<Comments> comments = monent.getComments();
					Set<Praise> praises = monent.getPraise();
					Set<CommentsVo> CommentsVoes =new HashSet<CommentsVo>() ;
					Set<PraiseVo> PraiseVoes =new HashSet<PraiseVo>() ;
					
					//获取moment 对应的comment
					for (Comments comment : comments) {

						CommentsVo commentsVo = VOPOConverter.copyProperties(comment, CommentsVo.class);
						commentsVo.setUserId(comment.getUser().getHxid());
						commentsVo.setNickname(comment.getUser().getNick());
						commentsVo.setFxid(comment.getUser().getFxid());
						CommentsVoes.add(commentsVo);
					}
					//获取moment 对应的praises
					for (Praise praise : praises) {

						PraiseVo praiseVo = new PraiseVo();
						praiseVo.setPid(praise.getPid());
						praiseVo.setUserId((praise.getUser().getHxid()));
						praiseVo.setNickname(praise.getUser().getNick());
						praiseVo.setFxid(praise.getUser().getFxid());
						PraiseVoes.add(praiseVo);
					}
					
					
					monentsVo.setComment(CommentsVoes);
					monentsVo.setPraises(PraiseVoes);
					monentsVoes.add(monentsVo);
				}
				String backUrl=userService.findbyUseId(userId);
				if(StringUtils.isEmpty(backUrl)){
					backUrl="";
				}
				
				jsonBean.setRet_msg("success");
				jsonBean.setRet_code(200);
				jsonBean.setBackgroundImage(backUrl);//背景图url
				jsonBean.setRet_data(monentsVoes);
				jsonBean.setTime(new Date());
				
				return jsonBean;
			}

		}
		jsonBean.setRet_msg("出现未知错误");
		jsonBean.setRet_code(500);
		return jsonBean;

	}
	
	
	
	
	
	@RequestMapping(value = "dynamicInfo")
	public @ResponseBody JsonBean getOneMonentInfo(String userId,String mid) {
	
	
		JsonBean<MonentsVo> jsonBean = new JsonBean<MonentsVo>();
 
		if (!StringUtils.isEmpty(mid)) {

			List<Monents> monents = monentsService.findOneMonentInfo(Integer.parseInt(mid));			
			
			List<MonentsVo> monentsVoes=new ArrayList<MonentsVo>();

			if (monents.size() > 0&&null!=monents) {

				for (Monents monent : monents) {

					MonentsVo monentsVo = VOPOConverter.copyProperties(monent,MonentsVo.class);
					monentsVo.setUsernick(monent.getUser().getNick());//呢称
					monentsVo.setUserId(monent.getUser().getHxid());//用户id
					monentsVo.setAvatar(monent.getUser().getAvatar());//用户头像
					monentsVo.setFxid(monent.getUser().getFxid());
					Set<Comments> comments = monent.getComments();
					Set<Praise> praises = monent.getPraise();
					Set<CommentsVo> CommentsVoes =new HashSet<CommentsVo>() ;
					Set<PraiseVo> PraiseVoes =new HashSet<PraiseVo>() ;
					
					//获取moment 对应的comment
					for (Comments comment : comments) {

						CommentsVo commentsVo = VOPOConverter.copyProperties(comment, CommentsVo.class);
						commentsVo.setUserId(comment.getUser().getHxid());
						commentsVo.setNickname(comment.getUser().getNick());
						commentsVo.setFxid(comment.getUser().getFxid());
						CommentsVoes.add(commentsVo);
					}
					//获取moment 对应的praises
					for (Praise praise : praises) {

						PraiseVo praiseVo = new PraiseVo();
						praiseVo.setPid(praise.getPid());
						praiseVo.setUserId((praise.getUser().getHxid()));
						praiseVo.setNickname(praise.getUser().getNick());
						praiseVo.setFxid(praise.getUser().getFxid());
						PraiseVoes.add(praiseVo);
					}
					
					
					monentsVo.setComment(CommentsVoes);
					monentsVo.setPraises(PraiseVoes);
					monentsVoes.add(monentsVo);
				}
				String backUrl=userService.findbyUseId(userId);
				if(StringUtils.isEmpty(backUrl)){
					backUrl="";
				}
				
				jsonBean.setRet_msg("");
				jsonBean.setRet_code(200);
				jsonBean.setBackgroundImage(backUrl);//背景图url
				jsonBean.setRet_data(monentsVoes);
				jsonBean.setTime(new Date());
				
				return jsonBean;
			}

		}
		jsonBean.setRet_msg("出现未知错误");
		jsonBean.setRet_code(500);
		return jsonBean;

	}
	
	
	
	
	
	
	
	
	
	
	
	

	// 发布朋友圈
	@RequestMapping(value = "saveMonentInfo")
	public @ResponseBody
	Message saveMonentInfo(Monents Monents, String userId) {

		JsonBean<MonentsVo> jsonBean = new JsonBean<MonentsVo>();

		Date date = new Date();
		Monents.setPublishTime(date);
		User user=new User();
		log.debug(userId+"session----------------------------");
		if(!StringUtils.isEmpty(userId)){
			
			user.setHxid(Integer.valueOf(userId));//得客户端用户id
			
	
	
		Monents.setUser(user);
		int status = monentsService.saveMonents(Monents);

		if (status == 1) {

			User userOne = userService.findById(user.getHxid());

			if (userOne != null) {

				// 把Monents的值赋给 ResponseBean
				MonentsVo monentsVo = VOPOConverter.copyProperties(Monents, MonentsVo.class);
				monentsVo.setUsernick(userOne.getNick());
				monentsVo.setAvatar(userOne.getAvatar());
				monentsVo.setUserId(user.getHxid());
				monentsVo.setFxid(userOne.getFxid());
				List<MonentsVo> MonentsVoes = new ArrayList<MonentsVo>();
				MonentsVoes.add(monentsVo);

				jsonBean.setRet_data(MonentsVoes);
				jsonBean.setRet_code(200);
				jsonBean.setTime(new Date());
			}
		}
		if(status == 0) {
			jsonBean.setRet_msg("出现未知错误");
			jsonBean.setRet_code(500);
		}
	}
		return jsonBean;

	}
	//删除朋友圈动态
	@RequestMapping(value="removeTimeline")
	public @ResponseBody
	ResponseMessage deleteMonent(String mid){
		 
		 ResponseMessage<String> responseMessage =new ResponseMessage<String>();
		 
		  if(!StringUtils.isEmpty(mid)){
			  
			  
			 String imageName=monentsService.getMonentImage(Integer.parseInt(mid));
		
			 int num= monentsService.deleteMonent(mid);
			 
			 if(num==1){
				 
				 responseMessage.setRet_data(imageName);//返回发布朋友圈的图片名称
				 responseMessage.setRet_code(200);
				 return responseMessage;
				
			
								 
				 }
			 
		 }	 
		 responseMessage.setRet_code(500);
		 return responseMessage;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//保存评论信息
   @RequestMapping(value="commentTimeline")	
	public @ResponseBody ResponseBean saveComments(Comments comment,String mid,String userId){
	   
	   
		 ResponseBean responseBean=new ResponseBean();
	     Monents monets=new Monents();
	   
	   if(!StringUtils.isEmpty(mid)&&!StringUtils.isEmpty(userId)){		   
		   monets.setMid(Integer.parseInt(mid));	 
		   comment.setCreatTime(new Date());
		   User user=new User();
		   user.setHxid(Integer.parseInt(userId));
		   
		   comment.setMonets(monets);//设置关联关系 
		   comment.setUser(user);
		  int num= commentsService.saveComments(comment);
		  if(num==1){
		
			 List<Comments> comments= commentsService.getComments(Integer.parseInt(mid));
			 
			 if(comments.size()>0&&null!=comments){
				 
				 List<CommentsVo> commentsVoes = new ArrayList<CommentsVo>();
				 
				 for(Comments commentOne:comments){
					 
					 CommentsVo commentsVo=new CommentsVo();
					
					 
					 commentsVo.setCid(commentOne.getCid()); 
					 commentsVo.setCotent(commentOne.getCotent());
					 commentsVo.setNickname(commentOne.getUser().getNick());
					 commentsVo.setUserId(commentOne.getUser().getHxid());
					 commentsVoes.add(commentsVo);
					 
					
				 }
				 responseBean.setComment(commentsVoes);
				 responseBean.setRet_code(200);
				 responseBean.setRet_msg("success");
				 return responseBean;
			 }
			  
		  }
		  
		   
	   }
	   
	   responseBean.setRet_msg("出现未知错误");
	   responseBean.setRet_code(500);
	   responseBean.setRet_msg("fail");
	     
		return responseBean;
		
		
		
	
	}
   
   //保存点赞信息
   @RequestMapping(value="praiseTimeline")
    public @ResponseBody ResponseBean savePraise(String mid,String userId){
    	  	   
        ResponseBean responseBean=new ResponseBean();
        
        Praise praise=new Praise();
        User user=new User();
        Monents monets=new Monents();
        if(!StringUtils.isEmpty(mid)&&!StringUtils.isEmpty(userId)){  
	        user.setHxid(Integer.parseInt(userId));
	        
	        monets.setMid(Integer.parseInt(mid));
	        praise.setUser(user);	
	        praise.setMonets(monets);
	        praise.setCreatTime(new Date());

	       int statue= praiseService.savePraise(praise);
	       
	       if(statue==1){
	    	   List<Praise> praises= praiseService.getPraises(Integer.parseInt(mid));
				 
				 if(praises.size()>0&&null!=praises){
					 
					 List<PraiseVo> praiseVoes = new ArrayList<PraiseVo>();
					 
					 for(Praise praiseOne:praises){
						 
						 PraiseVo praiseVo=new PraiseVo();
						 praiseVo.setPid(praiseOne.getPid());
						 praiseVo.setNickname(praiseOne.getUser().getNick());
						 praiseVo.setUserId(praiseOne.getUser().getHxid());
						 praiseVoes.add(praiseVo);
					 }
					 responseBean.setRet_msg("success");
					 responseBean.setPraises(praiseVoes);
					 responseBean.setRet_code(200);
					 return responseBean;
				 }
	    	  
				
	       }
	        
        
        }   
        responseBean.setRet_msg("出现未知错误");
 	    responseBean.setRet_code(500);	
		return responseBean;
	   
	   
	   
   }
   
   //取消点赞
   @RequestMapping(value="deletePraiseTimeline")
   public @ResponseBody ResponseBean deletePraise(String pid){
	   
	   
	   ResponseBean responseBean=new ResponseBean();
   	
   	   if(!StringUtils.isEmpty(pid)){
   		   
   		   int mid= praiseService.deletePraise(Integer.parseInt(pid));
   		   
   		   if(mid!=0){
   			  List<Praise> praises= praiseService.getPraises(mid);
   			  List<PraiseVo> praiseVoes = new ArrayList<PraiseVo>();
				 if(praises.size()>0&&null!=praises){
					 
				
					 
					 for(Praise praiseOne:praises){
						 
						 PraiseVo praiseVo=new PraiseVo();
						 praiseVo.setPid(praiseOne.getPid());
						 praiseVo.setNickname(praiseOne.getUser().getNick());
						 praiseVo.setUserId(praiseOne.getUser().getHxid());
						 
						 praiseVoes.add(praiseVo);
					 }
					
				 }
				 
				 responseBean.setPraises(praiseVoes);
				 responseBean.setRet_code(200);
				 return responseBean;
   			   
   			   
   		     }
   
   		
   		
   		
   	   }
	   
        responseBean.setRet_msg("出现未知错误");
	    responseBean.setRet_code(500);	
		return responseBean;
	   
	 
	   
   }
   

   //根据cid 删除评论
   
    @RequestMapping(value="deleteCommentTimeline")
    public @ResponseBody ResponseBean deleteComment(String cid){
    	 ResponseBean responseBean=new ResponseBean();
    	
    	if(!StringUtils.isEmpty(cid)){
    		
    		int mid=commentsService.deleteComment(Integer.parseInt(cid));
    		
    		if(mid!=0){
    			 List<Comments> comments= commentsService.getComments(mid);
    			 
    			 List<CommentsVo> commentsVoes = new ArrayList<CommentsVo>();
    			 if(comments.size()>0&&null!=comments){
    				 
    			
    				 
    				 for(Comments commentOne:comments){
    					 
    					 CommentsVo commentsVo=new CommentsVo();
    					
    					 
    					 commentsVo.setCid(commentOne.getCid()); 
    					 commentsVo.setCotent(commentOne.getCotent());
    					 commentsVo.setNickname(commentOne.getUser().getNick());
    					 commentsVo.setUserId(commentOne.getUser().getHxid());
    					 commentsVoes.add(commentsVo);
    					 
    					
    				 }
    				
    			 }
    			 responseBean.setComment(commentsVoes);
				 responseBean.setRet_code(200);
				responseBean.setRet_msg("success");
				 return responseBean;
    			
    		}

    	}
    	
    	
    	responseBean.setRet_msg("出现未知错误");
  	    responseBean.setRet_code(500);
    	
		return responseBean;
    	
    }
    
    
    //更新朋友圈背景图url
    @RequestMapping(value="uploadpic",method=RequestMethod.POST)
    public @ResponseBody
	Message updateBackImage(String userId, String backImageUrl, String avatarUrl){
  
    	
    	 Message message =new Message();
    	 int row=userService.updateBackImage(backImageUrl,userId,avatarUrl);
    	 if(row!=0){
    		 message.setRet_code(200);
    		 message.setRet_msg("success");
    		 return message;
    	 }
    	
    	
    	 message.setRet_code(500);
    	 message.setRet_msg("更新背景图失败");
    	
		return message;
    	
    	
    	
    }
    

}
