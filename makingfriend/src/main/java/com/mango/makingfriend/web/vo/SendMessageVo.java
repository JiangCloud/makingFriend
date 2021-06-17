package com.mango.makingfriend.web.vo;

import lombok.Data;



@Data
public class SendMessageVo {


    private String thumb;//成功上传视频缩略图返回的UUID
    private String thumbSecret;//成功上传视频缩略图后返回的secret
    private String uri;

    private String share_secret;//功上传文件后返回的secret

    private  String uuid;//成功上传文件返回的UUID

    private  String type;
}
