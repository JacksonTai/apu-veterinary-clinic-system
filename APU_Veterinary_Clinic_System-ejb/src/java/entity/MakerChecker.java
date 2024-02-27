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
@Table(name = "MAKER_CHECKER")
@NamedQueries({
        @NamedQuery(name = "Customer.findByMakerIdAndModuleAndActionType",
                query = "SELECT mc FROM MakerChecker mc WHERE mc.makerId = :makerId AND mc.module = :module AND " +
                        "mc.actionType = :actionType")
})
@Data
@NoArgsConstructor
public class MakerChecker implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MAKER_CHECKER_ID")
    private String makerCheckerId;

    @Column(name = "MAKER_ID")
    private String makerId;

    @Column(name = "CHECKER_ID")
    private String checkerId;

    @Column(name = "MODULE")
    private String module;

    @Column(name = "ACTION_TYPE")
    private String actionType;

    @Column(name = "CURRENT_VALUE", columnDefinition = "VARCHAR(32672)")
    private String currentValue;

    @Column(name = "NEW_VALUE", columnDefinition = "VARCHAR(32672)")
    private String newValue;

    @Column(name = "STATUS")
    private String status;

    public MakerChecker(String makerId, String checkerId, String module, String actionType, String currentValue,
                        String newValue, String status) {
        this.makerId = makerId;
        this.checkerId = checkerId;
        this.module = module;
        this.actionType = actionType;
        this.currentValue = currentValue;
        this.newValue = newValue;
        this.status = status;
    }

}

