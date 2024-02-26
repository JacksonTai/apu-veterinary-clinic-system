/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Jackson Tai
 */
@Entity
@Table(name = "EXPERTISE")
@NamedQueries({
        @NamedQuery(name = "Expertise.findByName",
                query = "SELECT e FROM Expertise e WHERE LOWER(e.name) = LOWER(:name)")
})
@Data
@NoArgsConstructor
public class Expertise implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EXPERTISE_ID")
    private String expertiseId;

    @Column(name = "NAME")
    private String name;

    public Expertise(String name) {
        this.name = name;
    }

}
