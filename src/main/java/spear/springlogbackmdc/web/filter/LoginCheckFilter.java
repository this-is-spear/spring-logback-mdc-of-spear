package spear.springlogbackmdc.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.PatternMatchUtils;
import spear.springlogbackmdc.web.SessionConst;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LoginCheckFilter implements Filter {


    private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"/*, "/*.ico"*/};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestURI = httpServletRequest.getRequestURI();
        try {
            log.info("[{}] : 인증 체크 필터 시작 [{}][{}]", MDC.get(LogbackMdcId.LOGBACK_MDC_ID), requestURI, request.getDispatcherType().toString());
            if (isLoginCheckPath(requestURI)) {
                log.info("[{}] : 미인증 체크 로직 실행 [{}][{}]",MDC.get(LogbackMdcId.LOGBACK_MDC_ID), requestURI, request.getDispatcherType().toString());

                HttpSession session = httpServletRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("[{}] : 미인증 사용자 요청 [{}][{}]",MDC.get(LogbackMdcId.LOGBACK_MDC_ID), requestURI, request.getDispatcherType().toString());
                    //로그인으로 리다이렉트
                    httpServletResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.info("[{}] : EXCEPTION [{}][{}]", MDC.get(LogbackMdcId.LOGBACK_MDC_ID), requestURI, e.getMessage());
            throw e;
        }finally {
            log.info("[{}] : 인증 체크 필터 종료 [{}][{}]", MDC.get(LogbackMdcId.LOGBACK_MDC_ID), requestURI, request.getDispatcherType().toString());
        }
    }

    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
