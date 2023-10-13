package com.example.ch05.dto;

import com.example.ch05.domain.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
//기본생성자 만들기 어노테이션
public class ArticleViewResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;


    public ArticleViewResponse(Article article){
        this.id = article.getId();
        this.content = article.getContent();
        this.title = article.getTitle();
        this.createdAt = article.getCreatedAt();
    }
}
