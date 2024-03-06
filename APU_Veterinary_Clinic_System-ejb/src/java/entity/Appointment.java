/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import constant.AppointmentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Jackson Tai
 */
@Entity
@Table(name = "APPOINTMENT")
@NamedQueries({
        @NamedQuery(name = "Appointment.findByStatus",
                query = "SELECT a FROM Appointment a WHERE a.appointmentStatus = :appointmentStatus"),
        @NamedQuery(name = "Appointment.countByStatus",
                query = "SELECT COUNT(a) FROM Appointment a WHERE a.appointmentStatus = :appointmentStatus"),
        @NamedQuery(name = "Appointment.findByDateAndCustomerAndPetAndVetAndStatus",
                query = "SELECT a FROM Appointment a WHERE a.appointmentDate = :date AND " +
                        "a.customer.customerId = :customerId AND a.pet.petId = :petId AND " +
                        "a.assignedVet.clinicUserId = :vetId AND a.appointmentStatus = :status"),
})
@Data
@NoArgsConstructor
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "APPOINTMENT_ID")
    private String appointmentId;

    @Column(name = "APPOINTMENT_DATE")
    private String appointmentDate;

    @Column(name = "APPOINTMENT_STATUS")
    private String appointmentStatus;

    @OneToOne
    @JoinColumn(name = "VET_ID")
    private Vet assignedVet;

    @OneToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "PET_ID")
    private Pet pet;

    public Appointment(String appointmentDate, String appointmentStatus, Vet assignedVet, Customer customer, Pet pet) {
        this.appointmentDate = appointmentDate;
        this.appointmentStatus = appointmentStatus;
        this.assignedVet = assignedVet;
        this.customer = customer;
        this.pet = pet;
    }


    public Appointment(String appointmentDate, Vet assignedVet, Customer customer, Pet pet) {
        this.appointmentDate = appointmentDate;
        this.appointmentStatus = AppointmentStatus.SCHEDULED;
        this.assignedVet = assignedVet;
        this.customer = customer;
        this.pet = pet;
    }

}
