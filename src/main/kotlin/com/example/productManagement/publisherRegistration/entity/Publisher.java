package com.example.productManagement.publisherRegistration.entity;

import com.example.productManagement.utilityCode.MailDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="publisher")
public class Publisher implements MailDto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQUENCE", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name="key_Id")
    private String keyId;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false,unique = true, length=14)
    private String mobileNumber;

    @Column(nullable = false,length=25)
    private String role;

    @Column(nullable = false,length=40)
    private String wareHouseName;

    @Column(nullable = false,length = 50)
    private String  taxId;

    @Column(nullable = false,length = 50)
    private String address;

    @Column(nullable = false, length = 14)
    private String password;

    @Column(nullable = false, length = 14)
    private String confirmPassword;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    private boolean enabled;

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

}
