package com.flab.kidsafer.utils;

import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.User;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static final String MEMBER_ID = "MEMBER_ID";
    private static final String USER = "user";

    private SessionUtil() {
    }

    public static int getLoginUserId(HttpSession session) {

        return session.getAttribute(MEMBER_ID) == null ? -1 : (int) session.getAttribute(MEMBER_ID);
    }

    public static User getLoginUser(HttpSession session) {
        return (User) session.getAttribute(USER);
    }

    public static void setLoginUserId(HttpSession session, User user) {
        session.setAttribute(MEMBER_ID, user.getUserId());
        SessionUser sessionUser = new SessionUser(user);
        session.setAttribute(USER, sessionUser);
    }

    public static void clearSession(HttpSession session) {
        session.invalidate();
    }

    public static void logoutUser(HttpSession session) {
        session.removeAttribute(MEMBER_ID);
        session.removeAttribute(USER);
    }
}
