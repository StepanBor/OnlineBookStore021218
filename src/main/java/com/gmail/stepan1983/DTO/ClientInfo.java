package com.gmail.stepan1983.DTO;

import java.util.List;

public class ClientInfo {

    private  ClientDTO clientDTO;

    private List<OrderDTO> clientOrders;

    public ClientInfo(ClientDTO clientDTO, List<OrderDTO> clientOrders) {
        this.clientDTO = clientDTO;
        this.clientOrders = clientOrders;
    }

    public ClientInfo() {
    }

    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    public void setClientDTO(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }

    public List<OrderDTO> getClientOrders() {
        return clientOrders;
    }

    public void setClientOrders(List<OrderDTO> clientOrders) {
        this.clientOrders = clientOrders;
    }
}
