package com.mango.makingfriend.web.api.impl;


import com.mango.makingfriend.web.api.FileAPI;
import com.mango.makingfriend.web.api.comm.EasemobAPI;
import com.mango.makingfriend.web.api.comm.OrgInfo;
import com.mango.makingfriend.web.api.comm.ResponseHandler;
import com.mango.makingfriend.web.api.comm.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.UploadAndDownloadFilesApi;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EasemobFile implements FileAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private UploadAndDownloadFilesApi api = new UploadAndDownloadFilesApi();
    @Override
    public Object uploadFile(final Object file) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameChatfilesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(),(File)file,true);
             }
        });
    }

    @Override
    public Object downloadFile(final String fileUUID,final  String shareSecret,final Boolean isThumbnail) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
               return api.orgNameAppNameChatfilesUuidGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),fileUUID,shareSecret,isThumbnail);
            }
        });
    }
}
