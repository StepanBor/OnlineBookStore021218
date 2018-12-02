package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.DTO.ClientDTO;
import com.gmail.stepan1983.model.Client;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {

    Client addClient(Client client);

    Client getByLogin(String login);

    Client getByEmail(String email);

    Client getByPhone(String phone);

    Client getById(Long id);

    boolean existsByLogin(String login);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    void updateClient(Client client);

    void deleteClient(Client client);

    List<Client> findAll(Pageable pageable);

    List<Client> findAll();

    List<Client> findAll(Integer page, Integer itemsPerPage, String sortBy, boolean sortDirection);

    long count();


}
