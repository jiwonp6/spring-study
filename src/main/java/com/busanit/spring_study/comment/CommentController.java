package com.busanit.spring_study.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/comments")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment) {
        CommentDto createdComment = commentService.createComment(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);

        if (comment == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(comment.toDto());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody Comment updatedComment) {
        CommentDto comment = commentService.updatedComment(id, updatedComment.toDto());

        if (comment == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(comment);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        boolean isDeleted = commentService.deleteComment(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
