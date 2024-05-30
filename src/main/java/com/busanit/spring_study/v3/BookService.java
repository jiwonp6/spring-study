package com.busanit.spring_study.v3;

import com.busanit.spring_study.v2.Book;
import com.busanit.spring_study.v2.BookRepository;
import jakarta.transaction.Transactional;
    // => * 데이터를 조작하는 서비스 메소드는 @ Transactional 로 트랜잭션을 구현하는 것을 권장
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service    // 서비스(비즈니스) 계층임을 선언
public class BookService {
    // 서비스 -> 레포지토리
    // 서비스 계층은 Repository (데이터 접근 계층을 의존성 주입받아 사용)
    @Autowired
    private BookRepository bookRepository;

    // 모든 책 조회 메소드
    List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 하나의 책만 조회하는 메소드
    Book getBookById(Long id) {
        // 책을 조회하고 없으면 null 반환 (옵셔널)
        return bookRepository.findById(id).orElse(null);
    }

    // 책을 생성하는 메소드
    @Transactional  // 해당 서비스 내의 DB 접근을 트랜잭션으로 관리
    public Book createBook(Book book) {
        // 책을 데이터 베이스에 저장
        return bookRepository.save(book);
    }

    // 책 내용 변경
    @Transactional  // 해당 서비스 내의 DB 접근을 트랜잭션으로 관리
    public Book updateBook(Long id, Book updatedBook) {
        // 책 조회하여 내용 변경하고 저장
        Book book = bookRepository.findById(id).orElse(null);

        // 각 필드(컬럼)가 비어있는지 확인하고 업데이트
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

    // 책 삭제
    @Transactional  // 해당 서비스 내의 DB 접근을 트랜잭션으로 관리
    public boolean deleteBook(Long id) {
        // ID 기준으로 DB 에서 책 삭제
        Book book = bookRepository.findById(id).orElse(null);

        if (book != null) {
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
