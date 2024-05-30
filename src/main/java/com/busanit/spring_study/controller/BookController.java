package com.busanit.spring_study.controller;

import com.busanit.spring_study.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController                 // REST API 임을 표시
@RequestMapping("/api/books")   // HTTP 요청 엔드 포인트의 시작 부분을 설정
public class BookController {
    // 책 데이터를 저장할 리스트
    List<Book> books = new ArrayList<>();

    // 새로운 책 생성
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        book.setId((long) (books.size() + 1));  // ID 생성
        books.add(book);                        // 데이터 리스트에 추가
        return book;
    }
    
    // 모든 책 조회(Read)
    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    // 특정 책 조회
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        Book searchedBook = books.stream()
                                .filter(book -> book.getId().equals(id))
                                .findFirst()
                                .orElse(null);
        return searchedBook;
    }

    // 수정(Update)
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book book = getBookById(id);

        if (book != null) {
            // 요청 본문으로 준 데이터 업데이트
            if (updatedBook.getTitle() != null) {
                book.setTitle(updatedBook.getTitle());
            }
            if (updatedBook.getAuthor() != null) {
                book.setAuthor(updatedBook.getAuthor());
            }
        }

        return book;
    }

    // 삭제(Delete)
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        boolean deletedOrNot = books.removeIf(book -> book.getId().equals(id));
        if (deletedOrNot) {
            return "책(" + id + ")이 삭제 되었습니다.";
        } else {
            return "존재하지 않는 id 입니다.";
        }
    }
}
