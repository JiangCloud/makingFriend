package com.mango.makingfriend.web.im;

import io.swagger.client.model.MsgContent;
import lombok.Data;

@Data
public class ImageMsgContent extends MsgContent {

    private String url;
    private String filename;
    private String secret;
    private Size size;




}
