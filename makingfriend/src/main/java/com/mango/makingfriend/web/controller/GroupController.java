package com.mango.makingfriend.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.GsonBuilder;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.devtools.generate.utils.jAngel.utils.StringUtil;
import com.mango.makingfriend.model.BaseMessage;
import com.mango.makingfriend.model.Group;
import com.mango.makingfriend.service.GroupService;
import com.mango.makingfriend.util.VOPOConverter;
import com.mango.makingfriend.util.VideoImage;
import com.mango.makingfriend.web.api.ChatGroupAPI;
import com.mango.makingfriend.web.api.SendMessageAPI;
import com.mango.makingfriend.web.api.impl.EasemobFile;
import com.mango.makingfriend.web.im.ImageMsgContent;
import com.mango.makingfriend.web.im.Size;
import com.mango.makingfriend.web.im.VideoMessage;
import com.mango.makingfriend.web.validator.GroupValid;
import com.mango.makingfriend.web.vo.*;
import io.swagger.client.model.ModifyGroup;
import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.UserName;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "group")
@Slf4j
public class GroupController {
    @Autowired
    private GroupService groupService;

    private Object object;

    private Object result;
    private Object obj;

    @Autowired
    private SendMessageAPI easemobSendMessage ;
    @Autowired
    private ChatGroupAPI easemobChatGroup  ;

    @Autowired
    private EasemobFile easemobFile;

    @RequestMapping(value = "/updateGroupName")
    @RequiresPermissions("group:updateGroupName")
    @ResponseBody
    public BaseMessage updateGroupName(String groupId, String groupName){
        boolean statue=groupService.updateGroupName(groupName,groupId);
        BaseMessage baseMessage=new BaseMessage();

        if(statue){
            baseMessage.setRet_code(200);
            baseMessage.setRet_msg("更新群名称成功");
        }else {
            baseMessage.setRet_code(500);
            baseMessage.setRet_msg("更新群名称失败");

        }
        return baseMessage;

    }

    @RequestMapping(value = "/getGroupInfo")
    @RequiresPermissions("group:getGroupInfo")
    public String getGroupInfo(Model model ,Group group){

       /* Object result= easemobChatGroup.getChatGroups("1",cursor);
        JSONObject jsonObject= JSONObject.parseObject(result.toString());
        String str= jsonObject.getString("data");

        List<GroupVo> list= JSONArray.parseArray(str, GroupVo.class);
        Page<GroupVo> groups=new PageImpl(list);*/

        // 创建匹配器，进行动态查询匹配
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<Group> example= Example.of(group,matcher);
        Page<Group> groups= groupService.getGroupInfo(example);


        model.addAttribute("page",groups);
        model.addAttribute("list",groups.getContent());

        return "/group/index";


    }

    @GetMapping(value="/add")
     public String toAddPage(){

        return "/group/add";

     }

    /**
     * 保存和更新群组信息
     *
     * @param groupValid
     * @param group
     * @return
     */
    @PostMapping("/saveGroup")
    @ResponseBody
    public ResultVo saveGroup(@Validated GroupValid groupValid, io.swagger.client.model.Group group,ModifyGroup modifyGroup){


     //   group.groupname("江华同城交流").desc("a new group")._public(true).maxusers(50).approval(false).owner("cloud");
        Object result;
        if(StringUtils.isNotEmpty(groupValid.getGroupId())){
            modifyGroup.setDescription(group.getDesc());
            result=  easemobChatGroup.modifyChatGroup(groupValid.getGroupId(),modifyGroup);
        }else{
            result = easemobChatGroup.createChatGroup(group);
        }


        if(null!=result){
            JSONObject jsonObject= JSONObject.parseObject(result.toString());
            String str= jsonObject.getString("data");

            Group groupInfo= VOPOConverter.copyProperties(groupValid, Group.class);

            if(StringUtils.isNotEmpty(str)){
                String groupid=JSONObject.parseObject(str).getString("groupid");

                //groupid不为空时进行添加操作
                if(StringUtils.isNotEmpty(groupid)){

                    groupInfo.setGroupId(groupid);
                    groupInfo.setType("group");
                    groupInfo.setAffiliations(1);
                    groupInfo.setGroupName(groupValid.getGroupname());
                    groupInfo.setCreated(jsonObject.getLong("timestamp"));

                    Group groups=groupService.save(groupInfo);
                    if(null==groups){

                        return ResultVoUtil.error(500,"保存群组信息失败");


                    }
                }

                ModifyGroupVo modifyGroupVo= JSONObject.parseObject(str, ModifyGroupVo.class);
                //
                if(null!=modifyGroupVo&&null!=modifyGroup.getGroupname()&&null!=modifyGroup.getMaxusers()&&null!=modifyGroup.getDescription()){

                    if(!modifyGroupVo.getDescription()||!modifyGroupVo.getGroupname()||!modifyGroupVo.getMaxusers()){

                        return ResultVoUtil.error(500,"更新群组信息失败");
                    }


                    if(modifyGroupVo.getGroupname()){
                        groupInfo.setGroupName(modifyGroup.getGroupname());

                    }
                    groupInfo.setLast_modified(jsonObject.getLong("timestamp"));

                  /*  if(modifyGroupVo.getDescription()){
                        groupInfo.setGroupName(modifyGroup.getDescription());
                    }*/

                  /*if(modifyGroupVo.getMaxusers()){
                        groupInfo.set(modifyGroup.getDescription());
                    }*/


                    boolean flag=groupService.updateGroup(groupInfo);

                    if(flag){
                        return ResultVoUtil.success("更新群组信息成功");
                    }

                    return ResultVoUtil.error(500,"更新群组信息失败");
                }



            }

        }

        log.debug(result.toString());
        return ResultVoUtil.SAVE_SUCCESS;
    }

    @RequestMapping("/editGroupInfo/{groupId}")
     public  String getOneGroup(@PathVariable("groupId") String[] groupId,Model model){

      //  String[] gruops={"133714433605633"};

        Object result=  easemobChatGroup.getChatGroupDetails(groupId);
        JSONObject jsonObject= JSONObject.parseObject(result.toString());
        String jsonStr= jsonObject.getString("data");

        List<GroupVo> group=  JSONArray.parseArray(jsonStr,GroupVo.class);
        if(null!=group&&group.size()>0){

            model.addAttribute("group",group.get(0));
        }

        log.info(result.toString());


      return "/group/add";

     }

/*
    @RequestMapping("value=/saveGroupInfo")
     public  void saveGroupInfo(){
        Object result= easemobChatGroup.getChatGroups("","");
        JSONObject jsonObject= JSONObject.parseObject(result.toString());
        String str= jsonObject.getString("data");


        List<Group> groups= JSONArray.parseArray(str, Group.class);


        groupService.saveGroup(groups);
    }*/

    /**
     * 根据groupId删队群组
     * @param param
     * @param ids groupId
     * @return
     */
    @RequestMapping("/deleteGroup/{param}")
    @ResponseBody
    @RequiresPermissions("system:role:status")
    public ResultVo deleteGroup(@PathVariable("param")String param, @RequestParam(value = "ids", required = false)
                                List<String> ids){


        List<Group>  groups=new ArrayList<>();
        for(String groupId:ids){
            Group group1=new Group();
            group1.setGroupId(groupId);
            groups.add(group1);
       }

        boolean flag=groupService.deleteGroup(groups);

        if(flag){
            try {

                for(Group group :groups ){
                    easemobChatGroup.deleteChatGroup(group.getGroupId());

                }

            } catch (Exception e) {
                e.printStackTrace();
                return ResultVoUtil.error("删除失败");
            }

            return ResultVoUtil.success("删除成功");
        }
        return ResultVoUtil.error("删除失败");



    }


     @RequestMapping("/getGroupUsers/{groupId}")
     public String getGroupUsers(@PathVariable("groupId")String groupId,Model model){
         Object object=easemobChatGroup.getChatGroupUsers(groupId);
         JSONObject jsonObject= JSONObject.parseObject(String.valueOf(object));
         if(null!=jsonObject){
             String jsonStr= jsonObject.getString("data");
             List<GroupUserVo> groupUsers=JSON.parseArray(jsonStr,GroupUserVo.class);
             model.addAttribute("list",groupUsers);
         }

         model.addAttribute("groupId",groupId);

        return "/group/userList";

     }


     @RequestMapping("/addUserGroup")
     @ResponseBody
     public  ResultVo addUserGroup(String userId, String groupId){
/*

    {
        "action" : "post",
         "application" : "e60f7200-3b04-11e8-8ad4-0584c9067623",
            "uri" : "http://a1.easemob.com/1159180408228899/lottery/chatgroups/149220401152002/users",
            "entities" : [ ],
        "data" : {
        "newmembers" : [ "cloud" ],
        "groupid" : "149220401152002",
         "action" : "add_member"
    },
        "timestamp" : 1621969971338,
            "duration" : 0,
            "organization" : "1159180408228899",
            "applicationName" : "lottery"
    }
*/
         Object object=  easemobChatGroup.addSingleUserToChatGroup(groupId,userId);
         JSONObject jsonObject= JSONObject.parseObject(String.valueOf(object));

         if(null!=jsonObject){
             //String error= jsonObject.getString("error");
             String data= jsonObject.getString("data");
             if(StringUtils.isNotEmpty(data)){
                 String result=JSONObject.parseObject(data).getJSONArray("newmembers").getString(0);
                 if( result.equals(userId)){
                     return   ResultVoUtil.success("添加用户进群成功");
                 }
             }
         }

         return ResultVoUtil.error("添加用户进群失败");


    }

    @RequestMapping("/deleteGroupUser")
    @ResponseBody
    public ResultVo deleteGroupUser(String groupId,@RequestParam(value = "ids")
            String[] ids) {

        if (ids.length == 1) {
            object = easemobChatGroup.removeSingleUserFromChatGroup(groupId, ids[0]);
        }

        if (ids.length > 1) {
            object = easemobChatGroup.removeBatchUsersFromChatGroup(groupId, ids);

        }

        JSONObject jsonObject = JSONObject.parseObject(String.valueOf(object));
        if (null != jsonObject) {
            String str = jsonObject.getString("data");
            boolean result = false;
            if (StringUtils.isNotEmpty(str)) {
                if (ids.length == 1) {
                    result = JSON.parseObject(str).getBoolean("result");

                    if (result) {
                        return ResultVoUtil.success("移除群成员成功！");
                    }

                    return ResultVoUtil.error(500, "移除群成员失败!");
                }


                if (ids.length > 1) {

                    List<RemoveGroupUserVo> removeGroupUserVos = JSON.parseArray(str, RemoveGroupUserVo.class);

                    List<Boolean> flags = new ArrayList<>();

                    for (RemoveGroupUserVo removeGroupUserVo : removeGroupUserVos) {
                        flags.add(removeGroupUserVo.isResult());

                    }

                    if(flags.contains(false)) {
                        return ResultVoUtil.error(500, "移除群成员失败!");

                    }
                    return ResultVoUtil.success("移除群成员成功！");
                  }

                }

          }
           return ResultVoUtil.error(501, "系统繁忙，请用稍后再试!");

        }

        @RequestMapping(value = "/toSendMessagePage")
        public String toSendMessagePage(@RequestParam(value ="ids",required = false) List<String> ids,Model model){

        model.addAttribute("groupIds",ids);

        return "/group/sendMessage";


        }

    /***
     * 发送文字 图片消息
     * @param groupId
     * @param message
     * @param sendMessageVo
     * @return
     */

        @RequestMapping("/sendMessage")
        @ResponseBody
        public ResultVo sendMessage( @RequestParam(value = "groupId",required = false) List<String> groupId,String message,SendMessageVo sendMessageVo){

            if(StringUtils.isEmpty(message)&&StringUtils.isEmpty(sendMessageVo.getUri())){
                return ResultVoUtil.error("至少发送一个文字或者图片消息");



            }

            Msg msg = new Msg();
            UserName userName = new UserName();
            //userName.add("46201835683841");
            //userName.add("cloud");
            //userName.add("133714433605633");
             if(groupId.size()>0){
                userName.addAll(groupId);
                //发送文字消息
                if(StringUtils.isNotEmpty(message)){
                    MsgContent msgContent = new MsgContent();
                    msgContent.type(MsgContent.TypeEnum.TXT).msg(message);

                    Map<String,Object> ext = new HashMap<>();
                    ext.put("test_key","test_value");
                    msg.target(userName).targetType("chatgroups").msg(msgContent).setExt(ext);
                    // msg.from("客服").target(userName).targetType("chatrooms").msg(msgContent).setExt(ext);
                    //msg.from("客服").target(userName).targetType("users").msg(msgContent).setExt(ext);
                    System.out.println(new GsonBuilder().create().toJson(msg));
                     object = easemobSendMessage.sendMessage(msg);


                    System.out.println(object);
                }
                //发送图片消息
                if(StringUtils.isNotEmpty(sendMessageVo.getUri())&&sendMessageVo.getType().equals("image")){

                    ImageMsgContent imageContent = new ImageMsgContent();
                    String url= sendMessageVo.getUri()+"/"+sendMessageVo.getUuid();
                    imageContent.setUrl(url);
                    imageContent.setFilename("filename");
                    imageContent.setSize(new Size(480, 720));
                    imageContent.msg("this is an image message");
                    imageContent.setSecret(sendMessageVo.getShare_secret());
                    imageContent.type(MsgContent.TypeEnum.IMG);

                    msg.target(userName).targetType("chatgroups").msg(imageContent);
                    result = easemobSendMessage.sendMessage(msg);
                    System.out.println(result);
                }
                 if(StringUtils.isNotEmpty(sendMessageVo.getUri())&&sendMessageVo.getType().equals("video")){
                     VideoMessage videoMessage=new VideoMessage();

                     String url= sendMessageVo.getUri()+"/"+sendMessageVo.getUuid();
                     String thumb=sendMessageVo.getUri()+"/"+sendMessageVo.getThumb();
                     videoMessage.setThumb(thumb);
                     videoMessage.setThumb_secret(sendMessageVo.getThumbSecret());
                     videoMessage.setUrl(url);
                     videoMessage.setFilename("videoFile.mp4");
                     videoMessage.msg("this is an video message");
                     videoMessage.setSecret(sendMessageVo.getShare_secret());
                     videoMessage.type(MsgContent.TypeEnum.VIDEO);


                     msg.target(userName).targetType("chatgroups").msg(videoMessage);
                      obj = easemobSendMessage.sendMessage(msg);
                     System.out.println(obj);
                 }


                 JSONObject jsonObject = JSONObject.parseObject(String.valueOf(object));
                 JSONObject jsonObject1 = JSONObject.parseObject(String.valueOf(result));
                 JSONObject jsonObject2 = JSONObject.parseObject(String.valueOf(obj));
                 List<String> responeResult=new ArrayList<>();
                 if (null != jsonObject) {
                     String str = jsonObject.getString("data");
                     if(StringUtils.isNotEmpty(str)){
                         for(String gId:groupId){

                             responeResult.add(JSON.parseObject(str).getString(gId));
                         }


                     }


                 }

                 if(null != jsonObject1){
                     String str = jsonObject1.getString("data");
                     if(StringUtils.isNotEmpty(str)){
                         for(String gId:groupId){

                             responeResult.add(JSON.parseObject(str).getString(gId));
                         }


                     }

                 }

                 if(null != jsonObject2){
                     String str = jsonObject2.getString("data");
                     if(StringUtils.isNotEmpty(str)){
                         for(String gId:groupId){

                             responeResult.add(JSON.parseObject(str).getString(gId));
                         }


                     }

                 }

                 if(responeResult.contains("success")){
                     return ResultVoUtil.success("发送成功！");
                 }


            }
            return ResultVoUtil.success("发送失败！");


/*
            {"target_type":"chatgroups","target":["149221916344321","149220401152002"],"msg":{"type":"txt","msg":"gfg"},"from":"客服","ext":{"test_key":"test_value"}}
            {"path":"/messages","uri":"http://a1.easemob.com/1159180408228899/lottery/messages","timestamp":1622435001379,"organization":"1159180408228899","application":"e60f7200-3b04-11e8-8ad4-0584c9067623","action":"post",
            "data":{"149220401152002":"success","149221916344321":"success"},"duration":0,"applicationName":"lottery"}

*/

        }

      @RequestMapping("/uploadImage")
      @ResponseBody
      public ResultVo uploadImage(@RequestParam(value = "image") MultipartFile multipartFile, HttpServletRequest request){

          try {

              if(!multipartFile.isEmpty()){

                  System.out.println(multipartFile.getContentType()+":"+multipartFile.getName());
                  //真实文件名
                  System.out.println(multipartFile.getOriginalFilename());
                  String path = request.getSession().getServletContext().getRealPath("/upload")+File.separator;
                  String name = path + multipartFile.getOriginalFilename();
                  FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(name));

                  Object result=easemobFile.uploadFile(new File(name));


                  System.out.println(String.valueOf(result));
                  SendMessageVo sendMessageVo=new SendMessageVo();

                  //缩略图的相关操作
                  if(multipartFile.getContentType().equals("video/mp4")){
                      VideoImage videoImage=new VideoImage();
                      try {
                          //缩略图的路径
                         String filePath= videoImage.randomGrabberFFmpegImage(name,VideoImage.MOD);

                          Object object=easemobFile.uploadFile(new File(filePath));

                          JSONObject jsonObject = JSONObject.parseObject(String.valueOf(object));
                          JSONArray jsonArray= JSON.parseArray( jsonObject.getString("entities"));

                          String thumb=jsonArray.getJSONObject(0).getString("uuid");
                          String thumbSecret= jsonArray.getJSONObject(0).getString("share-secret");

                          sendMessageVo.setThumb(thumb);
                          sendMessageVo.setThumbSecret(thumbSecret);
                          System.out.println(String.valueOf(object));
                      } catch (Exception e) {
                          e.printStackTrace();
                      }

                  }



                  JSONObject jsonObject = JSONObject.parseObject(String.valueOf(result));
                  //SendMessageVo sendMessageVo = JSON.parseObject(String.valueOf(result), SendMessageVo.class);

                  if (null != jsonObject) {

                      JSONArray jsonArray= JSON.parseArray( jsonObject.getString("entities"));

                      String uuid=jsonArray.getJSONObject(0).getString("uuid");
                      String secret= jsonArray.getJSONObject(0).getString("share-secret");

                      sendMessageVo.setUri(jsonObject.getString("uri"));
                      sendMessageVo.setShare_secret(secret);
                      sendMessageVo.setUuid(uuid);
                      return ResultVoUtil.success(sendMessageVo);
                  }

              }
         /*     {"path":"/chatfiles","uri":"http://a1.easemob.com/1159180408228899/lottery/chatfiles","timestamp":1622527356189,"organization":"1159180408228899","application":"e60f7200-3b04-11e8-8ad4-0584c9067623","entities":[{"uuid":"f68b7cd0-c29e-11eb-9c4a-4b8dab472148","type":"chatfile","share-secret":"9ouj4MKeEeuQ7XO3m6VN1qSDDZFEtnWB-w_f4LcpIjfqft2Z"}],"action":"post","duration":21,"applicationName":"lottery"}
*/

          } catch (IOException e) {
              e.printStackTrace();
          }

          return ResultVoUtil.error("上传图片失败");


      }


      @RequestMapping("/sendImage")
      @ResponseBody
      public  ResultVo sendImage(){


        return ResultVoUtil.error();




      }


}




