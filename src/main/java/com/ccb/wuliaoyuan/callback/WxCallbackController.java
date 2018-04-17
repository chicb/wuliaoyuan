package com.ccb.wuliaoyuan.callback;

import com.ccb.wuliaoyuan.callback.support.WXCallbackContext;
import com.ccb.wuliaoyuan.callback.support.WXCallbackHandler;
import com.ccb.wuliaoyuan.utils.common.CommonValue;
import com.ccb.wuliaoyuan.utils.encrpyt.SHA1Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by ccb on 2018/4/13.
 */
@RestController
@Slf4j
public class WxCallbackController {
    private static final String ECHOSTR = "echostr";
    private static final String SIGNATURE = "signature";
    private static final String TIMESTAMP = "timestamp";
    private static final String NONCE = "nonce";
    @Autowired(required = false)
    private List<WXCallbackHandler> handlers;

    @GetMapping("wx/receive")
    public String receive(HttpServletRequest request) {
        if (validate(request))
            return request.getParameter(ECHOSTR);
        return "invalid signature";
    }

    @RequestMapping(value = "/weixin/receive", method = RequestMethod.POST)
    public void post(HttpServletRequest request,
                     HttpServletResponse response,
                     @RequestParam Map<String, String> params) throws IOException {
        PrintWriter writer = response.getWriter();
        if (!validate(request)) {
            writer.write("");
        } else {
            WXCallbackContext context = new WXCallbackContext(request, response);
            handlers.forEach(handler -> {
                handler.handle(context);
            });
            if (!context.isResponded()) {
                writer.write("");
            }
        }
        writer.flush();
        writer.close();
    }

    private boolean validate(HttpServletRequest request) {
        String signature = request.getParameter(SIGNATURE);
        String timestamp = request.getParameter(TIMESTAMP);
        String nonce = request.getParameter(NONCE);
        String[] arr = {CommonValue.getToken(), timestamp, nonce};
        Arrays.sort(arr);
        List<String> list = Arrays.asList(arr);
        String encode = SHA1Encoder.encode(StringUtils.collectionToDelimitedString(list, ""));
        return signature != null && signature.equals(encode);
    }
}
