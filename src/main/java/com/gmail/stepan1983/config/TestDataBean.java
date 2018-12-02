package com.gmail.stepan1983.config;

import com.gmail.stepan1983.DAO.*;
import com.gmail.stepan1983.Service.*;
import com.gmail.stepan1983.model.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.sun.java.util.jar.pack.Package;

//@Component
//@Transactional
public class TestDataBean {
    @Autowired
    private ClientService clientService;

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;

    @Autowired
    StorageBooksDAO storageBooksDAO;

    @Autowired
    StorageBooksService storageBooksService;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    PublisherDAO publisherDAO;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    BookDAO bookDAO;

//    public EntityManager entityManager=entityManagerFactory.createEntityManager();

    @Autowired
    ClientGroupService clientGroupService;

    @Autowired
    ClientGroupDAO clientGroupDAO;

//    @Autowired
//    StockDAO stockDAO;

    @PersistenceContext
    EntityManager entityManager;

    public TestDataBean() {
    }

//    @PostConstruct
////    @Transactional
//    public void fillData() {
//        /* get avatars*/
////        entityManager.getTransaction().begin();
//        List<File> avatars = new ArrayList<>();
//        for (int i = 0; i < 11; i++) {
//            File file = new File("C:\\Users\\HOME\\Documents\\git\\JavaPro3\\JavaPro2\\OnlineBookStore2\\src\\main\\webapp\\static\\images\\avatar-" + i + ".jpg");
////            File file = new File("C:\\Users\\borysenko\\Documents\\GitHub\\JavaPro\\JavaPro\\OnlineBookStore2\\src\\main\\webapp\\static\\images\\avatar-" + i + ".jpg");
//            avatars.add(file);
//        }
//
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//        File image = new File("C:\\Users\\borysenko\\Documents\\GitHub\\JavaPro\\JavaPro\\OnlineBookStore2\\src\\main\\webapp\\static\\images\\12274312_1719690841584330_6387016554043425967_n.jpg");
////        File image = new File("C:\\Users\\HOME\\Documents\\git\\JavaPro3\\JavaPro2\\OnlineBookStore2\\src\\main\\webapp\\static\\images\\12274312_1719690841584330_6387016554043425967_n.jpg");
//
//
//        /* create first test clients*/
//        ClientGroup clientGroup1 = new ClientGroup("customers", "clientGroupDescription1", new ArrayList<Client>());
//        ClientGroup clientGroup2 = new ClientGroup("administration", "clientGroupDescription2", new ArrayList<Client>());
//
//        Client client1 = new Client("test1", encoder.encode("Password"), "email1@com",
//                "phone1", "Adress1", "name1", "lastname1", UserRole.CUSTOMER, clientGroup1, avatars.get(0));
//        Client client2 = new Client("admin", encoder.encode("Password"), "email2@com",
//                "phone2", "Adress2", "name2", "lastname2", UserRole.ADMIN, clientGroup2, avatars.get(1));
//
//
//        clientGroup1 = clientGroupService.addClientGroup(clientGroup1);
//        clientGroup2 = clientGroupService.addClientGroup(clientGroup2);
//        clientService.addClient(client1);
//        clientService.addClient(client2);
//
//
//        CategoryItem categoryItem = new CategoryItem("Category1", "category1 description", new ArrayList<BookItem>());
//        Publisher publisher = new Publisher("Publisher1", "Publisher1 adress",
//                "Publisher1 Description", new ArrayList<BookItem>());
//        Stock stock1 = new Stock("Stock1 adress", "Stock1 phone");
//
//        List<BookItem> orderList = new ArrayList<>();
//
//        categoryDAO.save(categoryItem);
////        stockDAO.save(stock1);
//        publisherDAO.save(publisher);
//        StorageBooks storageBooks = new StorageBooks("storageAdress1", "storagePfone1");
//        storageBooksService.addStorageBooks(storageBooks);
//        System.out.println(storageBooks);
//        for (int i = 0; i < 19; i++) {
//            /*create books*/
//
//
//            BookItem book = new BookItem("name" + i, "description" + i, "Author" + i, publisher,
//                    null, 100.0, storageBooks, image);
//
//            storageBooks.getBookQuantityMap().put(book, 10);
//
//            book = bookService.addBookItem(book);
////            bookDAO.save(book);
//
//            /*create clients*/
//            Client client = new Client("login" + i, encoder.encode("Password" + i), "email" + i + "@com",
//                    "phone" + i, "Adress" + i, "name" + i, "lastname" + i, UserRole.CUSTOMER, clientGroup1,
//                    (i < 11) ? avatars.get(i) : avatars.get(i - 11));
//
//
//            client = clientService.addClient(client);
//
//            /*create orders*/
//            if (i % 2 == 0) {
//                orderList.add(book);
//                orderList.add(book);
//            } else {
//                orderList.add(book);
//            }
//
//            Shipment shipment = new Shipment("shipment adress" + i, "processed", null);
//            Order order = new Order(orderList, client, shipment, (i % 2 == 0) ? OrderStatus.processed : OrderStatus.closed, new Date());
//            shipment.setOrder(order);
//            orderService.addOrder(order);
//            Order order2 = new Order(orderList, client, shipment, (i % 2 == 0) ? OrderStatus.unProcessed : OrderStatus.closed, new Date());
//            orderService.addOrder(order2);
//
//        }
//
//
//
//    }
}
