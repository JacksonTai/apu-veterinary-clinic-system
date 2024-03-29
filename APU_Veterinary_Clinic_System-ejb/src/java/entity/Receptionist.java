/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static constant.UserRole.RECEPTIONIST;

/**
 *
 * @author Jackson Tai
 */
@Entity
@DiscriminatorValue(RECEPTIONIST)
@NoArgsConstructor
public class Receptionist extends ClinicUser {

    public Receptionist(String email, String password, String fullName) {
        super(email, password, fullName);
    }

}
