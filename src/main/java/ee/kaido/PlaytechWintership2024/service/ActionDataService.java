package ee.kaido.PlaytechWintership2024.service;

import ee.kaido.PlaytechWintership2024.model.ActionData;
import ee.kaido.PlaytechWintership2024.model.Operation;
import ee.kaido.PlaytechWintership2024.model.Side;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

@Log4j2
@Service
public class ActionDataService {

    @Value("${application.path}")
    String path;
    private final PlayGameService playGameService;

    public ActionDataService(PlayGameService playGameService) {
        this.playGameService = playGameService;
    }

    //get action data and go to play
    public void getActionDataAndPlayGames() throws Exception {
        String nextValue;
        try {
            File file = new File(path + "player_data.txt");
            Scanner input = new Scanner(file)
                    .useDelimiter(",|\\R")
                    .useLocale(Locale.ENGLISH);
            while (input.hasNext()) {
                nextValue = input.next().replace(",", "");
                //skip empty line
                if (nextValue.equals("")) continue;
                UUID id = UUID.fromString(nextValue);

                nextValue = input.next().replace(",", "");
                Operation operation = Operation.valueOf(nextValue);

                nextValue = input.next().replace(",", "");
                UUID matchId = null;
                if (!nextValue.equals("")) matchId = UUID.fromString(nextValue);

                nextValue = input.next().replace(",", "");
                int coins = Integer.parseInt(nextValue);

                Side side = null;
                if (input.hasNext()) {
                    nextValue = input.next().replace(",", "");
                    side = (!nextValue.equals("") ? Side.valueOf(nextValue) : null);
                }

                ActionData action = new ActionData(id, operation, matchId, coins, side);
                log.info(action);

                //Check the game and update casino balance
                playGameService.playGame(action);
            }

        } catch (Exception e) {
            throw new Exception("Error in player_data.txt: " + e.getMessage());
        }
    }
}
