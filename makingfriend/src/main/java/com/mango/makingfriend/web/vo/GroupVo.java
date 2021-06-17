package com.mango.makingfriend.web.vo;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GroupVo {
/*

    private String owner;
    private  Long groupId;
    private  int  affiliations;
    private  String type;
    private long last_modified;
    private String groupName;
*/

    private String  id;
    private String name;
    private String description;
    private String owner;

    private String membersonly;//加入群组是否需要群主或者群管理员审批
    private int maxusers;//群成员上限
    private long created;//创建该群组的时间戳
    private int affiliations_count;//现有成员总数。
    private boolean  _public;
/*
"action" : "get",
        "application" : "e60f7200-3b04-11e8-8ad4-0584c9067623",
        "uri" : "http://a1.easemob.com/1159180408228899/lottery/chatgroups/133714433605633",
        "entities" : [ ],
        "data" : [ {
        "id" : "133714433605633",
                "name" : "同城交友",
                "description" : "江华人的交友群",
                "membersonly" : false,
                "allowinvites" : false,
                "maxusers" : 200,
                "owner" : "cloud",
                "created" : 1606918421774,
                "custom" : "进城",
                "mute" : false,
                "affiliations_count" : 3,
                "affiliations" : [ {
            "member" : "yun"
        }, {
            "member" : "jty"
        }, {
            "owner" : "cloud"
        } ],
        "public" : false
    } ],
            "timestamp" : 1621343592213,
            "duration" : 0,
            "organization" : "1159180408228899",
            "applicationName" : "lottery",
            "count" : 1
}*/

/*{"owner":"cloud","created":1606918421774,"custom":"进城","description":"江华人的交友群",
            "affiliations":[{"member":"yun"},{"member":"jty"},{"owner":"cloud"}],
        "mute":false,"maxusers":200,"membersonly":false,"allowinvites":false,"public":false,"name":"同城交友","id":"133714433605633","affiliations_count":3}


*/
}
