package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.DAO.PublisherDAO;
import com.gmail.stepan1983.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    PublisherDAO publisherDAO;

    @Override
    @Transactional
    public Publisher addPublisher(Publisher publisher) {
        return publisherDAO.save(publisher);
    }

    @Override
    public Publisher getById(Long publisherId) {
        if(publisherDAO.existsById(publisherId)){
            return publisherDAO.getOne(publisherId);
        }
        return null;

    }

    @Override
    public Publisher getByName(String publisherName) {
        return publisherDAO.getByName(publisherName);
    }

    @Override
    @Transactional
    public void updatePublisher(Publisher publisher) {
        publisherDAO.save(publisher);

    }

    @Override
    public List<Publisher> findAll() {
        return publisherDAO.findAll();
    }

    @Override
    public long count() {
        return publisherDAO.count();
    }

    @Override
    public boolean existsByName(String name) {
        return publisherDAO.existsByName(name);
    }
}
