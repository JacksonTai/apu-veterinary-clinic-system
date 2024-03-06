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
 *
 * @author Jackson Tai
 */
@Entity
@Table(name="MEDICAL_RECORD")
@Data
@NoArgsConstructor
public class MedicalRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEDICAL_RECORD_ID")
    private String medicalRecordId;

    @Column(name = "DIAGNOSIS")
    private String diagnosis;

    @Column(name = "PRONOGSIS")
    private String prognosis;

    public MedicalRecord(String diagnosis, String prognosis) {
        this.diagnosis = diagnosis;
        this.prognosis = prognosis;
    }

}
