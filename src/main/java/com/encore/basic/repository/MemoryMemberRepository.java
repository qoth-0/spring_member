package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberResponseDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    private final List<Member> memberDB; // 데이터베이스로 사용하는 것으로 DTO사용 X

    static int total_id;
    public MemoryMemberRepository() {
        memberDB = new ArrayList<>();
    }

//    List<Member> 리턴
    @Override
    public List<Member> findAll() {
        return memberDB;
    }

//    Member 등록
    @Override
    public Member save(Member member) {
        total_id += 1;
        LocalDateTime now = LocalDateTime.now();
        member.setId(total_id);
        member.setCreate_time(now);
        memberDB.add(member);
        return member;
    }

//    데이터베이스 내 id에 해당하는 Member를 찾아서 return
    @Override
    public Optional<Member> findById(int id) {
        for(Member a : memberDB) {
            if(id == a.getId()) {
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }


}
