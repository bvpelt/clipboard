package com.bsoft.clipboard.persist.repositories;


import com.bsoft.clipboard.persist.model.RegistrationTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationTicketRepository extends JpaRepository<RegistrationTicket, Long> {

}
