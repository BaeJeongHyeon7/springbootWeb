package com.example8.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity  // 현재 클래스는 entity로 데이터베이스에 테이블생성을
		 // 위한 클래스가 된다. 
public class Member {
	
	@Id    // 반드시 entity에 존재해야된다. 
	@GeneratedValue(strategy = GenerationType.IDENTITY) // id값이 자동으로 증가하는 것!
	private Long idx;
		
	@Column
	private String 	 id; 
	
	@Column
	private String 	 password;  
	@Column
	private String 	 name;
	@Column
	private int 	 age; 
	@Column
	private String 	 gender;
	
	@Column
	private String 	 phone; 
	@Column
	private String 	 address;
	@Column
	private String 	 email;
	@Column
	private String[] hobby;
	@Column
	private Date 	 enrollDate; 
}
