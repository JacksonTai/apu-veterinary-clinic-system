/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.constant;

import lombok.Getter;

/**
 *
 * @author Jackson Tai
 */
@Getter
public enum AppointmentStatus {
    SCHEDULED("Scheduled"),
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled"),
    IN_PROGRESS("In progress"),
    COMPLETED("Completed");

    private final String description;

    AppointmentStatus(String description) {
        this.description = description;
    }

}
