package com.gmail.stepan1983.config;

import com.gmail.stepan1983.DAO.*;
import com.gmail.stepan1983.Service.*;
import com.gmail.stepan1983.model.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
//@Transactional
//@Service
public class FillDataBase {

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

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Autowired
    EntityManagerFactory entityManagerFactory;
//    public EntityManager entityManager=entityManagerFactory.createEntityManager();

    @Autowired
    ClientGroupService clientGroupService;

    @Autowired
    ClientGroupDAO clientGroupDAO;

    @Autowired
    PasswordEncoder encoder;


    public FillDataBase() {
    }

//    @PostConstruct
//    @Transactional
    public void fillData() {
        System.out.println(ConsoleColors.RED+"fill data"+ConsoleColors.RESET);
        File excelFile=new File("");
        try {
            excelFile=new ClassPathResource("/bookItems/books_10_11_18.xls").getFile();
            System.out.println(ConsoleColors.RED+excelFile.getAbsolutePath()+ConsoleColors.RESET);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        File excelFile = new File("f:\\Drive\\Литература\\Java\\books111018\\Books111018.xls");
//        File excelFile = new File("d:\\GoogleDrive\\Литература\\Java\\books111018\\Books111018.xls");

        List<BookItem> bookItemList = readFromExcel(excelFile);
        Date today = new Date();
        try {
            Class.forName("com.gmail.stepan1983.config.ConsoleColors");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        /* get avatars*/
//        platformTransactionManager.getTransaction(null);
        List<File> avatars = new ArrayList<>();
        for (int i = 0; i < 11; i++) {

//            File file = new File("C:\\Users\\HOME\\Documents\\git\\JavaPro3\\JavaPro2\\OnlineBookStore2\\src\\main\\webapp\\static\\images\\avatar-" + i + ".jpg");
//            File file = new File("C:\\Users\\borysenko\\Documents\\GitHub\\JavaPro\\JavaPro\\OnlineBookStore2\\src\\main\\webapp\\static\\images\\avatar-" + i + ".jpg");
            File file = null;
            try {
                file = new ClassPathResource("/avatars/avatar-" + i + ".jpg").getFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            avatars.add(file);
        }

//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

//        File image = new File("C:\\Users\\borysenko\\Documents\\GitHub\\JavaPro\\JavaPro\\OnlineBookStore2\\src\\main\\webapp\\static\\images\\12274312_1719690841584330_6387016554043425967_n.jpg");
//        List<File> bookCovers = new ArrayList<>();
//        for (int i = 0; i < 14; i++) {
//            File file = new File("f:\\Drive\\Литература\\Java\\Картинки для книг\\" + (i + 1) + ".jpg");
//            bookCovers.add(file);
//        }


//        File image = new File("C:\\Users\\HOME\\Documents\\git\\JavaPro3\\JavaPro2\\OnlineBookStore2\\src\\main\\webapp\\static\\images\\12274312_1719690841584330_6387016554043425967_n.jpg");


        /* create first test clients*/
        ClientGroup clientGroup1 = new ClientGroup("customers", "clientGroupDescription1", new ArrayList<Client>());
        ClientGroup clientGroup2 = new ClientGroup("administration", "clientGroupDescription2", new ArrayList<Client>());

        Client client1 = new Client("test1", encoder.encode("Password"), "email1@com",
                "phone1", "Adress1", "name1", "lastname1", UserRole.CUSTOMER, clientGroup1, avatars.get(0));
        Client client2 = new Client("admin", encoder.encode("Password"), "email2@com",
                "phone2", "Adress2", "name2", "lastname2", UserRole.ADMIN, clientGroup2, avatars.get(1));


        entityManager.persist(clientGroup1);
        entityManager.persist(clientGroup2);
        entityManager.persist(client1);
        entityManager.persist(client2);

        CategoryItem categoryItem = new CategoryItem("Category1", "category1 description", new ArrayList<BookItem>());

        Publisher publisher = new Publisher("Publisher1", "Publisher1 adress",
                "Publisher1 Description", new ArrayList<BookItem>());

        Stock stock1 = new Stock("Stock1 adress", "Stock1 phone");

        List<BookItem> orderList = new ArrayList<>();

        entityManager.persist(categoryItem);
        entityManager.persist(publisher);

        StorageBooks storageBooks = new StorageBooks("storageAdress1", "storagePfone1");
        entityManager.persist(storageBooks);
//        storageBooksService.addStorageBooks(storageBooks);
        for (int i = 0; i < bookItemList.size(); i++) {
            /*create books*/


//            BookItem book = new BookItem("name" + i, "description" + i, "Author" + i, publisher,
//                    categoryItem, 100.0, storageBooks, (i < 14) ? bookCovers.get(i) : bookCovers.get(i - 14), 0, "ISBN" + i);

            BookItem book=bookItemList.get(i);
            book.setStorageBooks(storageBooks);

            if (i < 10) {
                storageBooks.getBookQuantityMap().put(book, 10);
            } else {
                storageBooks.getBookQuantityMap().put(book, 25);
            }

            entityManager.persist(book);
//            book = bookService.addBookItem(book);
//            bookDAO.save(book);

            /*create clients*/
            Client client = new Client("login" + i, encoder.encode("Password" + i), "email" + i + "@com",
                    "phone" + i, "Adress" + i, "name" + i, "lastname" + i, UserRole.CUSTOMER, clientGroup1,
                    (i < 11) ? avatars.get(i) : avatars.get(i - 11));


            entityManager.persist(client);
//            client = clientService.addClient(client);

            /*create orders*/
            if (i % 2 == 0) {
                orderList.add(book);
                orderList.add(book);
            } else {
                orderList.add(book);
            }
            Shipment shipment = new Shipment("shipment adress" + i, "processed", null);
            Order order = new Order(new ArrayList<>(orderList), client, shipment, (i % 2 == 0) ? OrderStatus.processed : OrderStatus.closed, new Date((long) (today.getTime() - (long) i * 8.64e+7)));
            shipment.setOrder(order);
            if (order.getStatus() == OrderStatus.closed) {
                shipment.setShipmentStatus("Closed");
            }
            entityManager.persist(order);
//            if(order.getStatus()==OrderStatus.unProcessed){
//                Task task=new Task("Unprocessed order id "+order.getId(), "open");
//                entityManager.persist(task);
//            }


            Shipment shipment2 = new Shipment("shipment adress" + i, "processed", null);
            Order order2 = new Order(new ArrayList<>(orderList), client, shipment2, (i % 2 == 0) ? OrderStatus.unProcessed : OrderStatus.closed, new Date((long) (today.getTime() - (long) i * 8.64e+7)));
            shipment2.setOrder(order2);
            if (order2.getStatus() == OrderStatus.closed) {
                shipment2.setShipmentStatus("Closed");
            }
            entityManager.persist(order2);
            if (order2.getStatus() == OrderStatus.unProcessed) {
                Task task2 = new Task("Task description" + order2.getId(), "open");
                entityManager.persist(task2);
            }
        }
        entityManager.getTransaction().commit();
        List<Order> orders = orderService.findAll();
        orderService.updateOrder(orders.get(1));
    }

    public List<BookItem> readFromExcel(File excelFile) {

        System.out.println(ConsoleColors.RED+excelFile.getAbsolutePath()+ConsoleColors.RESET);

        Set<CategoryItem> categoryItemSet = new HashSet<>();
        List<BookItem> bookList = new ArrayList<>();
        Set<Publisher> publisherSet = new HashSet<>();

        try (InputStream is = new FileInputStream(excelFile)) {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
//            HSSFSheet hssfSheet=hssfWorkbook.getSheet("bookforsite");
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            for (Row cells : hssfSheet) {
                if (cells.getRowNum() == 0) {
                    continue;
                }
                Publisher tempPublisher = new Publisher();
                tempPublisher.setPublisherName(cells.getCell(3).getStringCellValue());
                publisherSet.add(tempPublisher);
                for (Publisher publisher : publisherSet) {
                    if (publisher.getPublisherName().equals(cells.getCell(3).getStringCellValue())) {
                        tempPublisher = publisher;
                    }
                }

                CategoryItem tempCategory = new CategoryItem();
                tempCategory.setCategoryName(cells.getCell(4).getStringCellValue());
                categoryItemSet.add(tempCategory);
                for (CategoryItem categoryItem : categoryItemSet) {
                    if (categoryItem.getCategoryName().equalsIgnoreCase(cells.getCell(4).getStringCellValue())) {
                        tempCategory = categoryItem;
                    }
                }
                BookItem tempBook = new BookItem();
                tempBook.setBookName(cells.getCell(0).getStringCellValue());
                tempBook.setDescription(cells.getCell(1).getStringCellValue());
                tempBook.setAuthor(cells.getCell(2).getStringCellValue());
                tempBook.setPublisher(tempPublisher);
                tempPublisher.getBooks().add(tempBook);
//                tempPublisher.getBooks().add(tempBook);
                tempBook.setCategory(tempCategory);
                tempCategory.getBooks().add(tempBook);
                tempBook.setPrice(cells.getCell(5).getNumericCellValue());
                File tempCover=new ClassPathResource("/bookItems/"+cells.getCell(6).getStringCellValue()).getFile();
//                tempBook.setCover(new File("f:\\Drive\\Литература\\Java\\Картинки для книг\\" + cells.getCell(6).getStringCellValue()));
                tempBook.setCover(tempCover);
//                tempBook.setCover(new File("d:\\GoogleDrive\\Литература\\Java\\Картинки для книг\\" + cells.getCell(6).getStringCellValue()));
                tempBook.setISBN(cells.getCell(7).getStringCellValue());
                bookList.add(tempBook);

//                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + tempBook + ConsoleColors.RESET);
            }

//            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + hssfWorkbook.getNumberOfSheets() + ConsoleColors.RESET);
//            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + hssfSheet.getPhysicalNumberOfRows() + ConsoleColors.RESET);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }

}
