package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.model.ClientGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientGroupService {

    ClientGroup addClientGroup(ClientGroup clientGroup);

    public void deleteClientGroup(ClientGroup clientGroup);

    ClientGroup getById(Long bookId);

    void updateClientGroup(ClientGroup clientGroup);

    Page<ClientGroup> findAll(Pageable pageable);

    List<ClientGroup> findAll();

//    ClientGroup findByClient(Client client);

    ClientGroup findByGroupName(String clientGroupName);

    long count();

}
