/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * @author Jackson Tai
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Pet.findByAppointmentVetId",
                query = "SELECT p FROM Pet p WHERE EXISTS (SELECT a FROM Appointment a WHERE " +
                        "a.pet = p AND a.assignedVet.clinicUserId = :vetId)"),
        @NamedQuery(name = "Pet.countByAppointmentVetId",
                query = "SELECT COUNT(p) FROM Pet p WHERE EXISTS (SELECT a FROM Appointment a WHERE " +
                        "a.pet = p AND a.assignedVet.clinicUserId = :vetId)"),
})
@Data
@NoArgsConstructor
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PET_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String petId;

    @Column(name = "SPECIES")
    private String species;

    @Column(name = "BREED")
    private String breed;

    @Column(name = "NAME")
    private String name;

    @Column(name = "HEALTH_STATUS")
    private String healthStatus;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<MedicalRecord> medicalRecords = new ArrayList<>();

    public Pet(String species, String breed, String name, String healthStatus) {
        this.species = species;
        this.breed = breed;
        this.name = name;
        this.healthStatus = healthStatus;
    }
}
