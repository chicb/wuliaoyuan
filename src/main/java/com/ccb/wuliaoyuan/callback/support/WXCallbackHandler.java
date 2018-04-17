package com.ccb.wuliaoyuan.callback.support;

import com.ccb.wuliaoyuan.exception.WeiXinException;
import com.ccb.wuliaoyuan.wx.message.send.NewsContent;
import com.ccb.wuliaoyuan.wx.message.send.WxResponseMessage;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;

/**
 * Created by Administrator on 2018/4/17.
 */
public interface WXCallbackHandler extends Ordered {

    @Override
    default int getOrder() {
        return 0;
    }

    /**
     * 判断是否要处理该回调-供实现类自定义实现
     *
     * @param context
     * @return
     */
    boolean accept(WXCallbackContext context);

    /**
     * 处理微信回调
     *
     * @param context
     */
    void handle(WXCallbackContext context);

    /**
     * 发送客服消息-文字消息
     *
     * @param context
     */
   /* default void sendCustomTextMessage(WXCallbackContext context, String text) {
        try {
            WxApi.instance()
                    .sendCustom(WxCustomMessage.builder()
                            .toUser(context.readFromUserName())
                            .content(new TextContent().setContent(text))
                            .build());
        } catch (WeiXinException e) {
            throw new RuntimeException(e);
        }
    }*/

    /**
     * 发送图文消息
     *
     * @param context
     * @param articles
     */
    default void sendNewsMessage(WXCallbackContext context, NewsContent.Item... articles) {
        if (null == articles || articles.length < 1)
            return;
        NewsContent content = new NewsContent();
        content.setArticles(Arrays.asList(articles));
        WxResponseMessage message = WxResponseMessage.builder()
                .fromUserName(context.readToUserName())
                .toUserName(context.readFromUserName())
                .createTime(System.currentTimeMillis() / 1000)
                .content(content)
                .build();
        try {
            context.writeXML(message.asXml());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (WeiXinException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 键值
     *
     * @param context
     * @return
     */
    default String key(WXCallbackContext context) {
        String key = context.readMsgId();
        if (StringUtils.isEmpty(key))
            key = context.readFromUserName() + context.readCreateTime();
        return this.getClass().getName() + key;
    }

}
