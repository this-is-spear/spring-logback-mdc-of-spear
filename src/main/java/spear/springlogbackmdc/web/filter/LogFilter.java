package spear.springlogbackmdc.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import spear.springlogbackmdc.web.SessionConst;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        MDC.put(LogbackMdcId.LOGBACK_MDC_ID, uuid);
        try {
            log.info("[{}] : REQUEST [{}][{}]", MDC.get(LogbackMdcId.LOGBACK_MDC_ID), requestURI, request.getDispatcherType().toString());
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.info("[{}] : EXCEPTION [{}][{}]", MDC.get(LogbackMdcId.LOGBACK_MDC_ID), requestURI, e.getMessage());
            throw e;
        }finally {
            log.info("[{}] : RESPONSE [{}][{}]", MDC.get(LogbackMdcId.LOGBACK_MDC_ID), requestURI, request.getDispatcherType().toString());
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
