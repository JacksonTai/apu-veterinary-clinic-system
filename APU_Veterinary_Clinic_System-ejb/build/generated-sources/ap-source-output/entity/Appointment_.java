package entity;

import entity.constant.AppointmentStatus;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-21T09:13:36")
@StaticMetamodel(Appointment.class)
public class Appointment_ { 

    public static volatile SingularAttribute<Appointment, String> appointmentId;
    public static volatile SingularAttribute<Appointment, AppointmentStatus> appointmentStatus;
    public static volatile SingularAttribute<Appointment, Date> appointmentDate;

}