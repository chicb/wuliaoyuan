package com.ccb.wuliaoyuan.wx.message.send;

import com.ccb.wuliaoyuan.exception.WeiXinException;
import com.ccb.wuliaoyuan.wx.support.XMLableContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2018/4/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxResponseMessage{
    private static final String TEMPLATE = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$d</CreateTime>%4$s</xml>";
    private String toUserName;
    private String fromUserName;
    private Long createTime;
    private XMLableContent content;

    public String asXml() throws WeiXinException {
        if (toUserName == null) {
            throw new WeiXinException("ToUserName cannot be null");
        }
        if (fromUserName == null) {
            throw new WeiXinException("FromUserName cannot be null");
        }
        if (createTime == null) {
            throw new WeiXinException("CreateTime cannot be null");
        }
        if (content == null) {
            throw new WeiXinException("Message Content cannot be null");
        }
        return String.format(TEMPLATE, toUserName, fromUserName, createTime,
                content.asXml());
    }
}
