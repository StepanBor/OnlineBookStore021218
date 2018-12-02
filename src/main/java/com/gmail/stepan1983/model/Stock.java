package com.gmail.stepan1983.model;

import javax.persistence.*;

//@Entity
//@Table(name = "Stock1")
public class Stock {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="stockId")
    private long id;

    private String storageAddress;

    private String storagePhone;

    public Stock(String storageAddress, String storagePhone) {
        this.storageAddress = storageAddress;
        this.storagePhone = storagePhone;
    }

    public Stock() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public String getStoragePhone() {
        return storagePhone;
    }

    public void setStoragePhone(String storagePhone) {
        this.storagePhone = storagePhone;
    }
}
