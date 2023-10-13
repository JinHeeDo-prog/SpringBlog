package com.example.ch05.controller;

import com.example.ch05.domain.Article;
import com.example.ch05.dto.AddArticleRequest;
import com.example.ch05.dto.UpdateArticleRequest;
import com.example.ch05.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BlogRepository blogRepository;

    @BeforeEach
    public void mocMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        blogRepository.deleteAll();
    }
    @DisplayName("addArticle: 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest request = new AddArticleRequest(title, content);

        // 객체를 JSON 으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(request);
        System.out.println(requestBody);

        // when
        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Article> articleList = blogRepository.findAll();


        assertThat(articleList.get(0).getTitle()).isEqualTo(title);
        assertThat(articleList.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllArticles :: 블로그 글 목록 조회 성공")
    @Test
    public void findAllArticles() throws Exception{
        //given
        final String url = "/api/articles";
        final String title = "Title A";
        final String content = "Content AA";

        blogRepository.save(Article.builder().title(title).content(content).build());


        //when
        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].content").value(content));

    }

    @DisplayName("finadArticle : 블로그 글 조회 성공")
    @Test
    public void findArticle() throws Exception{
        //given
        final String url = "/api/articles/{id}";
        final String title = "제목입니다.";
        final String content = "내용입니다.";

        Article savedArticle = blogRepository.save(Article.builder().title(title).content(content).build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()).accept(MediaType.APPLICATION_JSON));


        //then
        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.title").value(title)).andExpect(jsonPath("$.content").value(content));

    }
    @DisplayName("deleteArticle: 블로그 글 삭제 성공")
    @Test
    public void deleteArticle() throws Exception {
        //given
        final String url = "/api/articles/{id}";
        final String title = "삭제될 제목입니다.";
        final String content = "삭제될 내용입니다.";

        Article savedArticle = blogRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        //when
        ResultActions resultActions = mockMvc.perform(
                delete(url, savedArticle.getId())
        );

        //then
        resultActions.andExpect(status().isOk());

        List<Article> articleList = blogRepository.findAll();
        assertThat(articleList.size()).isEqualTo(0);
    }
    @DisplayName("updateArticle:: 블로그 글 수정")
    @Test
    public void updateArticle() throws Exception{

        //given
        final String url = "/api/articles/{id}";
        final String title = "제목입니다.";
        final String content = "내용입니다.";

        Article savedArticle = blogRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );

        final String newTitle = "제목수정";
        final String newContent = "내용수정";
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest(newTitle, newContent);

        ResultActions resultActions = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(updateArticleRequest)));

        resultActions.andExpect(status().isOk());
        Article article = blogRepository.findById(savedArticle.getId()).get();
        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);

        //then
        // - status
        // - title 값
        // - content 값 확인
    }
}