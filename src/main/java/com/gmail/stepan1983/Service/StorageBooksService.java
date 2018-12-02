package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.model.StorageBooks;

import java.util.List;

public interface StorageBooksService {

    StorageBooks addStorageBooks(StorageBooks storageBooks);

    StorageBooks getById(Long storageBooksId);

    void updateStorageBooks(StorageBooks storageBooks);

    List<StorageBooks> findAll();

    long count();

}
