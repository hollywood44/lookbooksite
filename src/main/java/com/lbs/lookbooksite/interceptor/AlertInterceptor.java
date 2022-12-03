package com.lbs.lookbooksite.interceptor;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AlertInterceptor implements HandlerInterceptor {

    @Autowired
    private NoticeService noticeService;


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HttpSession session = request.getSession();
        Object securityContextObject = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        if (securityContextObject != null) {
            SecurityContext securityContext = (SecurityContext)securityContextObject;
            Authentication authentication = securityContext.getAuthentication();
            Member member = (Member) authentication.getPrincipal();

            if (authentication.isAuthenticated() && !member.getMemberId().equals("admin")) {

                Long Notice = noticeService.checkNoti(member);
                modelAndView.addObject("noticeCount",Notice);
            }
        }


    }


}
