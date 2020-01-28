package io.vlinx.communicate.net;

import io.vlinx.communicate.version.utils.HttpUtil;
import io.vlinx.configutils.JSONUtils;
import io.vlinx.configutils.exception.JSONException;
import io.vlinx.encryption.AES;
import io.vlinx.logging.Logger;
import io.vlinx.utils.StringUtil;

public class HttpComClient {

    private String url = "";

    private String dataPwd = "";

    public HttpComClient(String url) {
        this.url = url;
    }

    public HttpComClient(String url, String dataPwd) {
        this.url = url;
        this.dataPwd = dataPwd;
    }

    public NetMessage sendRequest(NetMessage request) throws Exception {
        try {
            if (request == null)
                return null;

            String json = JSONUtils.toJson(request);
            if (!StringUtil.isEmpty(dataPwd)) {
                json = AES.encrypt(json, dataPwd);
            }

            String responseStr = HttpUtil.sendPost(url, "request=" + json);
            if (!StringUtil.isEmpty(dataPwd)) {
                responseStr = AES.decrypt(responseStr, dataPwd);
            }

            if (StringUtil.isEmpty(responseStr)) {
                Logger.ERROR("Could not get response");
            }

            NetMessage response = null;
            try {
                response = JSONUtils.fromJson(responseStr, NetMessage.class);
            } catch (JSONException e) {
                Logger.ERROR(e);
            }

            return response;
        } catch (Exception e) {
            throw e;
        }

    }

}
