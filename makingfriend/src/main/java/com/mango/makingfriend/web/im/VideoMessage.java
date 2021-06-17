package com.mango.makingfriend.web.im;

import io.swagger.client.model.MsgContent;
import lombok.Data;

@Data
public class VideoMessage extends MsgContent {

    private String url;
    private String filename;
    private String secret;
    private Size size;
    private  String  thumb;
    private String thumb_secret;

}
