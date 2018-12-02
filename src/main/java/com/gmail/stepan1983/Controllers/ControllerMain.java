package com.gmail.stepan1983.Controllers;

import com.gmail.stepan1983.DTO.ClientDTO;
import com.gmail.stepan1983.Service.BookService;
import com.gmail.stepan1983.Service.ClientGroupService;
import com.gmail.stepan1983.Service.ClientService;
import com.gmail.stepan1983.Service.OrderService;
import com.gmail.stepan1983.config.ConsoleColors;
import com.gmail.stepan1983.model.BookItem;
import com.gmail.stepan1983.model.Client;
import com.gmail.stepan1983.model.Order;
import com.gmail.stepan1983.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerMain {

    //    private static final int ITEMS_PER_PAGE = 6;
    private static boolean sortDirection;

    @Autowired
    ClientService clientService;

    @Autowired
    OrderService orderService;

    @Autowired
    ClientGroupService clientGroupService;

    @Autowired
    BookService bookService;


//    @CrossOrigin(origins = "*")
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/adminPage")
    public String adminPage() {
        return "index2";
    }
}
