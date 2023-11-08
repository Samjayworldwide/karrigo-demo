package com.decagon.karrigobe.entities.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class DriverTaskEntity extends BaseEntity {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "driverTaskEntity")
    private List<OrderLocationEntity> orderLocationEntity = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "driverTaskEntity", cascade = {CascadeType.DETACH,CascadeType.MERGE
            ,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<OrderEntity> orderEntity = new ArrayList<>();

    public void  addOrder(OrderEntity order){
        orderEntity.add(order);
        order.setDriverTaskEntity(this);
    }

    public void  addOrderLocation(OrderLocationEntity orderLocation){
        orderLocationEntity.add(orderLocation);
        orderLocation    .setDriverTaskEntity(this);
    }
}
