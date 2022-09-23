package com.mysite.sbb;

import com.mysite.sbb.question.dao.QuestionRepository;
import com.mysite.sbb.question.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void contextLoads() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.?");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	void getAll() {
		// SELECT * FROM question;
		List<Question> all = questionRepository.findAll();
		assertEquals(4, all.size());

		Question q = all.get(1);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	void getQuestionById() {
		Optional<Question> oq = questionRepository.findById(1);
		if(oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

	// "sbb가 무엇인가요?"라는 데이터가 2개 존재할 경우
	// getQuestionBySubject() 는 에러가 발생 >>> 해당 데이터가 2개 존재하기 때문에
	// >> getQuestionsBySubject() 처럼 List로 받아오면 오류X
	@Test
	void getQuestionBySubject() {
		// findBySubject는 원래 없는 method, 이때 Ctrl+1을 눌러 method를 만들어준다.
		Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
	}

	@Test
	void getQuestionsBySubject() {
		List<Question> questions = this.questionRepository.findAllBySubject("sbb가 무엇인가요?");
		assertEquals(2, questions.size());
	}

	// 2가지 조건으로 데이터 조회
	@Test
	void getQuestionByTwoSubject() {
		List<String> searchWordList = new ArrayList<>();
		searchWordList.add("sbb가 무엇인가요?");
		searchWordList.add("스프링부트 모델 질문입니다.?");
		List<Question> questions = this.questionRepository.findAllBySubjectIn(searchWordList);
		assertEquals(4, questions.size());
	}
}
