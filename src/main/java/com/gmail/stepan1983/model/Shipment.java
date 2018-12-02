package com.gmail.stepan1983.model;

import com.gmail.stepan1983.DTO.ShipmentDTO;

import javax.persistence.*;

@Entity
@Table(name = "Shipment1")
public class Shipment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="shipmentId")
    private long id;

    private String shippingAddress;

    private String shipmentStatus;

    @OneToOne
    private Order order;

    public Shipment(String shippingAddress, String shipmentStatus, Order order) {
        this.shippingAddress = shippingAddress;
        this.shipmentStatus = shipmentStatus;
        this.order=order;
    }

    public Shipment() {
    }

    public ShipmentDTO toDTO(){
        return new ShipmentDTO(id,shippingAddress,shipmentStatus,order.getId());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
