package bsoft.com.clipboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity(name = "User")
@Table(name = "clipuser")
public class User {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;    // email adres, must have a value and be unique

    @Column(name = "ENDPOINT")
    private String endpoint;

//    @JsonIgnore
    @Column(name = "STATUS")
    private String status;   // possible values [ created, confirmed, disabled, removed ]

    @OneToOne(optional = true)
    @JoinColumn(name = "REGISTRATIONTICKET_ID", referencedColumnName = "ID")
    private RegistrationTicket registrationTicket;

}
