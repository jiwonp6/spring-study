package com.busanit.spring_study.v3;

import com.busanit.spring_study.v2.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 표현 계층: 사용자로부터 요청을 받고 응답을 해주는 일을 전담
@RestController                 // 표현 계층임을 선언
@RequestMapping("api/ver3/books") // 기본 경로
public class BookV3Controller {
    // ResponseEntity: HTTP 응답을 표현하는 객체
    // ResponseEntity.ok(): 200
    // ResponseEntity.status(상태코드).body(엔티티): 해당 상태코드와 데이터 본문을 응답
    // ResponseEntity.notFound().build(): 404 Not Found Exception

    // 컨트롤러 -> 서비스
    @Autowired  // 해당 멤버 변수에 BookService 인스턴스를 주입
    private BookService bookService;

    // CREATE
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        // 데이터베이스 접근은 서비스 계층에 위임
        Book createdBook = bookService.createBook(book);
        
        // 상태코드 201번(CREATED)에 응답 본문을 담아서 리턴
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    // READ
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // READ 'a' Book
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);

        if (book == null) {
            // 책이 없으면 404 Not Found 응답
            return ResponseEntity.notFound().build();
        } else {
            // 책이 있으면 200 OK 응답과 함께 책 객체 리턴
            return ResponseEntity.ok(book);
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book book = bookService.updateBook(id, updatedBook);

        if (book == null) {
            // 책이 없으면 404 Not Found 응답
            return ResponseEntity.notFound().build();
        } else {
            // 책이 있으면 200 OK 응답과 함께 책 객체 리턴
            return ResponseEntity.ok(book);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteBook(id);
        if (!isDeleted) {
            // 책 삭제 실패하면 404 Not Found 응답
            return ResponseEntity.notFound().build();
        } else {
            // 책을 삭제하면 200 OK 응답
            return ResponseEntity.ok().build();
        }
    }
}
