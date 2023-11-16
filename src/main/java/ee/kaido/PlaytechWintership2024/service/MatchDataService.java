package ee.kaido.PlaytechWintership2024.service;

import ee.kaido.PlaytechWintership2024.model.MatchData;
import ee.kaido.PlaytechWintership2024.model.Side;
import ee.kaido.PlaytechWintership2024.repository.MatchDataRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Log4j2
@Service
public class MatchDataService {

    @Value("${application.path}")
    String path;
    private final MatchDataRepository matchDataRepository;

    public MatchDataService(MatchDataRepository matchDataRepository) {
        this.matchDataRepository = matchDataRepository;
    }

    public void getMatchData() {

        Collection<MatchData> items = new ArrayList<MatchData>();
        String nextValue = "";
        try {
            File file = new File(path + "match_data.txt");
            Scanner input = new Scanner(file)
                    .useDelimiter(",|\\R")
                    .useLocale(Locale.ENGLISH);


            while (input.hasNext()) {
                nextValue = input.next().replace(",", "");
                UUID id = UUID.fromString(nextValue);

                nextValue = input.next().replace(",", "");
                float aSideRate = Float.parseFloat(nextValue);

                nextValue = input.next().replace(",", "");
                float bSideRate = Float.parseFloat(nextValue);

                nextValue = input.next().replace(",", "");
                Side matchResult = Side.valueOf(nextValue);

                items.add(new MatchData(id, aSideRate, bSideRate, matchResult, new HashSet<>()));
            }
            log.info(items);
            matchDataRepository.saveAll(items);

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }

}
