package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.DAO.OrderDAO;
import com.gmail.stepan1983.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private BookService bookService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ShipmentService shipmentService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    @Transactional
    public Order addOrder(Order order) {
        Client client=entityManager.merge(order.getClient());
        order.setClient(client);
        Order temp=entityManager.merge(order);
        updateBookItemsRaiting();
        return temp;
    }

    @Override
    @Transactional(readOnly = true)
    public Order getById(Long orderId) {

        Optional<Order> optional = orderDAO.findById(orderId);
        return optional.get();
    }

    @Override
    @Transactional
    public Order updateOrder(Order order) {

//        for (BookItem bookItem : order.getOrderList()) {
//            bookService.addBookItem(bookItem);
//        }
//        clientService.addClient(order.getClient());
//        shipmentService.addShipment(order.getShipment());

        Order temp= entityManager.merge(order);
        updateBookItemsRaiting();
      return temp;
//        orderDAO.save(order);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAll(Pageable pageable) {
        return orderDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        Sort sort = new Sort(Sort.Direction.ASC , "id");
        return orderDAO.findAll();
    }

    @Transactional
    @Override
    public List<Order> findAll(Integer page, Integer itemsPerPage, String sortBy, boolean sortDirection) {

        Sort sort = new Sort(sortDirection ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);

        Pageable pageable = PageRequest.of(page, itemsPerPage, sort);

        return orderDAO.findAll(pageable).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findByClient(Client client, Pageable pageable) {

        return orderDAO.findByClient(client, pageable).getContent();
    }

    @Override
    public List<Order> findByClient(Client client) {
        return orderDAO.findByClient2(client);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return orderDAO.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long count(Order orderExample) {
        return orderDAO.count();
    }

    @Override
    @Transactional
    public void deleteOrder(Order order) {

        Order tempOrder=entityManager.merge(order);
        entityManager.remove(tempOrder);
        updateBookItemsRaiting();
    }

    @Override
    @Transactional
    public long countByParam(String paramName, String paramValue){

        TypedQuery<Long> typedQuery=entityManager.createQuery("SELECT COUNT(o) FROM TheOrder o WHERE o."+paramName+"=:parValue", Long.class);
        if(paramName.equals("status")){
            typedQuery.setParameter("parValue",OrderStatus.valueOf(paramValue));
        }else {
            typedQuery.setParameter("parValue",paramValue);
        }

        return typedQuery.getSingleResult();
    }

    public void updateBookItemsRaiting() {
        List<Order> orders = orderDAO.findAll();
        List<BookItem> items = bookService.findAll();

        for (int i = 0; i < items.size(); i++) {
            items.get(i).setRating(0);
            for (int j = 0; j < orders.size(); j++) {
                List<BookItem> orderList = orders.get(j).getOrderList();
                for (BookItem bookItem : orderList) {
                    if (bookItem.getId() == items.get(i).getId()) {
                        items.get(i).setRating(items.get(i).getRating() + 1);
                    }
                }
            }
            bookService.updateBookItem(items.get(i));
        }
    }

    @Override
    public boolean existById(Long id) {
        return orderDAO.existsById(id);
    }
}
