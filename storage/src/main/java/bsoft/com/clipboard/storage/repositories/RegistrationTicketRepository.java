package bsoft.com.clipboard.storage.repositories;

import bsoft.com.clipboard.storage.model.RegistrationTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationTicketRepository extends JpaRepository<RegistrationTicket, Long> {

}
