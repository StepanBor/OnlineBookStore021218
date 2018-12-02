package com.gmail.stepan1983.DAO;

import com.gmail.stepan1983.model.Client;
import com.gmail.stepan1983.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherDAO extends JpaRepository<Publisher,Long> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Publisher u WHERE u.id = :id")
    boolean existsById(@Param("id") Long id);

    @Query("SELECT p FROM Publisher p WHERE p.publisherName=:name")
    Publisher getByName(@Param("name") String name);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Publisher p WHERE p.publisherName = :name")
    boolean existsByName(@Param("name") String name);
}
