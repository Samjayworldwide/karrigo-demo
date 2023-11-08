package com.decagon.karrigobe.entities.model;

import com.decagon.karrigobe.entities.enums.Gender;
import com.decagon.karrigobe.entities.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users_table")
public class UserEntity extends BaseEntity {

    @Column(nullable = false,length = 25)
    private String firstName;

    @Column(length = 25)
    private String lastName;

    @Column(nullable = false, length = 25)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Transient
    private String confirmPassword;

    @Column(nullable = false,length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String pictureUrl;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    private Boolean isVerified;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<OrderEntity> orderEntities = new ArrayList<>();

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private KYDEntity kydEntity;

    public void addOrder(OrderEntity order){
        orderEntities.add(order);
        order.setUserEntity(this);
    }

}
