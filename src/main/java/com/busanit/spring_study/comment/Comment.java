package com.busanit.spring_study.comment;

import com.busanit.spring_study.article.Article;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String author;

    // 1 대 N 관계에서 외래키를 갖는 엔티티는 (N)
    // 외래키 필드 지정
    // => 댓글은 Many 에 해당
    @ManyToOne  // 관계 설정
    @JoinColumn(name = "article_id", nullable = false) // 외래키로 사용될 컬럼 이름, null 불가능
    private Article article;

    // 엔티티 -> Dto 변환 메소드
    public CommentDto toDto() {
        return new CommentDto(id, content, author, article.getId());
    }

//    // Dto -> 엔티티 변환 메소드 (생성 메소드), (방법 1)
//    public static Comment createComment(CommentDto commentDto) {
//        Comment comment = new Comment();
//        comment.setContent(commentDto.getContent());
//        comment.setAuthor(comment.getAuthor());
//
//        Article article = new Article();
//        article.setId(commentDto.getArticleId());
//        comment.setArticle(article);
//        return comment;
//    }
}
