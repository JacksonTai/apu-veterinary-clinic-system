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
import org.hibernate.validator.constraints.NotEmpty;
import validation.EmptyFields;
import validation.InvalidFields;
import validation.valid.ValidClinicUser;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static constant.RegexConstant.*;
import static constant.i18n.En.*;

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
@ValidClinicUser
public class ClinicUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name="CLINIC_USER_ID")
    private String clinicUserId;

//    @NotEmpty(message = EMPTY_FULL_NAME_MESSAGE)
//    @Size(min = 1, max = 50, message = INVALID_FULL_NAME_MESSAGE)
    @Column(name="FULL_NAME")
    private String fullName;

//    @NotEmpty(message = EMPTY_EMAIL_MESSAGE)
//    @Pattern(regexp = EMAIL_REGEX, message = INVALID_EMAIL_MESSAGE)
    @Column(name="EMAIL")
    private String email;

//    @NotEmpty(message = EMPTY_PASSWORD_MESSAGE)
//    @Pattern(regexp = PASSWORD_REGEX, message = INVALID_PASSWORD_MESSAGE)
    @Column(name="PASSWORD")
    private String password;

//    @NotEmpty(message = EMPTY_PHONE_NUMBER_MESSAGE)
//    @Pattern(regexp = MY_PHONE_REGEX, message = INVALID_PHONE_NUMBER_MESSAGE)
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

    public ClinicUser(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
