package com.flab.kidsafer.aop;

import com.flab.kidsafer.error.exception.UserNotSignInException;
import com.flab.kidsafer.utils.SessionUtil;
import javax.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class CheckLoginAspect {

    @Before(value = "@annotation(com.flab.kidsafer.aop.CheckLogin)")
    public void loginCheck() throws HttpClientErrorException {
        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder
            .currentRequestAttributes())).getRequest().getSession();
        int userId = SessionUtil.getLoginUserId(session);
        if (userId == -1) {
            throw new UserNotSignInException();
        }
    }
}
