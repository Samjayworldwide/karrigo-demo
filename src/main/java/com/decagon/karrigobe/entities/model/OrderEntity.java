package com.decagon.karrigobe.entities.model;

import com.decagon.karrigobe.entities.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_table")
public class OrderEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreationTimestamp
    private LocalDateTime time;

    @OneToOne(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    private OrderLocationEntity orderLocationEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.MERGE
            ,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    private List<TrackingLocationEntity> trackingLocationEntities = new ArrayList<>();

    @OneToOne(mappedBy = "orderEntity")        //No cascade to avoid deleting stored amount from transaction entity.
    private TransactionEntity transactionEntity;

    @OneToOne(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    private ItemDescriptionEntity itemDescriptionEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DriverTaskEntity driverTaskEntity;


    public void addTrackingLocation(TrackingLocationEntity location){
        trackingLocationEntities.add(location);
        location.setOrderEntity(this);
    }
}
