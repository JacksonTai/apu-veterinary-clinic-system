/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jackson Tai
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
        @NamedQuery(name = "Customer.findByFullName",
                query = "SELECT c FROM Customer c WHERE LOWER(c.fullName) = LOWER(:fullName)"),
        @NamedQuery(name = "Customer.findByEmail",
                query = "SELECT c FROM Customer c WHERE LOWER(c.email) = LOWER(:email)"),
        @NamedQuery(name = "Customer.findByPhoneNumber",
                query = "SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber"),
        @NamedQuery(name = "Customer.findByIdOrFullNameOrEmailOrPhoneNumber",
                query = "SELECT c FROM Customer c WHERE c.customerId = :input OR LOWER(c.fullName) = LOWER(:input) " +
                        "OR  LOWER(c.email) = LOWER(:input) OR c.phoneNumber = :input"),
        @NamedQuery(name = "Customer.findByPetId",
                query = "SELECT c FROM Customer c JOIN c.pets p WHERE p.petId = :petId"),
})
@Data
@NoArgsConstructor
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CUSTOMER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String customerId;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;

    @Column(name = "ADDRESS")
    private String address;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Pet> pets = new ArrayList<>();

    public Customer(String fullName, String phoneNumber, String email, String gender, String dateOfBirth,
                    String address) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
}
