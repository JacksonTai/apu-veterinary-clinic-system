/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 *
 * @author Jackson Tai
 */
@Entity
@NoArgsConstructor
public class Customer extends ClinicUser {

    public Customer(String fullName, String email, String password, String phoneNumber, String userType) {
        super(fullName, email, password, phoneNumber, userType);
    }

}
