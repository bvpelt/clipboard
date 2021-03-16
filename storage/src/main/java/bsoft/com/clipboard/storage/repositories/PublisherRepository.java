package bsoft.com.clipboard.storage.repositories;

import bsoft.com.clipboard.storage.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    String FIND_BY_API_KEY = "SELECT p.* " +
            "FROM Publisher p, RegistrationTicket t " +
            "WHERE p.registrationticket_id = t.id AND " +
            "t.publisherticket = :apikey ";

    Optional<Publisher> findByEmail(final String email);

    @Query(value = FIND_BY_API_KEY, nativeQuery = true)
    List<Publisher> findByApiKey(@Param("apikey") String apikey);

}
