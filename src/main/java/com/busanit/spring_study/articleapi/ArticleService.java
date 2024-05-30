package com.busanit.spring_study.articleapi;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    List<Article> getAllArticles(){
        return articleRepository.findAll();
    }
    Article getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Transactional
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Transactional
    public Article updatedArticle(Long id, Article updatedArticle) {
        Article article = articleRepository.findById(id).orElse(null);

        if (article != null) {
            if (updatedArticle.getTitle() != null) {
                article.setTitle(updatedArticle.getTitle());
            }
            if (updatedArticle.getAuthor() != null) {
                article.setAuthor(updatedArticle.getAuthor());
            }
            return articleRepository.save(article);
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
}
