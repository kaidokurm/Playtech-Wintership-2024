The app reads match data from match_data.txt and fulfills players actions in player_data.txt.
The result is written in the result.txt


In resultController gets/stores the match data.
Then goes true each action:
    Get the action:
        Checks if player is not illegitimate
        If Deposit or Withdraw:
            Chane player balance
            If Withdraw bigger than balance mark player as illegitimate
        If bet, go true the game:
            Get match
            Check if legal:
                Legal:
                    bets+1
                    Win - casinoBalance-win
                    Lose- casinoBalance+bet
                    Add player to match
                Illegal:
                    Store the first action
                    Set player as illegitimate
Get the end CasinoBalance without illegitimate players
Write to the result.txt


