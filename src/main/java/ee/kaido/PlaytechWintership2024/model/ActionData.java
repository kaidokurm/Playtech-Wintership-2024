package ee.kaido.PlaytechWintership2024.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionData {
    private UUID id;
    private Operation operation;
    private UUID matchId;
    private int coins;
    private Side side;
}
