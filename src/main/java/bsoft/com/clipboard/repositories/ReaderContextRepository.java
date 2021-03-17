package bsoft.com.clipboard.repositories;

import bsoft.com.clipboard.model.ClipTopic;
import bsoft.com.clipboard.model.PostMessage;
import bsoft.com.clipboard.model.ReaderContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderContextRepository extends JpaRepository<ReaderContext, Long> {
    Optional<ReaderContext> findByContextName(final String name);
}
