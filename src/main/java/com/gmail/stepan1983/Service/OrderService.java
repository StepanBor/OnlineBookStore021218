package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.model.Client;
import com.gmail.stepan1983.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Order addOrder(Order order);

    void deleteOrder(Order order);

    Order getById(Long bookId);

    Order updateOrder(Order order);

    Page<Order> findAll(Pageable pageable);

    List<Order> findAll();

    List<Order> findAll(Integer page, Integer itemsPerPage, String sortBy, boolean sortDirection);

    List<Order> findByClient(Client client, Pageable pageable);

    List<Order> findByClient(Client client);

    boolean existById(Long id);

    long count();

    long count(Order orderExample);

    long countByParam(String paramName, String paramValue);
}
