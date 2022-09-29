package com.mysite.sbb.question.service;

import com.mysite.sbb.question.dao.QuestionRepository;
import com.mysite.sbb.question.domain.Question;
import com.mysite.sbb.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        // 아래 return 코드 처럼 축약하여 표현 가능
//        Optional<Question> question = questionRepository.findById(id);
//        if(question.isPresent()) {
//            return question.get();
//        }else {
//            throw new DataNotFoundException("question not found");
//        }

        return questionRepository.findById(id).orElseThrow(() -> new DataNotFoundException("question not found"));
    }
}
