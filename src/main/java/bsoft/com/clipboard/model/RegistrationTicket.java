package bsoft.com.clipboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationTicket {
    private String status;
    private String userTicket;
    private String errorMessage;
}
