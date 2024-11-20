package com.scm.scm10.helpers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

@Component
public class SessionHelper {
    public static void removeMessage(){
        try{
            HttpSession httpSession = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            httpSession.removeAttribute("message");
        }catch (Exception e){
            System.out.printf("Exception in remove message "+e);
            e.printStackTrace();
        }
    }
}
