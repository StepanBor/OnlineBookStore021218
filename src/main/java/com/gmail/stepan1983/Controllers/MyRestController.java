package com.gmail.stepan1983.Controllers;

import com.gmail.stepan1983.DTO.*;
import com.gmail.stepan1983.Service.*;
import com.gmail.stepan1983.config.ConsoleColors;
import com.gmail.stepan1983.config.FillDataBase;
import com.gmail.stepan1983.config.RateRetriever;
import com.gmail.stepan1983.config.jwt.JwtProvider;
import com.gmail.stepan1983.config.jwt.JwtResponse;
import com.gmail.stepan1983.model.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
public class MyRestController {

    private static boolean sortDirection;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    ClientService clientService;

    @Autowired
    OrderService orderService;

    @Autowired
    ClientGroupService clientGroupService;

    @Autowired
    BookService bookService;

    @Autowired
    RateRetriever rateRetriever;

    @Autowired
    TaskServiceImpl taskService;

    @Autowired
    PublisherService publisherService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    StorageBooksService storageBooksService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    FillDataBase fillDataBase;

    @CrossOrigin(origins = "*")
    @RequestMapping("/userPage")
    public List<ClientDTO> userPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                    @RequestParam(required = false, defaultValue = "6") Integer itemsPerPage,
                                    @RequestParam(required = false) Long clientId,
                                    @RequestParam(required = false, defaultValue = "0") Long pageOrders,
                                    @RequestParam(required = false, defaultValue = "id") String sortBy,
                                    @RequestParam(required = false, defaultValue = "false") Boolean changeSortDirect) {


        if (changeSortDirect) {
            sortDirection = !sortDirection;
        }

        long clientNum = clientService.count();

        Long clientPageNum = clientNum % itemsPerPage == 0
                ? clientNum / itemsPerPage : clientNum / itemsPerPage + 1;

        List<Client> clients = clientService.findAll(page - 1, itemsPerPage, sortBy, sortDirection);


        List<ClientDTO> clientDTOS = new ArrayList<>();

        for (Client client : clients) {
            clientDTOS.add(client.toDTO());
        }

        return clientDTOS;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/getUsers")
    public List<ClientDTO> getUsers() {

        List<Client> clients = clientService.findAll();

        List<ClientDTO> clientDTOS = new ArrayList<>();

        for (Client client : clients) {
            clientDTOS.add(client.toDTO());
        }

        return clientDTOS;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/usersCount")
    public Long getTotalUsersCount() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");

//        return new ResponseEntity<>(clientService.count(), headers, HttpStatus.OK);
        return clientService.count();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/createNewUser", method = RequestMethod.POST)
    public ResponseEntity createNewUser(@RequestParam(required = false) MultipartFile photo,
                                        @RequestParam String login,
                                        @RequestParam String email,
                                        @RequestParam String phone,
                                        @RequestParam(required = false) String address,
                                        @RequestParam(required = false, defaultValue = "CUSTOMER") String userRole,
                                        @RequestParam(required = false) String name,
                                        @RequestParam(required = false) String lastname,
                                        @RequestParam(required = false) String password,
                                        @RequestParam(required = false, defaultValue = "false") Boolean updateUser,
                                        @RequestParam(required = false) Long id) {

        File avatar = new File("photo");
        if (photo != null) {
            try (OutputStream os = new FileOutputStream(avatar)) {
                os.write(photo.getBytes());
                System.out.println(ConsoleColors.BLUE_UNDERLINED + avatar.length() + ConsoleColors.RESET);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<String> message = new ArrayList<>();
        if (updateUser) {
            Client client = clientService.getById(id);
            if (login != null) {
                client.setLogin(login);
            }
            if (email != null) {
                client.setEmail(email);
            }
            if (phone != null) {
                client.setPhone(phone);
            }
            if (address != null) {
                client.setAdress(address);
            }
            if (name != null) {
                client.setName(name);
            }
            if (lastname != null) {
                client.setLastname(lastname);
            }
            if (password != null) {
                client.setPassword(encoder.encode(password));
            }
            if (photo != null) {
                client.setAvatar(avatar);
            }
            clientService.updateClient(client);
            message.add("client profile id " + client.getId() + " updated");
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (clientService.existsByLogin(login)) {
            message.add("client with login \'" + login + "\' already exists");
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (clientService.existsByEmail(email)) {
            message.add("client with email \'" + email + "\' already exists");
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (clientService.existsByPhone(phone)) {
            message.add("client with phone \'" + phone + "\' already exists");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        ClientGroup clientGroup = clientGroupService.findByGroupName("customers");
        Client newClient = new Client(login, encoder.encode(password), email, phone, address, name, lastname, UserRole.valueOf(userRole.toUpperCase()), clientGroup, avatar);

        Client client = clientService.addClient(newClient);
        message.add("New user id is " + client.getId());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginForm loginRequest) {

        System.out.println(ConsoleColors.RED + loginRequest + ConsoleColors.RESET);
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getLogin(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("wrong credentials", HttpStatus.UNAUTHORIZED);
//            e.printStackTrace();
        }
        System.out.println(ConsoleColors.RED + authentication + ConsoleColors.RESET);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/signinAdmin")
    public ResponseEntity<?> authenticateAdmin(@RequestBody LoginForm loginRequest) {

        System.out.println(ConsoleColors.RED + loginRequest + ConsoleColors.RESET);
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getLogin(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("wrong credentials", HttpStatus.UNAUTHORIZED);
//            e.printStackTrace();
        }
        for (Object o : authentication.getAuthorities()) {
            if (o.toString().equalsIgnoreCase("ROLE_ADMIN") ||
                    o.toString().equalsIgnoreCase("ROLE_MANAGER")) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtProvider.generateJwtToken(authentication);
                return ResponseEntity.ok(new JwtResponse(jwt));
            }
        }

        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + authentication + ConsoleColors.RESET);
        System.out.println();
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + authentication.getAuthorities() + ConsoleColors.RESET);
        System.out.println();
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + (authentication.getAuthorities().contains("ROLE_ADMIN") ||
                authentication.getAuthorities().contains("ROLE_MANAGER")) + ConsoleColors.RESET);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = jwtProvider.generateJwtToken(authentication);
//        return ResponseEntity.ok(new JwtResponse(jwt));
        return new ResponseEntity<>("wrong credentials", HttpStatus.UNAUTHORIZED);
    }


    @CrossOrigin(origins = "*")
    @RequestMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestParam Long userId) {
        Client client = clientService.getById(userId);

        clientService.deleteClient(client);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/userInfo")
    public ResponseEntity userInfo(@RequestParam String login,
                                   @RequestHeader("Authorization") String token) {

        Client client = clientService.getByLogin(login);
        List<Order> userOrders = orderService.findByClient(client);
        List<OrderDTO> userOrdersDTO = new ArrayList<>();
        for (Order userOrder : userOrders) {
            userOrdersDTO.add(userOrder.toDTO());
        }
        String username = jwtProvider.getUserNameFromJwtToken(token);


        if (username.equalsIgnoreCase(client.getLogin())) {
            ClientInfo clientInfo = new ClientInfo(client.toDTO(), userOrdersDTO);
            return new ResponseEntity<>(clientInfo, HttpStatus.OK);
        }


        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }


    @CrossOrigin(origins = "*")
    @RequestMapping("/orders")
    public ResponseEntity getUserOrders(@RequestParam(required = false) Long userId,
                                        @RequestParam(required = false, defaultValue = "1") Long page,
                                        @RequestParam(required = false, defaultValue = "6") Integer itemsPerPage,
                                        @RequestParam(required = false, defaultValue = "id") String sortBy,
                                        @RequestParam(required = false, defaultValue = "false") Boolean changeSortDirect,
                                        @RequestParam(required = false, defaultValue = "false") Boolean allOrders
                                       /* @RequestHeader(value = "Authorization", required = false) String token*/) {

//        if(token!=null){
//            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+token+ConsoleColors.RESET);
//        }

//        String username = jwtProvider.getUserNameFromJwtToken(token);
//
//        Client client1 = clientService.getByLogin(username);
//        if (client1.getRole().toString().equalsIgnoreCase("ROLE_CUSTOMER") & (userId == null || client1.getId() != userId)) {
//
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
        if (changeSortDirect) {
            sortDirection = !sortDirection;
        }

        List<Order> orders;
        if (allOrders) {
            orders = orderService.findAll();
        } else if (userId != null) {
            Client client = clientService.getById(userId);
            orders = orderService.findByClient(client, PageRequest.of(page.intValue() - 1, itemsPerPage,
                    Sort.Direction.ASC, "status", "orderPrice"));
        } else {
            orders = orderService.findAll(page.intValue() - 1, itemsPerPage, sortBy, sortDirection);
        }


        List<OrderDTO> ordersDTO = new ArrayList<>();

        for (Order order : orders) {
            ordersDTO.add(order.toDTO());
        }

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity(ordersDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/orderCount")
    public Long getTotalOrderCount() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "*");
//        return new ResponseEntity<>(orderService.count(), headers, HttpStatus.OK);
        return orderService.count();
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    public ResponseEntity saveOrder(@RequestBody OrderDTO orderDTO) {


        System.out.println(ConsoleColors.PURPLE + orderDTO + ConsoleColors.RESET);

        Order order = orderDTO.toOrder();
        System.out.println(ConsoleColors.BLUE_BRIGHT + order + ConsoleColors.RESET);

        order = orderService.updateOrder(order);
//        if(order.getStatus()==OrderStatus.unProcessed){
//            Task task = new Task("Unprocessed order id "+order.getId(), "open");
//            taskService.addTask(task);
//        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    public ResponseEntity deleteOrder(@RequestBody OrderDTO orderDTO) {

        System.out.println(ConsoleColors.YELLOW + orderDTO + ConsoleColors.RESET);

        Order order = orderDTO.toOrder();

        System.out.println(ConsoleColors.RED + order + ConsoleColors.RESET);

        orderService.deleteOrder(order);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/createNewOrder")
    public ResponseEntity createNewOrder() {

        Shipment shipment = new Shipment("", "unProcessed", null);
        ClientGroup clientGroup = clientGroupService.findByGroupName("customers");
        Client client = new Client("default", "", "default", "default",
                "default", "default", "default",
                UserRole.CUSTOMER, clientGroup, new File("default"));
        Order order = new Order(new ArrayList<BookItem>(), client, shipment, OrderStatus.unProcessed, new Date());
        shipment.setOrder(order);
//        clientService.addClient(client);
        order = orderService.addOrder(order);
        System.out.println(ConsoleColors.BLUE_UNDERLINED + order + ConsoleColors.RESET);
        return new ResponseEntity<>(order.getId(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
    public ResponseEntity submitOrder(@RequestBody OrderDTO orderDTO) {

        Order order = orderDTO.toOrder();

//        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+order+ConsoleColors.RESET);

        order = orderService.addOrder(order);

        List<String> reply = new ArrayList<>();
        reply.add("your order id is " + order.getId());
        return new ResponseEntity<>(reply, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/countOrdersByParam", method = RequestMethod.GET)
    public ResponseEntity countOrdersByParam(@RequestParam String paramName,
                                             @RequestParam String paramValue) {

        System.out.println(ConsoleColors.BLUE_UNDERLINED + orderService.countByParam(paramName, paramValue) + ConsoleColors.RESET);
        return new ResponseEntity<>(orderService.countByParam(paramName, paramValue), HttpStatus.OK);
    }


    @CrossOrigin(origins = "*")
    @RequestMapping("/bookItems")
    public List<BookItemDTO> getBookItems(@RequestParam(required = false) Long bookId,
                                          @RequestParam(required = false, defaultValue = "1") Long page,
                                          @RequestParam(required = false, defaultValue = "6") Integer itemsPerPage,
                                          @RequestParam(required = false, defaultValue = "id") String sortBy,
                                          @RequestParam(required = false, defaultValue = "false") Boolean changeSortDirect,
                                          @RequestParam(required = false) String sortDirect) {

        boolean localSortDirect = sortDirection;

        if (changeSortDirect) {
            sortDirection = !sortDirection;
            localSortDirect = sortDirection;
        }

        if (sortDirect != null) {
            localSortDirect = sortDirect.equalsIgnoreCase("ASC");
        }

        List<BookItem> bookItems;

        if (page == null) {
            bookItems = bookService.findAll();
        } else {
            bookItems = bookService.findAll(page.intValue() - 1, itemsPerPage, sortBy, localSortDirect);
        }

        List<BookItemDTO> bookItemsDTO = new ArrayList<>();

        for (BookItem bookItem : bookItems) {
            bookItemsDTO.add(bookItem.toDTO());
        }

        return bookItemsDTO;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/bookItemsByParam")
    public BookItemsWithParamList getBookItemsByParam(@RequestParam(required = false) String[] bookName,
                                                      @RequestParam(required = false) String[] author,
                                                      @RequestParam(required = false) String[] publisher,
                                                      @RequestParam(required = false) String[] category,
                                                      @RequestParam(required = false) String id,
                                                      @RequestParam(required = false, defaultValue = "1") Integer page,
                                                      @RequestParam(required = false, defaultValue = "12") Integer itemsPerPage,
                                                      @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                      @RequestParam(required = false, defaultValue = "false") Boolean changeSortDirect) {

        if (changeSortDirect) {
            sortDirection = !sortDirection;
        }

        int sortDirection1 = sortDirection ? -1 : 1;
        Set<BookItem> bookItemsSet = new HashSet<>();


        System.out.println(ConsoleColors.RED + changeSortDirect + ConsoleColors.RESET);

        if (bookName != null) {
            for (String s : bookName) {
                bookItemsSet.add(bookService.getByBookName(s));
            }
        }
        if (author != null) {
            for (String s : author) {
                bookItemsSet.addAll(bookService.getByAuthor(s));
            }
        }
        if (publisher != null) {
            for (String s : publisher) {
                bookItemsSet.addAll(bookService.getByPublisher(s));
            }
        }
        if (id != null) {
            bookItemsSet.add(bookService.getById(Long.valueOf(id)));
        }
        if (category != null) {
            for (String s : category) {
                bookItemsSet.addAll(bookService.getByCategory(s));
            }
        }
        for (BookItem bookItem : bookItemsSet) {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + bookItem + ConsoleColors.RESET);
        }
        List<BookItem> bookItems = new ArrayList<>(bookItemsSet);

        bookItems.sort((BookItem b1, BookItem b2) -> {
            if (sortBy.equalsIgnoreCase("bookName")) {
                return b1.getBookName().compareToIgnoreCase(b2.getBookName()) * sortDirection1;
            }
            if (sortBy.equalsIgnoreCase("author")) {
                return b1.getAuthor().compareToIgnoreCase(b2.getAuthor()) * sortDirection1;
            }
            if (sortBy.equalsIgnoreCase("publisher")) {
                return b1.getPublisher().getPublisherName().compareToIgnoreCase(b2.getPublisher().getPublisherName()) * sortDirection1;
            }
            if (sortBy.equalsIgnoreCase("category")) {
                return b1.getCategory().getCategoryName().compareToIgnoreCase(b2.getCategory().getCategoryName()) * sortDirection1;
            } else {
                return (b1.getRating() - b2.getRating()) * sortDirection1;
            }
        });


        List<BookItemDTO> bookItemsDTO = new ArrayList<>();


        for (BookItem bookItem : bookItems) {
            System.out.println(ConsoleColors.RED + bookItem + ConsoleColors.RESET);
        }

        for (int i = (page - 1) * itemsPerPage;
             i < ((((page - 1) * itemsPerPage + itemsPerPage) > bookItems.size())
                     ? bookItems.size() : ((page - 1) * itemsPerPage + itemsPerPage));
             i++) {

            bookItemsDTO.add(bookItems.get(i).toDTO());
        }

        for (BookItemDTO bookItem : bookItemsDTO) {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + bookItem + ConsoleColors.RESET);
        }

        return new BookItemsWithParamList(bookItemsDTO, bookItems.size());
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/bookCount")
    public Long getTotalBookCount() {
        return bookService.count();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/saveBookItem", method = RequestMethod.POST)
    public ResponseEntity saveBookItem(@RequestBody BookItemDTO bookItemDTO) {

        BookItem bookItem = bookItemDTO.toBookItem();
        bookService.updateBookItem(bookItem);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/deleteBookItem", method = RequestMethod.POST)
    public ResponseEntity deleteBookItem(@RequestBody BookItemDTO bookToDelete) {
        BookItem bookItem;
        try {
            bookItem = bookService.getById(bookToDelete.getId());
        } catch (javax.persistence.EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        bookService.deleteBookItem(bookItem);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/createNewBookItem", method = RequestMethod.POST)
    public ResponseEntity createNewBookItem(@RequestParam(required = false) MultipartFile cover,
                                            @RequestParam String bookName,
                                            @RequestParam String description,
                                            @RequestParam String author,
                                            @RequestParam String publisherName,
//                                            @RequestParam String publisherName,
//                                            @RequestParam String publisherDescription,
//                                            @RequestParam String publisherAdress,
                                            @RequestParam(required = false) String categoryName,
                                            @RequestParam(required = false) Double price,
                                            @RequestParam(required = false) String ISBN,
                                            @RequestParam(required = false, defaultValue = "false") Boolean updateBook,
                                            @RequestParam(required = false) Long id) {

        File bookCover = new File("cover");
        if (cover != null) {
            bookCover = new File(cover.getName());
            System.out.println(ConsoleColors.RED + cover.getName() + ConsoleColors.RESET);
            try (OutputStream os = new FileOutputStream(bookCover)) {
                os.write(cover.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Publisher publisher = publisherService.getByName(publisherName);
        CategoryItem categoryItem = categoryService.getByName(categoryName);

        if (updateBook) {
            BookItem bookItem = bookService.getById(id);

            bookItem.setISBN(ISBN);
            bookItem.setBookName(bookName);
            bookItem.setDescription(description);
            bookItem.setPrice(price);
            bookItem.setAuthor(author);

            if (bookItem.getPublisher().getId() != publisher.getId()) {
                Publisher tempPublisher = bookItem.getPublisher();
                tempPublisher.getBooks().remove(bookItem);
                bookItem.setPublisher(publisher);
                publisherService.updatePublisher(publisher);
            }

            if (bookItem.getCategory().getId() != categoryItem.getId()) {
                CategoryItem tempCategory = bookItem.getCategory();
                tempCategory.getBooks().remove(bookItem);
                bookItem.setCategory(categoryItem);
                categoryService.updateCategory(tempCategory);
            }

            bookItem.setPublisher(publisherService.getByName(publisherName));
            bookItem.setCategory(categoryItem);

            if (cover != null) {
                bookItem.setCover(bookCover);
            }

            bookService.updateBookItem(bookItem);
            return new ResponseEntity(HttpStatus.OK);
        }


        BookItem newBookItem = new BookItem(bookName, description, author, publisher,
                categoryItem, price, new StorageBooks(), bookCover, 0, ISBN);

        bookService.addBookItem(newBookItem);
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/createNewPublisher", method = RequestMethod.POST)
    public ResponseEntity createNewPublisher(@RequestParam String publisherName,
                                             @RequestParam String publisherDescription,
                                             @RequestParam String publisherAddress) {


        if (publisherService.existsByName(publisherName)) {
            Publisher publisher = publisherService.getByName(publisherName);
            publisher.setDescription(publisherDescription);
            publisher.setPublisherAdress(publisherAddress);
            publisherService.updatePublisher(publisher);
        } else {
            Publisher publisher = new Publisher(publisherName, publisherAddress, publisherDescription, new ArrayList<BookItem>());
            publisherService.addPublisher(publisher);
        }


        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/createNewCategory", method = RequestMethod.POST)
    public ResponseEntity createNewCategoryItem(@RequestParam String categoryName,
                                                @RequestParam String categoryDescription) {


        if (categoryService.existsByName(categoryName)) {
            CategoryItem categoryItem = categoryService.getByName(categoryName);
            categoryItem.setDescription(categoryDescription);
            categoryService.updateCategory(categoryItem);
        } else {
            CategoryItem categoryItem = new CategoryItem(categoryName, categoryDescription, new ArrayList<BookItem>());
            categoryService.addCategoryItem(categoryItem);
        }


        return new ResponseEntity(HttpStatus.OK);
    }


    @CrossOrigin(origins = "*")
    @RequestMapping("/storageBook")
    public StorageBookDTO getStorageBook() {
        List<StorageBooks> storageBooksList = storageBooksService.findAll();
        StorageBookDTO storageBookDTO = storageBooksList.get(0).toStorageDTO();
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + storageBookDTO + ConsoleColors.RESET);
        return storageBookDTO;
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/rates", method = RequestMethod.GET)
    public Rate getRates() {

        System.out.println(ConsoleColors.BLUE_UNDERLINED + rateRetriever.getRate() + ConsoleColors.RESET);
        return rateRetriever.getRate();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public List<Task> updateTasks(@RequestBody TaskDTO taskDTO) {

        Task task = taskDTO.toTask();
//        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+task+ConsoleColors.RESET);
        if (!task.getStatus().equals("closed")) {
            taskService.addTask(task);
        } else if (task.getStatus().equals("closed")) {
            taskService.deleteTask(task);
        }

        return taskService.findByStatus("open");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getTasks")
    public List<Task> getTasks() {

        return taskService.findByStatus("open");
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getBookParameters")
    public List<List<String>> getBookParameters() {

        List<List<String>> result = new ArrayList<>();

        List<String> categories = new ArrayList<>();
        List<String> authorsList = new ArrayList<>();
        Set<String> authorsSet = new HashSet<>();
        List<String> publishers = new ArrayList<>();

        List<BookItem> bookItems = bookService.findAll();
        List<CategoryItem> categoryItems = categoryService.findAll();
        List<Publisher> publishers1 = publisherService.findAll();

        for (BookItem bookItem : bookItems) {
            if (!authorsSet.contains(bookItem.getAuthor())) {
                authorsSet.add(bookItem.getAuthor());
            }
        }
        authorsList.addAll(authorsSet);

        for (CategoryItem categoryItem : categoryItems) {
            categories.add(categoryItem.getCategoryName());
        }

        for (Publisher publisher : publishers1) {
            publishers.add(publisher.getPublisherName());
        }

        result.add(categories);
        result.add(authorsList);
        result.add(publishers);

        return result;

    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/addBooks", method = RequestMethod.POST)
    public ResponseEntity addBooks(@RequestParam() MultipartFile table,
                                   @RequestParam() MultipartFile covers) {

        File coversZip = new File(covers.getOriginalFilename());
        System.out.println(ConsoleColors.RED + coversZip.getName() + ConsoleColors.RESET);
        File bookItemsExcell = new File(table.getOriginalFilename());
        try (OutputStream os = new FileOutputStream(coversZip);
             OutputStream os2 = new FileOutputStream(bookItemsExcell)) {
            os.write(covers.getBytes());
            os2.write(table.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<File> coversList = new ArrayList<>();
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(coversZip))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = new File(zipEntry.getName());
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                coversList.add(newFile);
                zipEntry = zis.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<BookItem> bookItemList = readFromExcel(bookItemsExcell, coversList);

        StorageBooks storageBooks = storageBooksService.findAll().get(0);


        bookService.addBookList(bookItemList);

        return new ResponseEntity(HttpStatus.OK);
    }

    public List<BookItem> readFromExcel(File excelFile, List<File> covers) {

        Set<CategoryItem> categoryItemSet = new HashSet<>();
        List<BookItem> bookList = new ArrayList<>();
        Set<Publisher> publisherSet = new HashSet<>();

        try (InputStream is = new FileInputStream(excelFile)) {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            for (Row cells : hssfSheet) {
                BookItem tempBook = new BookItem();
                tempBook.setBookName(cells.getCell(0).getStringCellValue());

                if (cells.getRowNum() == 0) {
                    continue;
                }

                if (tempBook.getBookName().equals("")) {
                    continue;
                }

                Publisher tempPublisher = new Publisher();
                tempPublisher.setPublisherName(cells.getCell(3).getStringCellValue());
                if (!publisherSet.add(tempPublisher)) {
                    for (Publisher publisher : publisherSet) {
                        if (publisher.equals(tempPublisher)) {
                            tempPublisher = publisher;
                        }
                    }
                }

                CategoryItem tempCategory = new CategoryItem();
                tempCategory.setCategoryName(cells.getCell(4).getStringCellValue());
                if (!categoryItemSet.add(tempCategory)) {
                    for (CategoryItem categoryItem : categoryItemSet) {
                        if (categoryItem.equals(tempCategory)) {
                            tempCategory = categoryItem;
                        }
                    }
                }


                tempBook.setDescription(cells.getCell(1).getStringCellValue());
                tempBook.setAuthor(cells.getCell(2).getStringCellValue());
                tempBook.setPublisher(tempPublisher);
                tempBook.setCategory(tempCategory);
                tempBook.setPrice(cells.getCell(5).getNumericCellValue());
                String coverFileName = cells.getCell(6).getStringCellValue();

                for (File cover : covers) {
                    System.out.println(ConsoleColors.RED + cover.getName() + ConsoleColors.RESET);
                    if (cover.getName().equalsIgnoreCase(coverFileName)) {
                        tempBook.setCover(cover);
                    }
                }

                tempBook.setISBN(cells.getCell(7).getStringCellValue());
                tempBook.setRating(0);
                bookList.add(tempBook);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + categoryItemSet + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + publisherSet + ConsoleColors.RESET);
        return bookList;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/photo/{photoId}")
    public ResponseEntity<byte[]> getPhotoBytes(@PathVariable("photoId") Long id) {

        Client client;
        try {
            client = clientService.getById(id);
        } catch (javax.persistence.EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        File file = client.getAvatar();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

//        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        return new ResponseEntity<>(getBytes(file), HttpStatus.OK);

    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/cover/{coverId}")
    public ResponseEntity<byte[]> getCoverBytes(@PathVariable("coverId") Long id) {

        BookItem bookItem;
        try {
            bookItem = bookService.getById(id);
        } catch (javax.persistence.EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        File file = bookItem.getCover();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(getBytes(file), HttpStatus.OK);

    }

    public byte[] getBytes(File file) {
        byte[] bytes = null;
        byte[] buffer = new byte[(int) file.length()];
        try (InputStream in = new FileInputStream(file); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int read = 0;
            while ((read = in.read(buffer)) > 0) {
                baos.write(buffer, 0, read);
            }
            bytes = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }



}
