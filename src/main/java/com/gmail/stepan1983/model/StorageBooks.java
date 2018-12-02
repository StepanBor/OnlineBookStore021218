package com.gmail.stepan1983.model;

import com.gmail.stepan1983.DTO.StorageBookDTO;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "StorageBooks1")
public class StorageBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stogrageBooksId")
    private long id;

    @ElementCollection
    @CollectionTable(name = "BookStorage",
            joinColumns = @JoinColumn(name = "STORE"))
    @Column(name = "COPIES_IN_STOCK")
    @MapKeyJoinColumn(name = "Book", referencedColumnName = "bookId")
    private Map<BookItem, Integer> bookQuantityMap = new HashMap<>();


    private String storageAddress;

    private String storagePhone;

//    private Long bookQuantity;

    public StorageBooks(/*BookItem book, Long bookQuantity,*/ String storageAddress,
                                                              String storagePhone) {
//        this.book = book;
//        this.bookQuantity = bookQuantity;
        this.storageAddress = storageAddress;
        this.storagePhone = storagePhone;

    }

    public StorageBooks() {
    }

    public StorageBookDTO toStorageDTO() {
        StorageBookDTO storageBookDTO = new StorageBookDTO();

        storageBookDTO.setId(id);
        storageBookDTO.setStorageAddress(storageAddress);
        storageBookDTO.setStoragePhone(storagePhone);
        storageBookDTO.setStorageEntryList(new ArrayList<>());

        Set<Map.Entry<BookItem,Integer>> mapEntrySet = bookQuantityMap.entrySet();

        for (Map.Entry<BookItem, Integer> bookItemIntegerEntry : mapEntrySet) {
            StorageBookDTO.StorageEntry storageEntry=
                    storageBookDTO.new StorageEntry(bookItemIntegerEntry.getKey().getId(),bookItemIntegerEntry.getValue());
            storageBookDTO.getStorageEntryList().add(storageEntry);
        }

        return storageBookDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public BookItem getBook() {
//        return book;
//    }
//
//    public void setBook(BookItem book) {
//        this.book = book;
//    }
//
//    public Long getBookQuantity() {
//        return bookQuantity;
//    }
//
//    public void setBookQuantity(Long bookQuantity) {
//        this.bookQuantity = bookQuantity;
//    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public String getStoragePhone() {
        return storagePhone;
    }

    public void setStoragePhone(String storagePhone) {
        this.storagePhone = storagePhone;
    }

    public Map<BookItem, Integer> getBookQuantityMap() {
        return bookQuantityMap;
    }

    public void setBookQuantityMap(Map<BookItem, Integer> bookQuantityMap) {
        this.bookQuantityMap = bookQuantityMap;
    }

    @Override
    public String toString() {
        return "StorageBooks{" +
                "id=" + id +
//                ", bookQuantityMap=" + bookQuantityMap +
                ", storageAddress='" + storageAddress + '\'' +
                ", storagePhone='" + storagePhone + '\'' +
                '}';
    }
}

