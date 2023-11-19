package ee.kaido.PlaytechWintership2024.service;

import ee.kaido.PlaytechWintership2024.model.Player;
import ee.kaido.PlaytechWintership2024.repository.IllegitimateDataRepository;
import ee.kaido.PlaytechWintership2024.repository.PlayerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Log4j2
@Service
public class ReportService {

    @Value("${application.path}")
    String path;
    private final PlayerRepository playerRepository;
    private final IllegitimateDataRepository illegitimateDataRepository;

    public ReportService(PlayerRepository playerRepository, IllegitimateDataRepository illegitimateDataRepository) {
        this.playerRepository = playerRepository;
        this.illegitimateDataRepository = illegitimateDataRepository;
    }

    private List<Player> getPlayerList() {
        return playerRepository.findAllByIllegitimateIsFalse();
    }

    private String getIllegitimateList() {
        return illegitimateDataRepository.findAllByOrderByIdAsc().toString();
    }

    public void WriteToFile(Long balance) throws IOException {
        //write to file
        BufferedWriter writer = new BufferedWriter(new FileWriter(path + "result.txt"));
        writer.write(getPlayerList().toString().replace("[", "").replace("]", "\n\n").replace(", ", "\n"));
        writer.write(getIllegitimateList().replace("[", "").replace("]", "\n\n").replace(", ", "\n"));
        writer.write(String.valueOf(balance));
        writer.close();
        log.info(getPlayerList().toString().replace("[", "").replace("]", "").replace(", ", "\n"));
        log.info(getIllegitimateList().replace("[", "").replace("]", "").replace(", ", "\n"));
        log.info(balance);
    }

}
