package com.gmail.stepan1983.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GroupClient1")
public class ClientGroup {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="clientGroupId")
    private long id;

    private String groupName;

    private String groupDescription;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Client> clients;

    public ClientGroup(String groupName, String groupDescription, List<Client> clients) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.clients=clients;
    }

    public ClientGroup() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
