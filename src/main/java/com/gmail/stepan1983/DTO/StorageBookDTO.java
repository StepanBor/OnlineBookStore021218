package com.gmail.stepan1983.DTO;

import com.gmail.stepan1983.Service.BookService;
import com.gmail.stepan1983.Service.ClientService;
import com.gmail.stepan1983.Service.StorageBooksService;
import com.gmail.stepan1983.config.ContextProvider;
import com.gmail.stepan1983.model.BookItem;
import com.gmail.stepan1983.model.StorageBooks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageBookDTO {


    BookService bookService = ContextProvider.getBean(com.gmail.stepan1983.Service.BookServiceImpl.class);

    private long id;

    private String storageAddress;

    private String storagePhone;

    private List<StorageEntry> storageEntryList;

    public StorageBookDTO(long id, String storageAddress,
                          String storagePhone, List<StorageEntry> storageEntryList) {
        this.id = id;
        this.storageAddress = storageAddress;
        this.storagePhone = storagePhone;
        this.storageEntryList = storageEntryList;
    }

    public StorageBookDTO() {
    }

    public class StorageEntry {
        private Long bookItemId;
        private Integer copiesInStock;

        public StorageEntry(Long bookItemId, Integer copiesInStock) {
            this.bookItemId = bookItemId;
            this.copiesInStock = copiesInStock;
        }

        public StorageEntry() {
        }

        public Long getBookItemId() {
            return bookItemId;
        }

        public void setBookItemId(Long bookItemDTO) {
            this.bookItemId = bookItemDTO;
        }

        public Integer getCopiesInStock() {
            return copiesInStock;
        }

        public void setCopiesInStock(Integer copiesInStock) {
            this.copiesInStock = copiesInStock;
        }

        @Override
        public String toString() {
            return "StorageEntry{" +
                    "bookItemId=" + bookItemId +
                    ", copiesInStock=" + copiesInStock +
                    '}';
        }
    }

    public StorageBooks toStorageBooks() {
        Map<BookItem, Integer> storageMap=new HashMap<>();
        for (StorageEntry storageEntry : storageEntryList) {
            storageMap.put(bookService.getById(storageEntry.getBookItemId()), storageEntry.getCopiesInStock());
        }
        StorageBooks storageBooks=new StorageBooks(storageAddress,storagePhone);
        storageBooks.setId(id);
        storageBooks.setBookQuantityMap(storageMap);
        return storageBooks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public List<StorageEntry> getStorageEntryList() {
        return storageEntryList;
    }

    public void setStorageEntryList(List<StorageEntry> storageEntryList) {
        this.storageEntryList = storageEntryList;
    }

    @Override
    public String toString() {
        return "StorageBookDTO{" +
                "id=" + id +
                ", storageAddress='" + storageAddress + '\'' +
                ", storagePhone='" + storagePhone + '\'' +
                ", storageEntryList=" + storageEntryList +
                '}';
    }
}
