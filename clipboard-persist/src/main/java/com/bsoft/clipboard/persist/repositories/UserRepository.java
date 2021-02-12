package com.bsoft.clipboard.persist.repositories;


import com.bsoft.clipboard.persist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);
}
