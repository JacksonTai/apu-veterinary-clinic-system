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

/**
 * @author Jackson Tai
 */
@Entity
@Table(name = "CLINIC_USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_ROLE", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
        @NamedQuery(name = "ClinicUser.findByEmailAndStatus",
                query = "SELECT c FROM ClinicUser c WHERE LOWER(c.email) = LOWER(:email) AND c.status = :status"),
        @NamedQuery(name = "ClinicUser.findByFullNameAndStatus",
                query = "SELECT c FROM ClinicUser c WHERE LOWER(c.fullName) = LOWER(:fullName) AND c.status = :status"),
        @NamedQuery(name = "ClinicUser.findByIdOrFullNameOrEmailAndStatus",
                query = "SELECT c FROM ClinicUser c WHERE (c.clinicUserId = :input OR " +
                        "LOWER(c.fullName) = LOWER(:input) OR LOWER(c.email) = LOWER(:input)) AND c.status = :status"),
        @NamedQuery(name = "ClinicUser.countByStatus",
                query = "SELECT COUNT(c) FROM ClinicUser c WHERE c.status = :status"),
        @NamedQuery(name = "ClinicUser.findByUserRoleAndStatus",
                query = "SELECT c FROM ClinicUser c WHERE c.userRole = :userRole AND c.status = :status"),
        @NamedQuery(name = "ClinicUser.countByUserRoleAndStatus",
                query = "SELECT COUNT(c) FROM ClinicUser c WHERE c.userRole = :userRole AND c.status = :status"),
})
@Data
@NoArgsConstructor
public class ClinicUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CLINIC_USER_ID")
    private String clinicUserId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "USER_ROLE")
    private String userRole;

    @Column(name = "STATUS")
    private String status;

    public ClinicUser(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

}
