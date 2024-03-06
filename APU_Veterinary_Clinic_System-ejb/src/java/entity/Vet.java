/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static constant.UserRole.VET;

/**
 *
 * @author Jackson Tai
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@NamedQueries({
        @NamedQuery(name = "Vet.findAllByExpertiseId",
                query = "SELECT v FROM Vet v JOIN v.expertises e WHERE e.expertiseId = :expertiseId"),
})
@Data
@DiscriminatorValue(VET)
@NoArgsConstructor
public class Vet extends ClinicUser {

    @OneToMany(fetch = FetchType.EAGER)
    private List<Expertise> expertises = new ArrayList<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "VET_WORKING_DAYS", joinColumns = @JoinColumn(name = "VET_ID"))
    @Column(name="WORKING_DAY")
    private List<String> workingDays = new ArrayList<>();

    public Vet(String email, String password, String fullName) {
        super(email, password, fullName);
    }

}
