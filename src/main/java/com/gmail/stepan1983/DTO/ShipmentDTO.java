package com.gmail.stepan1983.DTO;


import com.gmail.stepan1983.Service.ClientService;
import com.gmail.stepan1983.Service.OrderService;
import com.gmail.stepan1983.config.ContextProvider;
import com.gmail.stepan1983.model.Order;
import com.gmail.stepan1983.model.Shipment;

public class ShipmentDTO {

    OrderService orderService=ContextProvider.getBean(com.gmail.stepan1983.Service.OrderServiceImpl.class);

    private long id;

    private String shippingAddress;

    private String shipmentStatus;

    private Long order;

    public ShipmentDTO(long id, String shippingAddress, String shipmentStatus, Long order) {
        this.id = id;
        this.shippingAddress = shippingAddress;
        this.shipmentStatus = shipmentStatus;
        this.order = order;
    }

    public Shipment toShipment(){
        Order order1;
        if(orderService.existById(order)){
            order1=orderService.getById(order);
        }else{
            order1=new Order();
        }
        Shipment tempShipment=new Shipment(shippingAddress,shipmentStatus,order1);
        tempShipment.setId(id);
        order1.setShipment(tempShipment);
        return tempShipment;
    }

    public ShipmentDTO() {
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

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "ShipmentDTO{" +
                "id=" + id +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", shipmentStatus='" + shipmentStatus + '\'' +
                ", order=" + order +
                '}'+"\r\n";
    }
}
