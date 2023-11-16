package ee.kaido.PlaytechWintership2024.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    private UUID id;
    private long balance;
    private BigDecimal winRate;
    private boolean illegitimate = false;
    private long wins;
    private long bets;

    @Override
    public String toString() {
        return id.toString() + " " + balance + " " + winRate.toString();
    }
}
