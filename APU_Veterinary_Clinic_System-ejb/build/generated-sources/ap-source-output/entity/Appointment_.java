package entity;

import constant.AppointmentStatus;
import entity.Pet;
import entity.Vet;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-24T17:29:36")
@StaticMetamodel(Appointment.class)
public class Appointment_ { 

    public static volatile SingularAttribute<Appointment, Vet> assignedVet;
    public static volatile SingularAttribute<Appointment, String> appointmentId;
    public static volatile SingularAttribute<Appointment, AppointmentStatus> appointmentStatus;
    public static volatile SingularAttribute<Appointment, String> appointmentDate;
    public static volatile SingularAttribute<Appointment, Pet> pet;

}