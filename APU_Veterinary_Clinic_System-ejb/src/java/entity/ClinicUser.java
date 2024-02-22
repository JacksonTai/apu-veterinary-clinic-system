/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        @NamedQuery(name = "ClinicUser.findAll", query = "SELECT c FROM ClinicUser c"),
        @NamedQuery(name = "ClinicUser.findByEmail", query = "SELECT c FROM ClinicUser c WHERE c.email = :email"),
})
@Data
@NoArgsConstructor
public class ClinicUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CLINIC_USER_ID")
    @Setter(AccessLevel.NONE)
    private String clinicUserId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_ROLE")
    private String userRole;

    public ClinicUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
