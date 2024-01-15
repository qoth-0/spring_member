package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryMemberRepository {
    private final List<Member> memberDB; // 데이터베이스로 사용하는 것으로 DTO사용 X
    public MemoryMemberRepository() {
        memberDB = new ArrayList<>();
    }

//    List<Member> 리턴
    public List<Member> members() {
        return memberDB;
    }

//    Member 등록
    public  void memberCreate(Member member) {
        memberDB.add(member);
    }
}
