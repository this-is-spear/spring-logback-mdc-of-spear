package spear.springlogbackmdc.web;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import spear.springlogbackmdc.domain.member.Member;
import spear.springlogbackmdc.web.filter.LogbackMdcId;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model) {
        if (member == null) {
            return "home";
        }

        log.info("[{}] : LOG IN HOME [{}]", MDC.get(LogbackMdcId.LOGBACK_MDC_ID), member);
        model.addAttribute(member);
        return "loginHome";
    }
}
