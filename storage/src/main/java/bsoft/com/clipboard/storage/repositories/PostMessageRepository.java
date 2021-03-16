package bsoft.com.clipboard.storage.repositories;

import bsoft.com.clipboard.storage.model.PostMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostMessageRepository extends JpaRepository<PostMessage, Long> {
}
