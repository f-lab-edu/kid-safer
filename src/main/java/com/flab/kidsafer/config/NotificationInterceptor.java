package com.flab.kidsafer.config;

import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.mapper.NotificationMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class NotificationInterceptor implements HandlerInterceptor {

    private final NotificationMapper notificationMapper;

    public NotificationInterceptor(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            SessionUser user = (SessionUser) session.getAttribute("user");
            long count = notificationMapper.countByUserAndChecked(user.getId(), false);

            session.setAttribute("hasNotification", count > 0);
        }
    }
}
