package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.model.BookItem;
import com.gmail.stepan1983.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookService {

    BookItem addBookItem(BookItem bookItem);

    void addBookList(List<BookItem> bookList);

    BookItem getById(Long bookId);

    List<BookItem> getByAuthor(String author);

    List<BookItem> getByCategory(String category);

    List<BookItem> getByPublisher(String publisher);

    BookItem getByBookName(String bookName);

    BookItem updateBookItem(BookItem bookItem);

    void deleteBookItem(BookItem bookItem);

    Page<BookItem> findAll(Pageable pageable);

    List<BookItem> findAll(Integer page, Integer itemsPerPage, String sortBy, boolean sortDirection);

    List<BookItem> findAll();

    long count();

    Double getAvgRating();

    boolean existsById(Long id);
}
