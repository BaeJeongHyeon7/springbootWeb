package com.example8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example8.entity.Member;

public interface MemberRepository extends
			JpaRepository<Member, String>{

}
