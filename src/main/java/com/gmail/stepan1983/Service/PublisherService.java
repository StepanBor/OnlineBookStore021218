package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.model.BookItem;
import com.gmail.stepan1983.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublisherService {

    Publisher addPublisher(Publisher publisher);

    Publisher getById(Long publisherId);

    Publisher getByName(String publisherName);

    void updatePublisher(Publisher publisher);

    List<Publisher> findAll();

    long count();

    boolean existsByName(String name);

}
