package bsoft.com.clipboard.repositories;

import bsoft.com.clipboard.model.ClipTopic;
import bsoft.com.clipboard.model.Subscription;
import bsoft.com.clipboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserAndClipTopic(User user, ClipTopic clipTopic);

    @Query(
            value = "SELECT s FROM subscription s, clipuser u, cliptopic t WHERE s.user_id = u.id AND s.cliptopic_id = t.id AND t.name = :cliptopicname AND u.id=:userid limit 1", nativeQuery = true)
    Subscription findByUserAndClipTopicName(@Param("userid") Long userid, @Param("cliptopicname") String cliptopicname);
}
