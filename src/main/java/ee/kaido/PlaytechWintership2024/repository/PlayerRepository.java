package ee.kaido.PlaytechWintership2024.repository;

import ee.kaido.PlaytechWintership2024.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    List<Player> findAllByIllegitimateIsFalse();
}
