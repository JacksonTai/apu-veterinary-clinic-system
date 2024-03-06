package model.appointment.createForm;

import entity.Customer;
import entity.Expertise;
import entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDetailModel {

    private String customerDetails;
    private Customer customer;
    private List<Pet> availablePets;
    private String selectedPetId;
    private LocalDate selectedWeek;
    private List<Expertise> selectedExpertises;

}
