package com.busanit.spring_study.comment;

import com.busanit.spring_study.article.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private String author;
    private Long articleId;

//  // Dto -> 엔티티 변환 메소드 (생성 메소드), (방법 2)
    public Comment toEntity(Article article) {
        Comment build = Comment.builder()
                                .id(id)
                                .content(content)
                                .author(author)
                                .article(article)
                                .build();
        return build;
    }
}
