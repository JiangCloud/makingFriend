package com.mango.makingfriend.web.vo;


import lombok.Data;

@Data
public class RemoveGroupUserVo {

    private boolean result;
    private String action;
    private String user;
    private String groupid;



}
