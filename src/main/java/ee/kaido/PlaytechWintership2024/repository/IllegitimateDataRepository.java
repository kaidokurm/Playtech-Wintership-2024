package ee.kaido.PlaytechWintership2024.repository;

import ee.kaido.PlaytechWintership2024.model.IllegitimateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IllegitimateDataRepository extends JpaRepository<IllegitimateData, UUID> {
    List<IllegitimateData> findAllByOrderByIdAsc();

}
