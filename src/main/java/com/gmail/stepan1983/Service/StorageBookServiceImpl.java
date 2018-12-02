package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.DAO.StorageBooksDAO;
import com.gmail.stepan1983.model.StorageBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class StorageBookServiceImpl implements StorageBooksService {

    @Autowired
    StorageBooksDAO storageBooksDAO;

    @Override
    @Transactional
    public StorageBooks addStorageBooks(StorageBooks storageBooks) {
        return storageBooksDAO.save(storageBooks);
    }

    @Override
    public StorageBooks getById(Long storageBooksId) {
        return storageBooksDAO.getOne(storageBooksId);
    }

    @Override
    @Transactional
    public void updateStorageBooks(StorageBooks storageBooks) {

        storageBooksDAO.save(storageBooks);

    }

    @Override
    public List<StorageBooks> findAll() {
        return storageBooksDAO.findAll();
    }

    @Override
    public long count() {
        return storageBooksDAO.count();
    }
}
