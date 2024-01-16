package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberResponseDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    private final List<Member> memberDB; // 데이터베이스로 사용하는 것으로 DTO사용 X
    public MemoryMemberRepository() {
        memberDB = new ArrayList<>();
    }

//    List<Member> 리턴
    @Override
    public List<Member> members() {
        return memberDB;
    }

//    Member 등록
    @Override
    public  void memberCreate(Member member) {
        memberDB.add(member);
    }

//    데이터베이스 내 id에 해당하는 Member를 찾아서 return
    @Override
    public Member memberDetail(int id) {
        for(Member a : memberDB) {
            if(id == a.getId()) {
                return a;
            }
        }return null;
    }


}
