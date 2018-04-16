package com.ccb.wuliaoyuan.callback;

import com.ccb.wuliaoyuan.utils.common.CommonValue;
import com.ccb.wuliaoyuan.utils.encrpyt.SHA1Encoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ccb on 2018/4/13.
 */
@RestController
@Slf4j
public class WxCallbackController{
    private static final String ECHOSTR = "echostr";
    private static final String SIGNATURE = "signature";
    private static final String TIMESTAMP = "timestamp";
    private static final String NONCE = "nonce";

    @GetMapping("wx/receive")
    public String receive(HttpServletRequest request){
        if(validate(request))
            return request.getParameter(ECHOSTR);
        return "invalid signature";
    }

    private boolean validate(HttpServletRequest request){
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
