package bsoft.com.clipboard.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
@Entity(name = "RegistrationTicket")
@Table(name = "registrationticket")
public class RegistrationTicket implements Serializable {

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
    @Size(min = 0, max = 36)
    @Column(name = "PUBLISHERTICKET")
    private String publisherTicket;

    @Size(min = 0, max = 12)
    @Column(name = "STATUS")
    private String status; // values created, inactive

    /*
    @JsonInclude
    @Transient
    private String errorMessage;
*/
}
