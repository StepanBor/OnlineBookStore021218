package com.gmail.stepan1983.DAO;

import com.gmail.stepan1983.model.BookItem;
import com.gmail.stepan1983.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDAO extends JpaRepository<BookItem,Long> {

    @Query("SELECT b FROM BookItem b WHERE b.author=:author")
    List<BookItem> getByAuthor(@Param("author") String author);

    @Query("SELECT b FROM BookItem b WHERE b.category.categoryName=:category")
    List<BookItem> getByCategory(@Param("category") String category);

    @Query("SELECT b FROM BookItem b WHERE b.publisher.publisherName=:publisher")
    List<BookItem> getByPublisher(@Param("publisher") String publisher);

    @Query("SELECT b FROM BookItem b WHERE b.bookName=:bookName")
    BookItem getByBookName(@Param("bookName") String bookName);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM BookItem b WHERE b.bookName = :bookName")
    boolean existsByBookName(@Param("bookName") String bookName);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM BookItem b WHERE b.id = :id")
    boolean existsById(@Param("id") Long id);

}
