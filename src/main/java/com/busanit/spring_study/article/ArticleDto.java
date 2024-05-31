package com.busanit.spring_study.article;

import com.busanit.spring_study.comment.Comment;
import com.busanit.spring_study.comment.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 데이터 전송 객체 (사용자에게 보여주고자 하는 객체들)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private List<CommentDto> comments;

//  // Dto -> 엔티티 변환 메소드 (방법 2)
    public Article toEntity() {
        // DTO -> 엔티티 필드 매핑
        Article article = Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        // DTO(댓글 리스트) -> 엔티티(댓글 리스트)
        if (comments != null) {
            List<Comment> commentList = comments.stream().map(commentDTO ->
                            commentDTO.toEntity(article))
                    .toList();
            article.setComments(commentList);
        }

        return article;
    }
}
