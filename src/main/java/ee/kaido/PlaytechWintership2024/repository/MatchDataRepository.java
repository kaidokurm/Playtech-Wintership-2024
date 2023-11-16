package ee.kaido.PlaytechWintership2024.repository;

import ee.kaido.PlaytechWintership2024.model.MatchData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MatchDataRepository extends JpaRepository<MatchData, UUID> {

}
