package com.mysite.sbb;


import com.mysite.sbb.answer.dao.AnswerRepository;
import com.mysite.sbb.answer.domain.Answer;
import com.mysite.sbb.question.dao.QuestionRepository;
import com.mysite.sbb.question.domain.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
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

	// subject 와 content 둘다 만족하는 데이터 찾기
	@Test
	void getQuestionBySubjectAndContent() {
		List<Question> questions = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해 알고 싶습니다.");
		assertEquals(2, questions.size());
	}


	// subject 내용에 특정 내용을 포함한 데이터 찾기
	@Test
	void getQuestionHaveStrings() {
		List<Question> questions = questionRepository.findBySubjectLike("sbb%");
		assertEquals(2, questions.size());
	}

	// 데이터 수정하기
	@Test
	void updateQuestion() {
		Optional<Question> oq = questionRepository.findById(2);
		if(oq.isPresent()) {
			Question question = oq.get();
			question.setSubject("수정된 질문");
			question.setContent("수정된 내용");
			questionRepository.save(question);
		}
	}

	// 데이터 삭제하기
	@Test
	void deleteQuestion() {
		assertEquals(5,questionRepository.count());
		Optional<Question> oq = questionRepository.findById(1);
		if(oq.isPresent()) {
			Question question = oq.get();
			questionRepository.delete(question);
		}
		assertEquals(4, questionRepository.count());
	}

	// 답변 생성하기

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	void createAnswer() {
		Optional<Question> oq = questionRepository.findById(2);
		if(oq.isPresent()) {
			Question question = oq.get();

			Answer answer = new Answer();
			answer.setContent("답변입니다.");
			answer.setQuestion(question);
			answer.setCreateDate(LocalDateTime.now());
			answerRepository.save(answer);
		}
	}

	// 답변이 달린 질문 조회하기
	@Test
	@Transactional
	void getAnswerByQuestion() {
		Optional<Question> oq = questionRepository.findById(2);
		if(oq.isPresent()){
			Question question = oq.get();
			List<Answer> answerList = question.getAnswerList();
			assertEquals(1, answerList.size());
		}
	}
}
