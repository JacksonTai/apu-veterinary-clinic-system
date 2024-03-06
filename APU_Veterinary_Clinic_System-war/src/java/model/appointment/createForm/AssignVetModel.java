package model.appointment.createForm;

import entity.Expertise;
import entity.Vet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignVetModel {

    private boolean noAvailableVets;
    private Map<LocalDate, List<Vet>> dateToVetsMap;
    private List<Expertise> selectedExpertises;

}
