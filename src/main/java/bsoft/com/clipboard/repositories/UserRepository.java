package bsoft.com.clipboard.repositories;

import bsoft.com.clipboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);


    @Query(
            value = "SELECT u.* FROM ClipUser u, RegistrationTicket t WHERE u.registrationticket_id = t.id AND t.userticket = :apikey limit 1", nativeQuery = true)
    User findByApiKey(@Param("apikey") String apikey);

}
