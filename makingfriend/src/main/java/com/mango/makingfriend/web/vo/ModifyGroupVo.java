package com.mango.makingfriend.web.vo;

import lombok.Data;

@Data
public class ModifyGroupVo {

    private Boolean description;
    private Boolean groupname;

    private Boolean  membersonly;
    private Boolean  maxusers;
    private Boolean  allowinvites;



}
