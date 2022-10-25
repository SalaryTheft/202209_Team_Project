package com.jotte.common.interceptor;

import com.jotte.user.vo.UserVO;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // @Auth 어노테이션 확인
        if (handler instanceof HandlerMethod handlerMethod) {
            Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
            if (auth == null) {
                return true;
            } else {
                // @Auth 어노테이션이 존재하면 세션에 유저 정보가 있는지 확인
                HttpSession session = request.getSession();
                session.setAttribute("redirect_uri", request.getHeader("referer"));
                UserVO user = (UserVO) session.getAttribute("user");
                if (user == null) {
                    // 세션에 유저 정보가 없으면 로그인 페이지로 이동
                    response.sendRedirect("/login");
                    return false;
                } else {
                    // 세션에 유저 정보가 있으면 권한 확인
                    if (auth.role().equals("ADMIN") && !user.getUserRole().equals("ADMIN")) {
                        response.sendRedirect("/error/403");
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
