package com.example.ch05.controller;

import com.example.ch05.domain.Article;
import com.example.ch05.dto.ArticleListViewResponse;
import com.example.ch05.dto.ArticleViewResponse;
import com.example.ch05.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
//생성자 형태로 알아서 주입 가능한 어노테이션
@RequiredArgsConstructor
public class BlogViewController {


    private final BlogService blogService;


    @GetMapping("/articles")
    public String findAllArticles(Model model) {
        List<ArticleListViewResponse> articleListViewResponses =
                blogService.findAll().stream().map(ArticleListViewResponse::new).toList();
        model.addAttribute("articles",articleListViewResponses);
        return "articleList";
        //주석 처리 한것들은 전부 같은 것임.
//        List<ArticleListViewResponse> articleListViewResponses2 =
//                blogService.findAll().stream().map(article -> {
//                   return new ArticleListViewResponse(article);
//                });


//        List<Article> list = blogService.findAll();
//        List<ArticleListViewResponse> result = new ArrayList<>();
//        for(int i =0; i<list.size(); i++){
//            Article article = list.get(i);
//            ArticleListViewResponse articleListViewResponse = new ArticleListViewResponse(article);
//        }

    }
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model){
        Article article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));
        return "article";
    }

    
    @GetMapping("/new-articles") // 수정 버튼 눌렀을 시 발생하는 메서드
    public String newArticle(@RequestParam(required = false)Long id, Model model){
        if(id == null){ // 등록한다
             model.addAttribute("article", new ArticleViewResponse());
        } else { // 수정
            Article article = blogService.findById(id);
            model.addAttribute("article", article);
        }
        return "newArticles";
    }


}
