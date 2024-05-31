package com.busanit.spring_study.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleDto article) {
        ArticleDto createdArticleDto = articleService.createArticle(article);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticleDto);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        List<ArticleDto> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        ArticleDto articleDto = articleService.getArticleById(id);

        if (articleDto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(articleDto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable Long id, @RequestBody ArticleDto updatedArticle) {
        ArticleDto article = articleService.updatedArticle(id, updatedArticle);

        if (article == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(article);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        boolean isDeleted = articleService.deleteArticle(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<ArticleDto>> getArticleByAuthor(@PathVariable String author) {
        List<ArticleDto> articleByAuthor = articleService.getArticleByAuthor(author);

        if (articleByAuthor == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            for (ArticleDto articleDto : articleByAuthor) {
                if (!articleDto.getAuthor().equals(author)){
                    return ResponseEntity.notFound().build();
                }
            }
            return ResponseEntity.ok(articleByAuthor);
        }
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<List<ArticleDto>> getArticleByTitleContaining(@PathVariable String title) {
        List<ArticleDto> articleByTitleContaining = articleService.getArticleByTitleContaining(title);

        if (articleByTitleContaining == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(articleByTitleContaining);
        }
    }
}
