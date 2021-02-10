package bsoft.com.clipboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;


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

    @JsonInclude
    @Transient
    private String status;

    @Column(name = "USERTICKET")
    private String userTicket;

    @JsonInclude
    @Transient
    private String errorMessage;

}
