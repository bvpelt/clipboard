package bsoft.com.clipboard.repositories;

import bsoft.com.clipboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    String FIND_BY_API_KEY = "SELECT u.* " +
            "FROM ClipUser u, RegistrationTicket t " +
            "WHERE u.registrationticket_id = t.id AND " +
            "t.userticket = :apikey ";

    Optional<User> findByEmail(final String email);

    @Query(value = FIND_BY_API_KEY, nativeQuery = true)
    List<User> findByApiKey(@Param("apikey") String apikey);

}
