package com.scm.scm10.config;

import com.scm.scm10.helpers.Message;
import com.scm.scm10.helpers.MessageType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class AutheFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(exception instanceof DisabledException){
            HttpSession session = request.getSession();
            session.setAttribute("message", Message.builder()
                            .content("user is disabled , email with verification link sent on your email id")
                            .type(MessageType.red)
                    .build());
            response.sendRedirect("/login");
        }
        else {
            response.sendRedirect("login?error=true");
        }
    }
}
