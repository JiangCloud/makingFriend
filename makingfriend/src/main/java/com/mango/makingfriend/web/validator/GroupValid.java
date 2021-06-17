package com.mango.makingfriend.web.validator;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class GroupValid implements Serializable {

    @NotEmpty(message = "群名称不能为空")
    private String groupname;
    @NotEmpty(message = "群描述不能为空")
    private String desc;
    @NotEmpty(message = "群管理员不能为空")
    private String owner;

    @SerializedName("public")
    private boolean _public;
    private  int maxusers;
    private String groupId;


}
