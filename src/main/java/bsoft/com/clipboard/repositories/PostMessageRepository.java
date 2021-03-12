package bsoft.com.clipboard.repositories;

import bsoft.com.clipboard.model.PostMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostMessageRepository extends JpaRepository<PostMessage, Long> {
}
