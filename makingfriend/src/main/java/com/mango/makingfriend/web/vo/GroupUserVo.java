package com.mango.makingfriend.web.vo;

import lombok.Data;

@Data
public class GroupUserVo {
    private String member;
    private String owner;

}
/*
{
        "action" : "get",
        "application" : "e60f7200-3b04-11e8-8ad4-0584c9067623",
        "uri" : "http://a1.easemob.com/1159180408228899/lottery/chatgroups/133714433605633/users",
        "entities" : [ ],
        "data" : [ {
        "member" : "yun"
        }, {
        "member" : "jty"
        }, {
        "owner" : "cloud"
        } ],
        "timestamp" : 1621882880622,
        "duration" : 0,
        "organization" : "1159180408228899",
        "applicationName" : "lottery",
        "count" : 3
        }*/
