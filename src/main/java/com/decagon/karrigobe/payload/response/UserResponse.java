package com.decagon.karrigobe.payload.response;

import com.decagon.karrigobe.entities.enums.Gender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private Gender gender;
    private String pictureUrl;
}