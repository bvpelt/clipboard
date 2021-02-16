package bsoft.com.clipboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity(name = "User")
@Table(name = "clipuser")
public class User {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(min=0, max=24)
    @Column(name = "NAME")
    private String name;

    @NotBlank
    @Size(min=0, max=128)
    @Column(name = "EMAIL")
    private String email;    // email adres, must have a value and be unique

    @NotBlank
    @Size(min=0, max=128)
    @Column(name = "ENDPOINT")
    private String endpoint;

    @Size(min=0, max=24)
//    @JsonIgnore
    @Column(name = "STATUS")
    private String status;   // possible values [ created, confirmed, disabled, removed ]

    @OneToOne(optional = true)
    @JoinColumn(name = "REGISTRATIONTICKET_ID", referencedColumnName = "ID")
    private RegistrationTicket registrationTicket;

}
