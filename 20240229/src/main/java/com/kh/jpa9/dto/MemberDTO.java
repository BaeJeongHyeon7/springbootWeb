package com.kh.jpa9.dto;

import com.kh.jpa9.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDTO {
	// 입력받은 dto를 데이터베이스로 보내기 위해서 엔티티 타입으로 변경해야된다.
	private int num;
	private String name;
	private String id;
	private String phone;
	private int age;
	public Member toEntity() {
		return new Member(num,name,id,phone,age);
	}
}
