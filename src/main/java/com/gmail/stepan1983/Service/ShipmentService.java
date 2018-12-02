package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.model.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ShipmentService {

    Shipment addShipment(Shipment shipment);

    Shipment getById(Long shipmentId);

    void updateShipment(Shipment shipment);

    Page<Shipment> findAll(Pageable pageable);

    List<Shipment> findAll();

//    Page<Shipment> findByOrder(Long orderId);

    long count();
}
