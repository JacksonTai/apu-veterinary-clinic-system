/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static constant.UserRole.VET;

/**
 *
 * @author Jackson Tai
 */
@Entity
@Data
@DiscriminatorValue(VET)
@NoArgsConstructor
public class Vet extends ClinicUser {

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "VET_EXPERTISES", joinColumns = @JoinColumn(name = "VET_ID"))
    @Column(name="EXPERTISE")
    private List<String> expertise;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Appointment> appointments = new ArrayList<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "WORKING_DAYS", joinColumns = @JoinColumn(name = "VET_ID"))
    @Column(name="WORKING_DAY")
    private List<String> workingDays = new ArrayList<>();

    public Vet(String email, String password, String fullName) {
        super(email, password, fullName);
    }

}
