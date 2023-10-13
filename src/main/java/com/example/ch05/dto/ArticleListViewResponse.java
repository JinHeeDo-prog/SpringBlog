package com.example.ch05.dto;

import com.example.ch05.domain.Article;
import lombok.Getter;

@Getter
public class ArticleListViewResponse {

    private Long id;
    private String title;
    private String content;


    public ArticleListViewResponse(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }

}
