package entity;

import entity.Appointment;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-25T14:34:53")
@StaticMetamodel(Vet.class)
public class Vet_ extends ClinicUser_ {

    public static volatile ListAttribute<Vet, Appointment> appointments;
    public static volatile ListAttribute<Vet, String> expertise;

}