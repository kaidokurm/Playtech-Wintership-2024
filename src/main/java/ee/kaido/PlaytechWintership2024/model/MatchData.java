package ee.kaido.PlaytechWintership2024.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MatchData {
    @Id
    private UUID id;
    private float aSideRate;
    private float bSideRate;
    private Side matchResult;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Player> players = new HashSet<>();
}
