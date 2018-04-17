package com.ccb.wuliaoyuan.wx.message.send;

import com.ccb.wuliaoyuan.exception.WeiXinException;
import com.ccb.wuliaoyuan.utils.CommonUtils;
import com.ccb.wuliaoyuan.wx.support.JSONableContent;
import com.ccb.wuliaoyuan.wx.support.XMLableContent;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 */
@Data
public class NewsContent implements JSONableContent, XMLableContent{

    private List<Item> articles;

    public String asXml() throws WeiXinException {
        int count = 0;
        StringBuilder itemStr = new StringBuilder();
        for (Item item : articles) {
            if (item == null)
                continue;
            itemStr.append(item.asXml());
            count++;
        }
        StringBuilder str = new StringBuilder();
        str.append("<MsgType><![CDATA[news]]></MsgType><ArticleCount>");
        str.append(count);
        str.append("</ArticleCount><Articles>");
        str.append(itemStr);
        str.append("</Articles>");
        return str.toString();
    }

    public String asJson() throws WeiXinException {
        check();
        StringBuilder str = new StringBuilder();
        str.append("\"msgtype\":\"news\",");
        str.append("\"news\":{");
        str.append("\"articles\":[");
        boolean first = true;
        for (Item item : articles) {
            if (item == null) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                str.append(",");
            }
            str.append(item.asJson());
        }
        str.append("]}");
        return str.toString();
    }

    private void check() throws WeiXinException {
        if (articles == null) {
            throw new WeiXinException("Articles cannot be null");
        }
    }

    public static class Item implements JSONableContent, XMLableContent {
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String asXml() {
            StringBuilder str = new StringBuilder();
            str.append("<item>");
            if (CommonUtils.isNotNull(title)) {
                str.append("<Title><![CDATA[");
                str.append(title);
                str.append("]]></Title>");
            }
            if (CommonUtils.isNotNull(description)) {
                str.append("<Description><![CDATA[");
                str.append(description);
                str.append("]]></Description>");
            }
            if (CommonUtils.isNotNull(picUrl)) {
                str.append("<PicUrl><![CDATA[");
                str.append(picUrl);
                str.append("]]></PicUrl>");
            }
            if (CommonUtils.isNotNull(url)) {
                str.append("<Url><![CDATA[");
                str.append(url);
                str.append("]]></Url>");
            }
            str.append("</item>");
            return str.toString();
        }

        @Override
        public String asJson() {
            boolean first = true;
            StringBuilder str = new StringBuilder();
            str.append("{");
            if (CommonUtils.isNotNull(title)) {
                str.append("\"title\":\"");
                str.append(CommonUtils.escapeJsonValue(title));
                str.append("\"");
                first = false;
            }
            if (CommonUtils.isNotNull(description)) {
                if (!first)
                    str.append(",");
                str.append("\"description\":\"");
                str.append(CommonUtils.escapeJsonValue(description));
                str.append("\"");
                first = false;
            }
            if (CommonUtils.isNotNull(url)) {
                if (!first)
                    str.append(",");
                str.append("\"url\":\"");
                str.append(CommonUtils.escapeJsonValue(url));
                str.append("\"");
                first = false;
            }
            if (CommonUtils.isNotNull(picUrl)) {
                if (!first)
                    str.append(",");
                str.append("\"picurl\":\"");
                str.append(CommonUtils.escapeJsonValue(picUrl));
                str.append("\"");
            }
            str.append("}");
            return str.toString();
        }
    }
}
