/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static constant.UserRole.VET;

/**
 *
 * @author Jackson Tai
 */
@Entity
@DiscriminatorValue(VET)
@NoArgsConstructor
public class Vet extends ClinicUser {

//    @Column(name="EXPERTISE")
//    private String expertise;

    public Vet(String email, String password) {
        super(email, password);
    }

}
