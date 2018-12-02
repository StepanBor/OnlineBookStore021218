package com.gmail.stepan1983.DAO;

import com.gmail.stepan1983.model.Client;
import com.gmail.stepan1983.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long>, QueryByExampleExecutor<Order> {

    @Query("SELECT o FROM TheOrder o WHERE o.client= :client ORDER BY o.orderPrice DESC ")
    Page<Order> findByClient(@Param("client") Client client, Pageable pageable);

    @Query("SELECT o FROM TheOrder o WHERE o.client= :client ORDER BY o.orderPrice DESC ")
    List<Order> findByClient2(@Param("client") Client client);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM TheOrder o WHERE o.id = :id")
    boolean existsById(@Param("id") Long id);

}
