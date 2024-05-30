package com.busanit.spring_study.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ver2/books")
public class BookController2 {
    // 스프링의 의존성 주입(DI: Dependency Injection)
    @Autowired  // 해당 멤버 변수에 BookRepository 인스턴스를 주입
    private BookRepository bookRepository;

    // CREATE
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        // 책을 데이터 베이스에 저장
        return bookRepository.save(book);
    }

    // READ
    @GetMapping
    public List<Book> getAllBooks() {
        // 모든 책을 데이터베이스에서 조회
        return bookRepository.findAll();
    }

    // READ 'a' Book
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        // ID 로 특정 책 조회
        return bookRepository.findById(id).orElse(null);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        // 책 조회하여 내용 변경하고 저장
        Book book = bookRepository.findById(id).orElse(null);

        if (book != null) {
            if (updatedBook.getTitle() != null) {
                book.setTitle(updatedBook.getTitle());
            }
            if (updatedBook.getAuthor() != null) {
                book.setAuthor(updatedBook.getAuthor());
            }
            return bookRepository.save(book);
        } else {
            return null;
        }
    }
    
    // DELETE
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        // ID 기준으로 DB 에서 책 삭제
        Book book = bookRepository.findById(id).orElse(null);

        if (book != null) {
            bookRepository.deleteById(id);
            return "책이 삭제되었습니다.";
        } else {
            return "존재하지 않는 책입니다.";
        }
    }

}
