package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.DAO.*;
import com.gmail.stepan1983.config.ConsoleColors;
import com.gmail.stepan1983.model.BookItem;
import com.gmail.stepan1983.model.CategoryItem;
import com.gmail.stepan1983.model.Publisher;
import com.gmail.stepan1983.model.StorageBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PublisherDAO publisherDAO;

    @Autowired
    private CategoryDAO categoryDAO;


    @Autowired
    private StorageBooksDAO storageBooksDAO;

    @Autowired
    PublisherService publisherService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    StorageBooksService storageBooksService;

    @Override
    @Transactional
    public BookItem addBookItem(BookItem bookItem) {

        StorageBooks storageBooks = storageBooksService.findAll().get(0);

        if (bookDAO.existsByBookName(bookItem.getBookName())) {
            return bookDAO.getByBookName(bookItem.getBookName());
        }

        Publisher tempPublisher = publisherService.getByName(bookItem.getPublisher().getPublisherName());
        if (tempPublisher == null) {
            tempPublisher = bookItem.getPublisher();
            tempPublisher = publisherService.addPublisher(tempPublisher);
        }
        CategoryItem tempCategoryItem = categoryService.getByName(bookItem.getCategory().getCategoryName());
        if (tempCategoryItem == null) {
            tempCategoryItem = bookItem.getCategory();
            tempCategoryItem = categoryService.addCategoryItem(tempCategoryItem);
        }

        bookItem.setPublisher(tempPublisher);
        bookItem.setCategory(tempCategoryItem);
        tempPublisher.getBooks().add(bookItem);
        tempCategoryItem.getBooks().add(bookItem);
        entityManager.persist(bookItem);
        bookItem.setStorageBooks(storageBooks);
        storageBooks.getBookQuantityMap().put(bookItem, 10);

        return entityManager.merge(bookItem);

    }

    @Override
    @Transactional
    public void addBookList(List<BookItem> bookList) {
        Set<Publisher> publisherSet = new HashSet<>();
        Set<CategoryItem> categoryItemSet = new HashSet<>();
        StorageBooks storageBooks = storageBooksService.findAll().get(0);

        for (int i = 0; i < bookList.size(); i++) {
            BookItem bookItem = bookList.get(i);

            if (bookDAO.existsByBookName(bookItem.getBookName())) {
                continue;
            }

            Publisher tempPublisher = publisherService.getByName(bookItem.getPublisher().getPublisherName());
            if (tempPublisher == null) {
                tempPublisher = bookItem.getPublisher();
                tempPublisher = publisherService.addPublisher(tempPublisher);
            }
            CategoryItem tempCategoryItem = categoryService.getByName(bookItem.getCategory().getCategoryName());
            if (tempCategoryItem == null) {
                tempCategoryItem = bookItem.getCategory();
                tempCategoryItem = categoryService.addCategoryItem(tempCategoryItem);
            }
            publisherSet.add(tempPublisher);
            categoryItemSet.add(tempCategoryItem);

            bookItem.setPublisher(tempPublisher);
            bookItem.setCategory(tempCategoryItem);
            tempPublisher.getBooks().add(bookItem);
            tempCategoryItem.getBooks().add(bookItem);
            entityManager.persist(bookItem);
            bookItem.setStorageBooks(storageBooks);
            storageBooks.getBookQuantityMap().put(bookItem, 10);
            entityManager.merge(bookItem);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BookItem getById(Long bookId) {
        return bookDAO.getOne(bookId);
    }

    @Override
    public List<BookItem> getByAuthor(String author) {
        return bookDAO.getByAuthor(author);
    }

    @Override
    public List<BookItem> getByCategory(String category) {
        return bookDAO.getByCategory(category);
    }

    @Override
    public List<BookItem> getByPublisher(String publisher) {
        return bookDAO.getByPublisher(publisher);
    }

    @Override
    public BookItem getByBookName(String bookName) {
        return bookDAO.getByBookName(bookName);
    }

    @Override
    @Transactional
    public BookItem updateBookItem(BookItem bookItem) {
        return entityManager.merge(bookItem);
    }

    @Override
    @Transactional
    public void deleteBookItem(BookItem bookItem) {
        BookItem temp = entityManager.merge(bookItem);
        temp.getPublisher().getBooks().remove(temp);
        temp.getCategory().getBooks().remove(temp);
        entityManager.remove(temp);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookItem> findAll(Pageable pageable) {
        return bookDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookItem> findAll(Integer page, Integer itemsPerPage, String sortBy, boolean sortDirection) {

        Sort sort = new Sort(sortDirection ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);

        Pageable pageable = PageRequest.of(page, itemsPerPage, sort);

        return bookDAO.findAll(pageable).getContent();
    }

    @Override
    public List<BookItem> findAll() {
        return bookDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return bookDAO.count();
    }

    @Transactional
    public Double getAvgRating() {

        TypedQuery<Double> avgRating = entityManager.createQuery("Select avg(s.rating) from BookItem s", Double.class);

        return avgRating.getSingleResult();
    }

    @Transactional
    public void setStorageBook(BookItem book) {
        StorageBooks storageBooks = storageBooksService.findAll().get(0);
        book.setStorageBooks(storageBooks);
        storageBooks.getBookQuantityMap().put(book, 10);
        entityManager.merge(storageBooks);
    }

    @Override
    public boolean existsById(Long id) {
        return bookDAO.existsById(id);
    }
}
