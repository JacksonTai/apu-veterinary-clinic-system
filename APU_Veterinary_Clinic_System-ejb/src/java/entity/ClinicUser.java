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

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Jackson Tai
 */
@Entity
@Table(name = "CLINIC_USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
    @NamedQuery(name = "ClinicUser.findAll", query = "SELECT c FROM ClinicUser c"),
    @NamedQuery(name = "ClinicUser.findByFullName", query = "SELECT c FROM ClinicUser c WHERE c.fullName = :fullName"),
    @NamedQuery(name = "ClinicUser.findByEmail", query = "SELECT c FROM ClinicUser c WHERE c.email = :email"),
    @NamedQuery(name = "ClinicUser.findByPhoneNumber", query = "SELECT c FROM ClinicUser c WHERE c.phoneNumber = :phoneNumber")
})
@Data
@NoArgsConstructor
public class ClinicUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="CLINIC_USER_ID")
    @Setter(AccessLevel.NONE)
    private String clinicUserId;
    
    @Column(name="FULL_NAME")
    private String fullName;

    @Column(name="EMAIL")
    private String email;
    
    @Column(name="PASSWORD")
    private String password;
    
    @Column(name="PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "USER_TYPE", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private String userType;

    public ClinicUser(String fullName, String email, String password, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

}
