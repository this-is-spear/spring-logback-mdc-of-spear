package spear.springlogbackmdc.domain.member;

import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Slf4j
@Repository
public class MemoryMemberRepository implements MemberRepository{
    Map<Long, Member> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save : member = {}", member);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Member findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream().filter(member -> member.getLoginId().equals(loginId)).findFirst();
    }
}
