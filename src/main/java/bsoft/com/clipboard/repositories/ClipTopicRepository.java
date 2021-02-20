package bsoft.com.clipboard.repositories;

import bsoft.com.clipboard.model.ClipTopic;
import bsoft.com.clipboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClipTopicRepository extends JpaRepository<ClipTopic, Long> {
    Optional<ClipTopic> findByName(final String name);
}
