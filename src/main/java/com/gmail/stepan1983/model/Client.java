package com.gmail.stepan1983.model;

import com.gmail.stepan1983.DTO.ClientDTO;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "Client1")
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="clientId")
    private long id;

    private String login;

    private String password;

    private String email;

    private String phone;

    private String adress;

    private String name;

    private String lastname;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne(cascade = CascadeType.ALL)
    private ClientGroup clientGroup;

    private File avatar;

    public Client(String login, String password, String email,
                  String phone, String adress, String name,
                  String lastname, UserRole role, ClientGroup clientGroup, File avatar) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        this.clientGroup = clientGroup;
        this.avatar=avatar;
    }

    public Client() {
    }


    public ClientDTO toDTO(){
        return new ClientDTO(this.id,this.login, this.email,
                this.phone, this.adress, this.name, this.lastname,
               this.role, this.clientGroup.getGroupName());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ClientGroup getClientGroup() {
        return clientGroup;
    }

    public void setClientGroup(ClientGroup clientGroup) {
        this.clientGroup = clientGroup;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public File getAvatar() {
        return avatar;
    }

    public void setAvatar(File avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", adress='" + adress + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role=" + role +
                ", clientGroup=" + clientGroup +
                '}';
    }
}
