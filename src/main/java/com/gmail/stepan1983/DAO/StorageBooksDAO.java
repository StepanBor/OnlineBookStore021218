package com.gmail.stepan1983.DAO;

import com.gmail.stepan1983.model.StorageBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageBooksDAO extends JpaRepository<StorageBooks,Long> {
}
