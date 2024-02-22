/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static constant.UserRole.MANAGING_STAFF;

/**
 *
 * @author Jackson Tai
 */
@Entity
@DiscriminatorValue(MANAGING_STAFF)
@NoArgsConstructor
public class ManagingStaff extends ClinicUser {

    public ManagingStaff(String email, String password) {
        super(email, password);
    }

}
