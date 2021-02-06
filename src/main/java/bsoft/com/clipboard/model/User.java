package bsoft.com.clipboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private String name;

    private String email;    // email adres, must have a value and be unique

    private String endpoint;

    @JsonIgnore
    private String status;   // possible values [ created, confirmed, disabled, removed ]

}
