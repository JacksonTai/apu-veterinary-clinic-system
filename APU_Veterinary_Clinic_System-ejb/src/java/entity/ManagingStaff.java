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
public class ManagingStaff extends ClinicUser {

    public ManagingStaff(String fullName, String email, String password, String phoneNumber) {
        super(fullName, email, password, phoneNumber);
    }

}
