package com.gmail.stepan1983.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.gmail.stepan1983.Service.ClientGroupService;
import com.gmail.stepan1983.Service.ClientService;
import com.gmail.stepan1983.config.ContextProvider;
import com.gmail.stepan1983.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDTO {

    ClientService clientService=ContextProvider.getBean(com.gmail.stepan1983.Service.ClientServiceImpl.class);
    ClientGroupService clientGroupService=ContextProvider.getBean(com.gmail.stepan1983.Service.ClientGroupServiceImpl.class);

    private long id;

    private List<MyEntry> orderList;

//    private Map<BookItemDTO,Integer> orderMap;


    private double orderPrice;

    private ClientDTO client;

    private ShipmentDTO shipment;

    private String status;

//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date orderDate;

//    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    public OrderDTO(long id, List<MyEntry> orderList, double orderPrice, ClientDTO client,
                    ShipmentDTO shipment, String status,
                    Date orderDate /*Map<BookItemDTO,Integer> orderMap*/) {
        this.id = id;
        this.orderList = orderList;
        this.orderPrice = orderPrice;
        this.client = client;
        this.shipment = shipment;
        this.status = status;
        this.orderDate = orderDate;
//        this.orderMap=orderMap;
    }

    public OrderDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public List<BookItemDTO> getOrderList() {
//        return orderList;
//    }
//
//    public void setOrderList(List<BookItemDTO> orderList) {
//        this.orderList = orderList;
//    }

    public Order toOrder(){

        List<BookItem> orderListTemp=new ArrayList<>();

        for (MyEntry entry : orderList) {
            for (int i = 0; i < entry.getValue(); i++) {
                orderListTemp.add(entry.getKey().toBookItem());
            }
        }

        Client client1;

        if(clientService.existsById(client.getId())){
            client1=clientService.getById(client.getId());
        }else if(clientService.existsByEmail(client.getEmail())){
            client1=clientService.getByEmail(client.getEmail());
        }else if(clientService.existsByPhone(client.getPhone())){
            client1=clientService.getByPhone(client.getPhone());
        } else {
            client1=new Client(this.client.getLogin(),"default",this.client.getEmail(),
                    this.client.getPhone(),this.client.getAdress(),this.client.getName(),
                    this.client.getLastname(),UserRole.CUSTOMER,clientGroupService.findByGroupName("customers"),null);
        }

        Order order=new Order(orderListTemp,client1,
                this.shipment.toShipment(),OrderStatus.valueOf(status),orderDate);

        order.getShipment().setOrder(order);

        order.setId(id);

        return order;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public ShipmentDTO getShipment() {
        return shipment;
    }

    public void setShipment(ShipmentDTO shipmentId) {
        this.shipment = shipmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

//    public Map<BookItemDTO, Integer> getOrderMap() {
//        return orderMap;
//    }
//
//    public void setOrderMap(Map<BookItemDTO, Integer> orderMap) {
//        this.orderMap = orderMap;
//    }


    public List<MyEntry> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<MyEntry> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", orderList=" + orderList +
                ", orderPrice=" + orderPrice +
                ", client=" + client +
                ", shipment=" + shipment +
                ", status='" + status + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}
