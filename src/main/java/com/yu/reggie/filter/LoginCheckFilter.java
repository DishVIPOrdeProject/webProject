package com.yu.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.yu.reggie.common.BaseContext;
import com.yu.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "logincheck",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       //1.定义放行的内容
        String[] requestUrls =new String[]{"/employee/login","/front/**","/employee/logout","/addressBook/**","/employee/page","/user/sendMsg","/user/**","/backend/**","/common/**","/front/**"};

        //2.获取请求网址
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = request.getRequestURI();
        log.info(requestUrl);
        //3.匹配放行的内容
        boolean check = march(requestUrls,requestUrl);
        log.info(String.valueOf(check));
        //放行
        if (check){
            filterChain.doFilter(request,response);
            return;
        }
        //4.1判断登录状态
        if (request.getSession().getAttribute("employee")!=null){
            Long id = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(id);
            filterChain.doFilter(request,response);
            return;
        }
        //4-2、判断登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登录");
       // 如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配
     * @param urls
     * @param requesturl
     * @return
     */
    public Boolean march(String[] urls,String requesturl){
        for (String url:urls
             ) {
            boolean match = PATH_MATCHER.match(url,requesturl);
            if (match){
                return true;
            }
        }
        return false;
    }
}
