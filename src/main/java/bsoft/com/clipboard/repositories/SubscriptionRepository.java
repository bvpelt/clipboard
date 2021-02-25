package bsoft.com.clipboard.repositories;

import bsoft.com.clipboard.model.ClipTopic;
import bsoft.com.clipboard.model.Subscription;
import bsoft.com.clipboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserAndClipTopic(User user, ClipTopic clipTopic);
}
