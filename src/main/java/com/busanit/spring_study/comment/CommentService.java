package com.busanit.spring_study.comment;

import com.busanit.spring_study.article.Article;
import com.busanit.spring_study.article.ArticleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    // Entity -> Dto로 변환하여 전달
    List<CommentDto> getAllComments(){
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(Comment::toDto).toList();
    }
    Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Transactional
    public CommentDto createComment(CommentDto dto) {
        // Article ID 가 존재하지 않는 경우
        Article article = articleRepository.findById(dto.getArticleId()).orElse(null);

        if (article == null) {
            throw new RuntimeException("존재하지 않는 Article");
        }

        Comment saved = commentRepository.save(dto.toEntity(article));
        return saved.toDto();
    }

    @Transactional
    public CommentDto updatedComment(Long id, CommentDto updatedComment) {
        Comment comment = commentRepository.findById(id).orElse(null);

        if (comment != null) {
            if (updatedComment.getContent() != null) {
                comment.setContent(updatedComment.getContent());
            }
            if (updatedComment.getAuthor() != null) {
                comment.setAuthor(updatedComment.getAuthor());
            }
            return commentRepository.save(comment).toDto();
        } else {
            return null;
        }
    }

    @Transactional
    public boolean deleteComment(Long id) {
        CommentDto comment = commentRepository.findById(id).orElse(null).toDto();

        if (comment != null) {
            commentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
