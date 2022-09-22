package com.mysite.sbb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @GetMapping("/saveSessionAge")
    @ResponseBody
    public String saveSession(@RequestParam("age") int age, HttpSession session) {
        System.out.println("age: " + age);
        session.setAttribute("age", age);
        return "나이 %d이 세션에 저장되었습니다.".formatted(age);
    }

    @GetMapping("/getSessionAge")
    @ResponseBody
    public String saveSession(HttpSession session) {
        int age = (int) session.getAttribute("age");
        return "세션에 저장된 나이는 %d입니다.".formatted(age);
    }

    @GetMapping("/addPerson")
    @ResponseBody
    public Person addPerson(Person person){
        return person;
    }

    @GetMapping("/addPerson/{id}")
    @ResponseBody
    public Person addPerson(Person person, @PathVariable("id") Integer id){
        return person;
    }

//    Question question = new Question();
//    question.getAnswerList();
}

@Getter
@AllArgsConstructor
class Person {
    private int id;
    private int age;
    private String name;
}
