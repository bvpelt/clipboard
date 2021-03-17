package bsoft.com.clipboard.repositories;

import bsoft.com.clipboard.model.ReaderContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReaderContextRepository extends JpaRepository<ReaderContext, Long> {



    Optional<ReaderContext> findByContextName(final String name);


}
