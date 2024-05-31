package com.busanit.spring_study.article;

import com.busanit.spring_study.comment.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

//    // streamAPI, toDto() 메소드 사용 전
//    List<ArticleDto> getAllArticles(){
//        List<Article> articles = articleRepository.findAll();
//
//        List<ArticleDto> articleDtoList = new ArrayList<>();
//
//        // 조회된 전체 엔티티를 순회하며 Article -> ArticleDto 로 변경
//        for (Article article : articles) {
//            // 게시글을 참조하는 댓글 전체 순회하며 Comment -> CommentDto 로 변경
//            List<Comment> comments = article.getComments();
//            List<CommentDto> commentDtoList = new ArrayList<>();
//            for (Comment comment : comments) {
//                CommentDto commentDto = new CommentDto(comment.getId(), comment.getContent(), comment.getAuthor(), article.getId());
//                commentDtoList.add(commentDto);
//            }
//
//            // ArticleDto 생성
//            ArticleDto articleDto = new ArticleDto(article.getId(),
//                                                    article.getTitle(),
//                                                    article.getContent(),
//                                                    article.getAuthor(),
//                                                    commentDtoList);
//            articleDtoList.add(articleDto);
//        }
//        return articleDtoList;
//    }

    List<ArticleDto> getAllArticles(){
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(Article::toDto).toList();
    }

    ArticleDto getArticleById(Long id) {
        // return articleRepository.findById(id).orElse(null);
        Article article = articleRepository.findById(id).orElse(null);
        if (article != null) {
            return article.toDto();
        } else {
            return null;

        }
    }

    @Transactional
    public ArticleDto createArticle(ArticleDto dto) {
        // return articleRepository.save(article);article.
        Article saved = articleRepository.save(dto.toEntity());
        return saved.toDto();
    }

    @Transactional
    public ArticleDto updatedArticle(Long id, ArticleDto updatedArticle) {
        Article article = articleRepository.findById(id).orElse(null);

        if (article != null) {
            if (updatedArticle.getTitle() != null) {
                article.setTitle(updatedArticle.getTitle());
            }
            if (updatedArticle.getAuthor() != null) {
                article.setAuthor(updatedArticle.getAuthor());
            }
            return articleRepository.save(article).toDto();
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElse(null);

        if (article != null) {
            articleRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /* 쿼리 메소드 사용 */
    public List<ArticleDto> getArticleByAuthor(String author) {
        return articleRepository.findByAuthor(author).stream().map(Article::toDto).toList();
    }
    public List<ArticleDto> getArticleByTitleContaining(String title) {
        return articleRepository.findByTitleContaining(title).stream().map(Article::toDto).toList();
    }

}
