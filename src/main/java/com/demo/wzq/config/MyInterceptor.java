package com.demo.wzq.config;

import com.alibaba.fastjson2.JSONObject;
import com.demo.wzq.model.entity.base.R;
import com.demo.wzq.mybatis.MyBatisUtil;
import com.demo.wzq.uitls.IpUtil;
import com.demo.wzq.uitls.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ThirdGoddess
 * @version 1.0.0
 * @time 2023/4/4 16:36
 * @desc
 */
@Slf4j
public class MyInterceptor implements HandlerInterceptor {

    //    private static final List<String> PASS_URL_ARR = new ArrayList<>();


    /**
     * 访问控制器方法前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipStr = IpUtil.getIpStr(request);
        String requestURI = request.getRequestURI();
        String userToken = request.getHeader("UserToken");

        log.info("{} => {}", ipStr, requestURI);

        //判断是否跳过拦截
        if (isPass(requestURI)) return true;


        //验证Token
        if (-1 == JwtUtils.getUserId(userToken)) {
            responseMsg(ipStr, requestURI, response, "token error", "呦小伙子,不要干坏事哦!整不好要封号哦!");
            return false;
        }


        return true;
    }

    /**
     * 访问控制器方法后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("响应2");
    }

    /**
     * postHandle方法执行完成后执行，一般用于释放资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MyBatisUtil.commit();
//        log.info(response.toString());
        log.info("响应3"+response.getWriter().);
    }

    private static final String[] PASS_URL_ARR = new String[]{"/user/login", "/user/register"};

    private static boolean isPass(String url) {
        for (String s : PASS_URL_ARR) {
            if (url.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 响应
     *
     * @param response 响应
     * @param msg      消息
     * @param sneer    嘲讽
     * @throws IOException 异常
     */
    private void responseMsg(String ip, String uri, HttpServletResponse response, String msg, String sneer) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        R r = new R();
        if (null != msg) r.setFailedState(msg);
        if (null != sneer) r.setSneer(sneer);
        String s = JSONObject.toJSONString(r);
        response.getWriter().println(s);
        response.getWriter().close();
        log.info("{} <= {} :{}", ip, uri, s);
        MyBatisUtil.commit();
    }

}
