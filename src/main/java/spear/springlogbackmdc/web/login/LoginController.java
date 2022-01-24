package spear.springlogbackmdc.web.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spear.springlogbackmdc.domain.login.LoginService;
import spear.springlogbackmdc.domain.member.Member;
import spear.springlogbackmdc.web.SessionConst;
import spear.springlogbackmdc.web.filter.LogbackMdcId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService longinService;


    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("[{}] : LOG IN FAILED [{}] REDIRECT : [{}]", MDC.get(LogbackMdcId.LOGBACK_MDC_ID), loginForm.getLoginId(), redirectURL);

            return "login/loginForm";
        }


        Member member = longinService.login(loginForm.getLoginId(), loginForm.getPassword());
        if (member == null) {

            log.info("[{}] : LOG IN FAILED [{}] REDIRECT : [{}]", MDC.get(LogbackMdcId.LOGBACK_MDC_ID), loginForm.getLoginId(), redirectURL);

            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        log.info("[{}] : SUCCESS LOG IN [{}] REDIRECT : [{}]", MDC.get(LogbackMdcId.LOGBACK_MDC_ID), loginForm.getLoginId(), redirectURL);

        //세션이 있으면 존재하는 제션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession(true);
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        return "redirect:" + redirectURL;
    }
}
