package com.gmail.stepan1983.DAO;

import com.gmail.stepan1983.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDAO extends JpaRepository<Client,Long> {

    @Query("SELECT c FROM Client c WHERE c.login=:login")
    Client getByLogin(@Param("login") String login);

    @Query("SELECT c FROM Client c WHERE c.email=:email")
    Client getByEmail(@Param("email") String email);

    @Query("SELECT c FROM Client c WHERE c.phone=:phone")
    Client getByPhone(@Param("phone") String phone);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Client u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Client u WHERE u.id = :id")
    boolean existsById(@Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Client u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Client u WHERE u.phone = :phone")
    boolean existsByPhone(@Param("phone") String phone);

}
