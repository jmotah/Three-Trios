THIS LINE IS NEEDED TO OPEN THE JAR FILE! ENSURE TO GO INTO THE FOLDER CONTAINING THE JAR FILE! TYPE THIS IN THE TERMINAL:

COMMAND-LINE OPTIONS:
    - The first 2 arguments of the command line specify the players to play the game. These are
      required parameters that cannot be left without specification:
        - "human" --> allows for an actual player to play the game
        - "strategy1" --> allows for an AI player to play the game using strategy 1
        - "strategy2" --> allows for an AI player to play the game using strategy 2
        - "strategy1and2" --> allows for an AI player to play the game using strategy 1 and 2
            combined
    - The next command line argument is optional for the rule or strategy. If a rule/strategy is
      specified, it will be applied to the game. If not specified, the game will automatically play
      with normal rules.
        - "strat:normal" --> allows for the Normal game strategy to be utilized
        - "strat:reverse" --> allows for the Reverse game strategy to be utilized
        - "strat:fallenace" --> allows for the FallenAce game strategy to be utilized
        - "strat:reverseandfallenace" --> allows for the Reverse strategy to be applied over the
                                          FallenAce game strategy; utilizes this
        - "rule:normal" --> allows for the Normal game rule to be utilized
        - "rule:same" --> allows for the Same game rule to be utilized
        - "rule:plus" --> allows for the Plus game rule to be utilized
    - The next command line argument is optional for the rule or strategy. If a rule/strategy is
      specified, it will be applied to the game. If not specified, the game will automatically play
      with normal rules. If a rule and strategy is specified on the optional command line arguments,
      it does not matter if you decide to specify the strategy in the first argument and the rule
      in the second, or vice versa.
        - "strat:normal" --> allows for the Normal game strategy to be utilized
        - "strat:reverse" --> allows for the Reverse game strategy to be utilized
        - "strat:fallenace" --> allows for the FallenAce game strategy to be utilized
        - "strat:reverseandfallenace" --> allows for the Reverse strategy to be applied over the
                                          FallenAce game strategy; utilizes this
        - "rule:normal" --> allows for the Normal game rule to be utilized
        - "rule:same" --> allows for the Same game rule to be utilized
        - "rule:plus" --> allows for the Plus game rule to be utilized


EXAMPLE:
java -jar ThreeTriosBetter.jar human human strat:reverse rule:plus
