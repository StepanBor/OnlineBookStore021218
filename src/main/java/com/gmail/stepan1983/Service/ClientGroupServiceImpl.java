package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.DAO.ClientGroupDAO;
import com.gmail.stepan1983.model.ClientGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ClientGroupServiceImpl implements ClientGroupService {

    @Autowired
    ClientGroupDAO clientGroupDAO;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public ClientGroup addClientGroup(ClientGroup clientGroup) {

//        return clientGroupDAO.save(clientGroup);
        return entityManager.merge(clientGroup);
    }

    @Override
    @Transactional
    public void deleteClientGroup(ClientGroup clientGroup) {

        clientGroupDAO.delete(clientGroup);

    }

    @Override
    @Transactional(readOnly = true)
    public ClientGroup getById(Long clientGroupId) {
        return clientGroupDAO.getOne(clientGroupId);
    }

    @Override
    @Transactional
    public void updateClientGroup(ClientGroup clientGroup) {

        clientGroupDAO.save(clientGroup);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientGroup> findAll(Pageable pageable) {
        return clientGroupDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientGroup> findAll() {
        return clientGroupDAO.findAll();
    }

//    @Override
//    @Transactional(readOnly = true)
//    public ClientGroup findByClient(Client client) {
//        return clientGroupDAO.findByClient(client);
//    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return clientGroupDAO.count();
    }

    @Override
    @Transactional(readOnly = true)
    public ClientGroup findByGroupName(String clientGroupName) {
        return clientGroupDAO.findByGroupName(clientGroupName);
    }

}
