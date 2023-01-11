package com.yudian.common.utils.wechat;


import com.yudian.common.utils.wechat.aes.AesException;
import com.yudian.common.utils.wechat.aes.WXBizMsgCrypt;

import javax.servlet.http.HttpServletRequest;

public class AuthProcess {
    public final static String Token = "wxfd522f37c6f267b8";
    public final static String EncodingAESKey = "Y2U1NjlkYTYyM2RhNDAxY2I0OTQxZDQwN2ViYTRkODE";
    public final static String AppID = "wxfd522f37c6f267b8";


    
    public static String decryptMsg(HttpServletRequest request, String originalXml) {


        String msgSignature = request.getParameter("msg_signature");

        String timestamp = request.getParameter("timestamp");

        String nonce = request.getParameter("nonce");
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(Token, EncodingAESKey, AppID);
            return pc.decryptMsg(msgSignature, timestamp, nonce, originalXml);
        } catch (AesException e) {

            e.printStackTrace();
        }

        return null;
    }

    
    public static String encryptMsg(HttpServletRequest request, String replyXml) {

        String timestamp = request.getParameter("timestamp");

        String nonce = request.getParameter("nonce");
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(Token, EncodingAESKey, AppID);
            return pc.encryptMsg(replyXml, timestamp, nonce);
        } catch (AesException e) {

            e.printStackTrace();
        }
        return null;
    }
}
