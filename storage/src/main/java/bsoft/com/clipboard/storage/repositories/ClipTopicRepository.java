package bsoft.com.clipboard.storage.repositories;

import bsoft.com.clipboard.storage.model.ClipTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClipTopicRepository extends JpaRepository<ClipTopic, Long> {
    Optional<ClipTopic> findByName(final String name);
}
