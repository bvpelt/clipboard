package bsoft.com.clipboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Data
@Entity(name = "RegistrationTicket")
@Table(name = "registrationticket")
public class RegistrationTicket {

    @JsonIgnore
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID")
    private Long id;

    /*
    @JsonInclude
    @Transient
    private String status;
*/
    @Size(min = 0, max = 48)
    @Column(name = "USERTICKET")
    private String userTicket;

    @Size(min = 0, max = 24)
    @Column(name = "STATUS")
    private String status; // values created, inactive

    /*
    @JsonInclude
    @Transient
    private String errorMessage;
*/
}
