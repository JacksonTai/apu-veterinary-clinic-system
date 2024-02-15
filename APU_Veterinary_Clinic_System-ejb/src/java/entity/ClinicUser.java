/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Jackson Tai
 */
@Entity
@Table(name = "clinic_user")
public class ClinicUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="clinic_user_id")
    private String clinicUserId;
    
    @Column(name="full_name")
    private String fullName;

    @Column(name="email")
    private String email;
    
    @Column(name="password")
    private String password;
    
    @Column(name="phone_number")
    private String phoneNumber;

    public ClinicUser() {
    }

    public ClinicUser(String clinicUserId, String fullName, String email, String password, String phoneNumber) {
        this.clinicUserId = clinicUserId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getClinicUserId() {
        return clinicUserId;
    }

    public void setClinicUserId(String clinicUserId) {
        this.clinicUserId = clinicUserId;
    }
   
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clinicUserId != null ? clinicUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClinicUser)) {
            return false;
        }
        ClinicUser other = (ClinicUser) object;
        if ((this.clinicUserId == null && other.clinicUserId != null) || (this.clinicUserId != null && !this.clinicUserId.equals(other.clinicUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ClinicUser[ clinicUserId=" + clinicUserId + " ]";
    }
    
}
