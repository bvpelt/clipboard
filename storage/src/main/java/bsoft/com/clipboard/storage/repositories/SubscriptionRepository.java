package bsoft.com.clipboard.storage.repositories;

import bsoft.com.clipboard.storage.model.ClipTopic;
import bsoft.com.clipboard.storage.model.Publisher;
import bsoft.com.clipboard.storage.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    String FIND_BY_PUBLISHER_AND_CLIPTOPIC_NAME = "SELECT s.* " +
            "FROM subscription s, publisher p, cliptopic t " +
            "WHERE s.publisher_id = p.id AND " +
            "s.cliptopic_id = t.id AND " +
            "t.name = :cliptopicname " +
            "AND p.id=:publisherid";

    Optional<Subscription> findByPublisherAndClipTopic(Publisher publisher, ClipTopic clipTopic);

    @Query(value = FIND_BY_PUBLISHER_AND_CLIPTOPIC_NAME, nativeQuery = true)
    List<Subscription> findByPublisherAndClipTopicName(@Param("publisherid") Long publisherid, @Param("cliptopicname") String cliptopicname);

}
