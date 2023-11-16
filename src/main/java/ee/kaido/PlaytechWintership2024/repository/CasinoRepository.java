package ee.kaido.PlaytechWintership2024.repository;

import ee.kaido.PlaytechWintership2024.model.CasinoBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CasinoRepository extends JpaRepository<CasinoBalance, Long> {

    @Query(value = "SELECT SUM(Casino_Balance.balance) as balance " +
            "FROM Casino_Balance, PLAYER  " +
            "WHERE PLAYER.ID = PLAYER_ID and PlAYER.ILLEGITIMATE =false",
            nativeQuery = true)
    Long getCasinoBalance();
}
