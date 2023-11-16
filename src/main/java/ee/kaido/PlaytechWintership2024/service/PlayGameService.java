package ee.kaido.PlaytechWintership2024.service;

import ee.kaido.PlaytechWintership2024.model.*;
import ee.kaido.PlaytechWintership2024.repository.CasinoRepository;
import ee.kaido.PlaytechWintership2024.repository.IllegitimateDataRepository;
import ee.kaido.PlaytechWintership2024.repository.MatchDataRepository;
import ee.kaido.PlaytechWintership2024.repository.PlayerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Log4j2
@Service
public class PlayGameService {
    private final PlayerRepository playerRepository;
    private final IllegitimateDataRepository illegitimateDataRepository;
    private final MatchDataRepository matchDataRepository;
    private final CasinoRepository casinoRepository;

    public PlayGameService(PlayerRepository playerRepository, IllegitimateDataRepository illegitimateDataRepository, MatchDataRepository matchDataRepository, CasinoRepository casinoRepository) {
        this.playerRepository = playerRepository;
        this.illegitimateDataRepository = illegitimateDataRepository;
        this.matchDataRepository = matchDataRepository;
        this.casinoRepository = casinoRepository;
    }

    public void playGame(ActionData action) {
        //get player or create new
        Player player = playerRepository.findById(action.getId()).orElseGet(() ->
                new Player(action.getId(), 0, BigDecimal.valueOf(0.00f), false, 0, 0));
        //if not an illegal player
        if (!player.isIllegitimate()) {
            //check if it's a Deposit, Bet or Withdraw
            switch (action.getOperation()) {
                //add deposit to players balance
                case DEPOSIT -> {
                    player.setBalance(player.getBalance() + action.getCoins());
                    playerRepository.save(player);
                }
                //check the game
                case BET -> {
                    checkGame(action, player);
                }
                //player takes coins out
                case WITHDRAW -> {
                    if ((player.getBalance() - action.getCoins()) < 0) IllegitimatePlayer(player, action);
                    else player.setBalance(player.getBalance() - action.getCoins());
                    playerRepository.save(player);
                }
                default -> throw new IllegalStateException("Unexpected value: " + action.getOperation());
            }
        }
    }

    private void checkGame(ActionData action, Player player) {
        Optional<MatchData> match = matchDataRepository.findById(action.getMatchId());
        //if match exists and balance is bigger than bet and its first bet in this match
        if (player.getBalance() > action.getCoins() && match.isPresent() && !match.get().getPlayers().contains(player)) {

            player.setBets(player.getBets() + 1);
            // if it`s a win
            if (match.get().getMatchResult().equals(action.getSide())) {
                int win = action.getCoins();
                if (action.getSide() == Side.A) {
                    win = (int) (win * match.get().getASideRate());
                } else {
                    win = (int) (win * match.get().getBSideRate());
                }
                player.setBalance(player.getBalance() + win);
                player.setWins(player.getWins() + 1);
                log.info("Win: " + win);
                CasinoBalance casinoBalance = new CasinoBalance();
                casinoBalance.setPlayer(player);
                casinoBalance.setBalance((long) -win);
                casinoRepository.save(casinoBalance);
            } else {
                //if not a draw
                if (!match.get().getMatchResult().equals(Side.DRAW)) {
                    player.setBalance(player.getBalance() - action.getCoins());
                    CasinoBalance casinoBalance = new CasinoBalance();
                    casinoBalance.setPlayer(player);
                    casinoBalance.setBalance((long) action.getCoins());
                    casinoRepository.save(casinoBalance);
                }
            }

            player.setWinRate(new BigDecimal(BigInteger.valueOf(100 * player.getWins() / player.getBets()), 2));
            playerRepository.save(player);

            match.get().getPlayers().add(player);
            matchDataRepository.save(match.get());
        } else IllegitimatePlayer(player, action);
    }

    private void IllegitimatePlayer(Player player, ActionData action) {
        log.error("Illegal: " + action);
        if (!player.isIllegitimate()) {
            illegitimateDataRepository.save(new IllegitimateData(
                    action.getId(), action.getOperation(), action.getMatchId(),
                    action.getCoins(), action.getSide()));
            player.setIllegitimate(true);
            playerRepository.save(player);
        }

    }
}
