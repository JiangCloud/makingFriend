package com.mango.makingfriend;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.mango.makingfriend.dao.GroupDao;
import com.mango.makingfriend.web.api.ChatGroupAPI;
import com.mango.makingfriend.web.api.IMUserAPI;
import com.mango.makingfriend.web.api.SendMessageAPI;
import com.mango.makingfriend.web.vo.GroupVo;
import com.mango.makingfriend.web.vo.ModifyGroupVo;
import com.tls.tls_sigature.tls_sigature;
import io.swagger.client.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.DataFormatException;

@SpringBootTest
@Slf4j
class MakingfriendApplicationTests {

    @Autowired
    private IMUserAPI easemobIMUsers;

   @Autowired
    private SendMessageAPI easemobSendMessage ;

   @Autowired
    private ChatGroupAPI easemobChatGroup  ;

    @Autowired
    private GroupDao groupDao;

    @Test
    void contextLoads() {

    }

    @Test
     void createUser()  {
        RegisterUsers users = new RegisterUsers();
        User user = new User().username("cloud" + new Random().nextInt(500)).password("a123456");
        // Users user1 = new Users().username("aaa123456" + new Random().nextInt(500)).password("123456");
        users.add(user);
        // users.add(user1);
        Object result = easemobIMUsers.createNewIMUserSingle(users);

        log.info(result.toString());

    }


    @Test
     void sendText() {
        Msg msg = new Msg();
        MsgContent msgContent = new MsgContent();
        msgContent.type(MsgContent.TypeEnum.TXT).msg("helloWord");
        UserName userName = new UserName();
        userName.add("46201835683841");
      //  userName.add("cloud");
        Map<String,Object> ext = new HashMap<>();
        ext.put("test_key","test_value");
        msg.from("客服").target(userName).targetType("chatrooms").msg(msgContent).setExt(ext);
       // msg.from("客服").target(userName).targetType("users").msg(msgContent).setExt(ext);


        System.out.println(new GsonBuilder().create().toJson(msg));
        Object result = easemobSendMessage.sendMessage(msg);
        System.out.println(result);
    }






    @Test
    public void sendImage() {
        Msg msg = new Msg();

        ImageMsgContent msgContent = new ImageMsgContent();
        msgContent.url("http://a1.easemob.com/1159180408228899/lottery/chatfiles/35e90350-c21f-11eb-8d06-0b9c65b77ae5").secret("test_sec").filename("filename").size(new ImageMsgContent.Size(480, 720))
                .type(MsgContent.TypeEnum.IMG).msg("this is an image message");
        UserName userName = new UserName();
        userName.add("cloud");
        userName.add("jty");
        msg.target(userName).targetType("users").msg(msgContent);
        System.out.println(new GsonBuilder().create().toJson(msg));
        Object result = easemobSendMessage.sendMessage(msg);
        System.out.println(result);
    }


    static class ImageMsgContent extends MsgContent {
        private String url;
        private String filename;
        private String secret;
        private Size size;

        ImageMsgContent url(String url) {
            this.url = url;
            return this;
        }

        ImageMsgContent filename(String filename) {
            this.filename = filename;
            return this;
        }

        ImageMsgContent secret(String secret) {
            this.secret = secret;
            return this;
        }

        ImageMsgContent size(Size size) {
            this.size = size;
            return this;
        }

        static class Size {
            private long width;
            private long height;

            Size(long width, long height) {
                this.width = width;
                this.height = height;
            }
        }
    }




    @Test
    public void createGroup() {
        Group group = new Group();
        group.groupname("江华同城交流").desc("a new group")._public(true).maxusers(50).approval(false).owner("cloud");
        Object result = easemobChatGroup.createChatGroup(group);
        log.info(result.toString());
    }

    @Test
    public void addUsers() {
        String groupId = "131270546882561";
        UserNames userNames = new UserNames();
        UserName userList = new UserName();
        userList.add("yun");
        userList.add("cloud35");
        userNames.usernames(userList);
        Object result = easemobChatGroup.addBatchUsersToChatGroup(groupId, userNames);
        log.info(result.toString());
    }

    @Test
   public void getGroup(){
        //Object result=  easemobChatGroup.getChatGroups("1","ZGNiMjRmNGY1YjczYjlhYTNkYjk1MDY2YmEyNzFmODQ6aW06Z3JvdXA6MTE1OTE4MDQwODIyODg5OSNsb3R0ZXJ5OjI");
        Object result=  easemobChatGroup.getChatGroups("","");
        JSONObject jsonObject= JSONObject.parseObject(result.toString());
        String str= jsonObject.getString("data");


        log.info(result.toString());
        log.info(str);
        List<com.mango.makingfriend.model.Group> list= JSONArray.parseArray(str, com.mango.makingfriend.model.Group.class);
       // List<GroupVo> list= JSONArray.parseArray(str,GroupVo.class);

       List<com.mango.makingfriend.model.Group> groups= groupDao.saveAll(list);


       System.out.println("=========="+groups.get(0).getLast_modified());


       /* SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd:mm:ss");

        String date= simpleDateFormat.format(new Date(Long.parseLong("1609071173054")));


*/
        System.out.println("=========="+list.get(0).getGroupId());
    }

    @Test
    public void getGroupDetail(){


        String[] gruops={"133714433605633"};


        Object result=  easemobChatGroup.getChatGroupDetails(gruops);
        JSONObject jsonObject= JSONObject.parseObject(result.toString());
        String str= jsonObject.getString("data");

        List<GroupVo> groupVo=  JSONArray.parseArray(str,GroupVo.class);
        log.info(result.toString());


    }

    @Test
    public void TestUpdateGroup(){

        String groupId="133714433605633";

        ModifyGroup modifyGroup=new ModifyGroup();
        modifyGroup.groupname("开心家族！！").description("天天开心");

        Object  result=  easemobChatGroup.modifyChatGroup(groupId,modifyGroup);

        JSONObject jsonObject= JSONObject.parseObject(result.toString());
        String str= jsonObject.getString("data");
        ModifyGroupVo modifyGroupVo= JSONObject.parseObject(str, ModifyGroupVo.class);




        log.info(result.toString());

    }






    @Test
    void genAndVerify() {
        try {
            //Use pemfile keys to test
            String privStr = "-----BEGIN PRIVATE KEY-----\n" +
                    "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgkTfHxPa8YusG+va8\n" +
                    "1CRztNQBOEr90TBEjlQBZ5d1Y0ChRANCAAS9isP/xLib7EZ1vS5OUy+gOsYBwees\n" +
                    "PMDvWiTygPAUsGZv1PHLoa0ciqsElkO1fMGwNrzOKJx1Oo194Ri+SypV\n" +
                    "-----END PRIVATE KEY-----\n";

            //change public pem string to public string
            String pubStr = "-----BEGIN PUBLIC KEY-----\n" +
                    "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEvYrD/8S4m+xGdb0uTlMvoDrGAcHn\n" +
                    "rDzA71ok8oDwFLBmb9Txy6GtHIqrBJZDtXzBsDa8ziicdTqNfeEYvksqVQ==\n" +
                    "-----END PUBLIC KEY-----\n";

            // generate signature
            tls_sigature.GenTLSSignatureResult result = tls_sigature.GenTLSSignatureEx(1400007211, "cloud", privStr);
            //  Assert(null, result);

            System.out.println(result.urlSig);

            System.out.println(result.expireTime);

            //  Assert.assertNotEquals(null, result.urlSig);
            // Assert.assertNotEquals(0, result.urlSig.length());
String sig="eJw1j11rgzAYRv*L12VEG5UVdjFqHPYDK60zuwoxeZNlUxtcSi1j-31W7O05F895fr3T7vjErTWScceWvfRWHvIWE4bBmh4YVw76EfthGAYIPayR0DmjzOREc77IWfwYPZI9KddZsYbkkx4Op6H9enV0o1LVN1DhQhtUCcq7ZvtGdunVmfdaZ7oiAEht6RXVjRioH-E0sm2ZHElZ5FBb0ibZJtd4mX*8PMbkN5vy74F4rENx4PuzdKaFO48QRvg5DuKZcyHOl84xd7Mw-f37B9CtURU_";
            // check signature
            tls_sigature.CheckTLSSignatureResult checkResult = tls_sigature.CheckTLSSignatureEx(sig, 1400007211, "cloud", pubStr);
            // Assert.assertNotEquals(null, checkResult);
            // Assert.assertTrue(checkResult.verifyResult);
            System.out.println(checkResult.verifyResult+"=======================");
            System.out.println  (checkResult.expireTime) ;
            checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, 1400000000, "xiaojun2", pubStr);
            //Assert.assertNotEquals(null, checkResult);
            //Assert.assertFalse( checkResult.verifyResult);

          //  System.out.println(checkResult.verifyResult);


            // new interface generate signature
            result = tls_sigature.genSig(1400000000, "xiaojun", privStr);
            // Assert.assertNotEquals(null, result);
            //Assert.assertNotEquals(null, result.urlSig);
            // Assert.assertNotEquals(0, result.urlSig.length());

            // check signature
            checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, 1400000000, "xiaojun", pubStr);
            // Assert.assertNotEquals(null, checkResult);
            //Assert.assertTrue(checkResult.verifyResult);

            checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, 1400000000, "xiaojun2", pubStr);
            // Assert.assertNotEquals(null, checkResult);
            //  Assert.assertFalse( checkResult.verifyResult);

        } catch (DataFormatException e) {
            e.printStackTrace();
        }
    }
}