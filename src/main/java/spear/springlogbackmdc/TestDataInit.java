package spear.springlogbackmdc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spear.springlogbackmdc.domain.member.Member;
import spear.springlogbackmdc.domain.member.MemberRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
//        itemRepository.save(new Item("itemA", 10000, 10));
//        itemRepository.save(new Item("itemB", 20000, 20));
        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test!");
        member.setName("테스터");

        memberRepository.save(member);
    }
}