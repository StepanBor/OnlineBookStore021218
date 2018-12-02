package com.gmail.stepan1983.Service;

import com.gmail.stepan1983.DAO.ShipmentDAO;
import com.gmail.stepan1983.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    @Autowired
    ShipmentDAO shipmentDAO;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Shipment addShipment(Shipment shipment) {

        return entityManager.merge(shipment);
    }

    @Override
    @Transactional(readOnly = true)
    public Shipment getById(Long shipmentId) {
        Optional<Shipment> optionalShipment=shipmentDAO.findById(shipmentId);
        return optionalShipment.get();
    }

    @Override
    @Transactional
    public void updateShipment(Shipment shipment) {
        shipmentDAO.save(shipment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Shipment> findAll(Pageable pageable) {

        return shipmentDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shipment> findAll() {
        return shipmentDAO.findAll();
    }

//    @Override
//    public Page<Shipment> findByOrder(Long orderId) {
//        return null;
//    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return shipmentDAO.count();
    }
}
