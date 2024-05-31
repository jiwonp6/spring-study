package com.busanit.spring_study.article;

import com.busanit.spring_study.comment.Comment;
import com.busanit.spring_study.comment.CommentDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// 롬복
// @Data                    : 게터, 세터, ToString, Equals, HashCode, @RequiredArgsConstructor 를 한 번에 적용
// @Getter                  : 게터만 생성
// @Setter                  : 세터만 생성
// @ToString                : ToString 만 생성
// @EqualsAndHashCode       : Equals, HashCode 생성
// @NoArgsConstructor       : 기본 생성자만 생성
// @AllArgsConstructor      : 모든 필드 인자로 갖는 생성자
// @RequiredArgsConstructor : final 또는 @not null 이 붙은 필드만 매개변수로 갖는 생성자 (없을 경우 기본생성자)

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder    // 빌더: 빌더 패턴
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String author;

    // 양방향 관계: 부모 객체가 자식을 알고 있음, 자식 객체도 부모 객체를 알고 있음
    // 1 대 N 관계: 하나의 게시글은 여러 개의 댓글을 가질 수 있다.
    // mappedBy: Comment 엔티티의 article 필드와 매핑이 됨
    // cascade: 삭제/수정/저장 시 관련된 상태를 모두 전이(같이 삭제/수정/저장), 영속성 전이
    // orphanRemoval: 부모 엔티티에서 자식 엔티티의 관계가 끊어질 때 자동으로 DB에서 제거
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    // 엔티티 -> Dto 변환 메소드
    public ArticleDto toDto() {
        ArticleDto articleDto;
        if (comments != null) {
            List<CommentDto> commentDtoList = comments.stream().map(Comment::toDto).toList();
            articleDto = new ArticleDto(id, title, content, author, commentDtoList);
        } else {
            articleDto = new ArticleDto(id, title, content, author, null);
        }
        return articleDto;
    }
//    // Dto -> 엔티티 변환 메소드 (방법 1)
//    public static Article createArticle(ArticleDto dto) {
//        Article article = new Article();
//        article.setTitle(dto.getTitle());
//        article.setContent(dto.getContent());
//        article.setAuthor(dto.getAuthor());
//        return article;
//    }

}
