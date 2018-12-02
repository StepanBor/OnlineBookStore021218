package com.gmail.stepan1983.DAO;

import com.gmail.stepan1983.model.ClientGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientGroupDAO extends JpaRepository<ClientGroup,Long> {

//    @Query("SELECT c FROM ClientGroup c WHERE c.client= :client")
//    ClientGroup findByClient(@Param("client") Client client);

    @Query("SELECT c FROM ClientGroup c WHERE c.groupName= :name")
    ClientGroup findByGroupName(@Param("name") String clientGroupName);

}
