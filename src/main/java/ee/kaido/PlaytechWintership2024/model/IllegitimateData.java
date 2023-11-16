package ee.kaido.PlaytechWintership2024.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IllegitimateData {
    @Id
    private UUID id;
    private Operation operation;
    private UUID matchId;
    private int coins;
    private Side side;

    @Override
    public String toString() {
        return id.toString() + " " +
                operation.toString() + " " +
                (matchId == null ? "null" : matchId.toString()) + " " +
                coins + " " +
                (side == null ? "null" : side.toString());
    }
}
