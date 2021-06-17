package com.mango.makingfriend.web.api.impl;

import com.mango.makingfriend.web.api.SendMessageAPI;
import com.mango.makingfriend.web.api.comm.EasemobAPI;
import com.mango.makingfriend.web.api.comm.OrgInfo;
import com.mango.makingfriend.web.api.comm.ResponseHandler;
import com.mango.makingfriend.web.api.comm.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.MessagesApi;
import io.swagger.client.model.Msg;
import org.springframework.stereotype.Service;

@Service
public class EasemobSendMessage implements SendMessageAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private MessagesApi api = new MessagesApi();
    @Override
    public Object sendMessage(final Object payload) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameMessagesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(), (Msg) payload);
            }
        });
    }
}
