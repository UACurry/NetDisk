package com.netdisk.backend.config.filter;

import com.alibaba.fastjson.JSON;
import com.netdisk.backend.common.BaseContext;
import com.netdisk.backend.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 检查是否登陆
//urlPatterns = "/*" 表示拦截所有
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

//    路径匹配器 支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        获取本次请求URI
        String requsetURI = request.getRequestURI();

//        定义不需要请求的路径
        String[] urls = new String[]{
                "/employee/login/",
                "/employee/logout/",
//                "/employee/page/",
//                静态资源放行
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg/",
                "/user/login/"
        };



        boolean check = check(urls, requsetURI);

//        放行
        if(check){
            filterChain.doFilter(request, response);
            return;
        }

//        判断是否登陆
//        从session获取有无 如果有 则放行
        if(request.getSession().getAttribute("employee") != null){

//            将id保存到 threadlocal 从而应用于 公共字段的更新
            Long empID = (Long) request.getSession().getAttribute("employee");

            BaseContext.setCurrentId(empID);
            filterChain.doFilter(request, response);
            return;
        }

        //        4-2、判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户已登录，用户id为：{}", request.getSession().getAttribute("user"));

            Long userId= (Long) request.getSession().getAttribute("user");

            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request, response);
            return;
        }



//        返回未登录结果 通过输出流方式 向客户端响应  R.error("NOTLOGIN"  NOTLOGIN要和前端request.js对应
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配 检查本次是否放行
     * @param requestURI, urls
     */
    public boolean check(String[] urls, String requestURI){
        for(String url : urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
