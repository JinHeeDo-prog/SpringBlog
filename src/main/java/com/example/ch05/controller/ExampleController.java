package com.example.ch05.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;


@Controller
public class ExampleController {

    // Controller 선언시 뷰를 찾기 때문에 에러남. RestController 사용시 ResponseBody 형식으로 나오기 때문에 에러가 안 남.
    // Controller 선언시 조심.
    @GetMapping("/test")
    //import org.springframework.ui.Model;
    // 객체를
    public String test(Model model){
        Person person = new Person();

        person.setId(1L);
        person.setName("진희도");
        person.setAge(25);
        person.setHobbies(List.of("운동","독서","코딩"));

        //- 모델 객체는 뷰, 즉 HTML쪽으로 값을 넘겨주는 객체
        //- addAttribute() 메서드로 모델에 값을 저장
        model.addAttribute("person",person);
        model.addAttribute("today", LocalDateTime.now());

        // - resources/templates 디렉토리에서 [반환값].html 파일을 찾아서 응답한다.
        // - String ‘test’를 반환하고 싶으면? @RestContoller 선언하거나 @ResponseBody 선언
        return "test";
    }
    @Getter
    @Setter
    class Person{
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;

    }

}
