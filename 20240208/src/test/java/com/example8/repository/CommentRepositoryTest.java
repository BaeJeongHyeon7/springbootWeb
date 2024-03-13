package com.example8.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example8.entity.Comment;
@SpringBootTest
class CommentRepositoryTest {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Test
	@Transactional
	void test() {
		List<Comment> saveTest= new ArrayList<Comment>();
		
		
		
		
	}

}
