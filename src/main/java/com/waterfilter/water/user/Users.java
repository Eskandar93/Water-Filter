package com.waterfilter.water.user;


import com.waterfilter.water.common.BaseAuditingEntity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_category", discriminatorType = DiscriminatorType.STRING)

public abstract class Users extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String username;
    // private String email;
    private String password;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    private UserType userType;
    
}
