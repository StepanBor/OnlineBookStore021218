package com.gmail.stepan1983.DTO;


import com.gmail.stepan1983.Service.BookService;
import com.gmail.stepan1983.config.ContextProvider;
import com.gmail.stepan1983.model.BookItem;

public class BookItemDTO {

    BookService bookService = ContextProvider.getBean(com.gmail.stepan1983.Service.BookServiceImpl.class);

    private long id;

    private String bookName;

    private String description;

    private String author;

    private String publisher;

    private String category;

    private Double price;

    private Long storageBooksId;

    private int copiesInStock;

    private Double rating;

    private String ISBN;


    public BookItemDTO(long id, String bookName, String description, String author,
                       String publisher, String category, Double price, Long storageBooksId,
                       Double rating, String ISBN, int copiesInStock) {
        this.id = id;
        this.bookName = bookName;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.price = price;
        this.storageBooksId = storageBooksId;
        this.rating = rating;
        this.ISBN = ISBN;
        this.copiesInStock=copiesInStock;
    }

    public BookItemDTO() {
    }

    public BookItem toBookItem() {

//        System.out.println("\u001B[33m"+id);
        BookItem bookItem = bookService.getById(id);
//        bookItem.setRating(rating);
        bookItem.setPrice(price);
        bookItem.setPrice(price);
        bookItem.setDescription(description);
        bookItem.setBookName(bookName);
        bookItem.setISBN(ISBN);
        bookItem.getStorageBooks().getBookQuantityMap().put(bookItem,copiesInStock);
//        System.out.println("\u001B[33m"+bookItem+"LLLLLLLLLLLLLLLLLLL");
//        return bookService.getById(id);

        return bookItem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getStorageBooksId() {
        return storageBooksId;
    }

    public void setStorageBooksId(Long storageBooksId) {
        this.storageBooksId = storageBooksId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getCopiesInStock() {
        return copiesInStock;
    }

    public void setCopiesInStock(int copiesInStock) {
        this.copiesInStock = copiesInStock;
    }



    @Override
    public String toString() {
        return "BookItemDTO{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", storageBooksId=" + storageBooksId +
                ", rating=" + rating +
                ", ISBN=" + ISBN +
                ", copiesInStock" + copiesInStock +
                '}';
    }
}
