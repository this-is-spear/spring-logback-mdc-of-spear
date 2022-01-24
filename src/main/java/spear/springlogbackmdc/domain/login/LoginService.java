package spear.springlogbackmdc.domain.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spear.springlogbackmdc.domain.member.Member;
import spear.springlogbackmdc.domain.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId).filter(member -> member.getPassword().equals(password)).orElse(null);
    }
}
