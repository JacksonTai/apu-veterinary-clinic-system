package entity;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-02-22T17:04:15")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, String> phoneNumber;
    public static volatile SingularAttribute<Customer, String> address;
    public static volatile SingularAttribute<Customer, String> gender;
    public static volatile SingularAttribute<Customer, String> customerId;
    public static volatile SingularAttribute<Customer, String> fullName;
    public static volatile SingularAttribute<Customer, LocalDate> dateOfBirth;
    public static volatile SingularAttribute<Customer, String> email;

}