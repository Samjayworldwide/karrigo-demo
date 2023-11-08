package com.decagon.karrigobe.entities.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ItemDescriptionEntity extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String itemName;

    @Column(nullable = false, length = 1000)
    private String itemDescription;

    @Column(nullable = false)
    private Double itemWeight;

    @Column(nullable = false)
    private Double declaredPrice;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE
            ,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "oder_id")
    private OrderEntity orderEntity;


}
