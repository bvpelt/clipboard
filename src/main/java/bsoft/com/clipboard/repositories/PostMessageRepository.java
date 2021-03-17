package bsoft.com.clipboard.repositories;

import bsoft.com.clipboard.model.PostMessage;
import bsoft.com.clipboard.model.ReaderContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostMessageRepository extends JpaRepository<PostMessage, Long> {
    String FIND_NEXT_CONTEXT = "SELECT p.* " +
            "FROM postmessage p " +
            "WHERE p.id > :id " +
            "ORDER BY ID " +
            "LIMIT 1";

    @Query(value = FIND_NEXT_CONTEXT, nativeQuery = true)
    List<PostMessage> findNextContext(@Param("id") Long id);
}
