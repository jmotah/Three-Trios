OVERVIEW (HOW THE GAME WORKS)

Three Trios is a strategic two-player card game inspired by the classic game Triple Triad.
The goal is to develop a grid-based competitive experience where each player strategically
places custom cards to control the board. This codebase is designed to implement the game’s rules,
manage game states, and provide an interactive command-line interface for players.

Three Trios is a variation of an older card game called Triple Triad. The goal of the game is for
two players to strategically place customized cards on a customized grid. The cards for the players
to use and the grid to play on are entirely customizable. The cards have four attack values ranging
from 1-9 and "A" which represents an attack value of 10. Similarly, the grid is entirely
customizable, allowing the player to set the desired number of rows and columns of the grid.
After the player must designate the layout of the grid which possess card cell spaces and/or holes.
Card cells spaces are spaces the players can play to, while holes are unplayable to.

When a player plays a card to a valid card cell grid space, battling occurs. Battle happens by
taking the placed card and battling any card cells across it's four corners. If the placed card wins
the battle, it transforms the battled against card to the color of the placed card. Then,
recursively, that successfully battled card battles against it's four cardinal directions and so on.

The game is over when all card cells are played to on the grid. The winner is determined by which
player holds the most colored card cells, including any cards in their hand. If both players
possess the same number of cards by color, a tie occurs.



OVERVIEW (ASSUMPTIONS)

The purpose of this code was to emulate the hand card game called Triple Triad, but focus on
creating the model for this game. Some high level assumptions are that we assume the user have a
foundational understanding of grid and card games, particularly with concepts of adjacency-based
combat mechanics. Familiarity with Java programming, such as the Model-View-Controller design
pattern, will help the user navigate the code.

The implementation focuses on establishing a core model for gameplay, which includes essential
classes for representing cards, players, and the grid structure. A textual view is provided to
visually render the game state, allowing users to observe the game's progress in a straightforward
manner. While a controller component has not yet been implemented, the model is intentionally
designed to support the seamless addition of this component in future updates. Currently, the
codebase does not support AI players or alternative battle rule variations. However, the design
incorporates interfaces and abstractions that make it adaptable, enabling the inclusion of these
advanced features in later versions.

A prerequisite that is required for this code is for Java to compile and run, and that file reading
capabilities for loading configuration files work. Another prerequisite is that users need grid and
card configuration files to initiate a game instance. These files specifies the layout of card cells
and hole cells as well as card properties.



QUICK START

To quickly start a game of Three Trios, edit the ThreeTriosGame class in the main method and follow
the example setup below. A card configuration file, a grid configuration file, a model object, and
a view object are required to run the game. Once made, run the main class and the game will start!

    public static void main(String[] args) {
        ThreeTriosModel<PlayingCard> model = new PlayerPlayerModel();
        ThreeTriosView view = new TextualView(model);

        File cardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/src/" +
                "tie_setup_card_config.txt");
        File gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/src/" +
                "tie_setup_grid_config.txt");

        ConfigurationReader<List<Cards>> deck = new CardReader(cardConfig);
        ConfigurationReader<Grid[][]> grid = new GridReader(gridConfig);

        model.startGame(grid, deck);
    }



KEY COMPONENTS

Model: ThreeTriosModel and PlayerPlayerModel
    This is the main body/source of code. The responsibility of the model class is to manage/change
    game states, game phases, player actions, and initializations.

ConfigurationReaders: CardReader and GridReader
    These classes primarily work to read, understand, and convert text to understandable code.
    These classes are working to make the game fully customizable through a configuration file!

Player: HumanPlayer
    Defines what a player actually is. In this instance, Player is the interface while HumanPlayer
    implements Player, allowing for variation for a possible AI player in the future. A player
    must possess a respective color and hand of list of playing cards.

View: ThreeTriosView and TextualView
    Manages the main visual display/text display of the game. Communicates with the model to
    create this display.



KEY SUBCOMPONENTS

Enums
    CardCompass - an enumeration class to represent the valid directions on each playing card.
    These positions are equivalent to cardinal direction values

    CardNumbers - an enumeration class to represent valid attack numbers for each playing card.
    These cards have integer values ranging from 1-9 and "A" which is equivalent to an integer
    value of 10.

    CellType - an enumeration class to represent the various types of cells on a grid. A hole
    refers to a grid cell that cannot be played to, a card cell refers to a grid cell that can be
    played to, and a player cell refers to a grid cell that has already been played to.

    GamePhase - an enumeration class to represent the various phases in the game. The placing phase
    is when the game is ready for the next player up to place the next card, while the battle phase
    is when battling is supposed to occur.

    GameState - an enumeration class to represent the various states in the game. The not started
    state refers to when the game is not yet started, the in progress state refers to when the game
    is in progress, and the game over state refers to when the game is over.

    PlayerColor - an enumeration class to represent the various colors for players in the game.
    Since ThreeTrios is a two player game, there are only two colors: red and blue.

    GridTile - A class to represent a single tile on a full grid. A grid tile possess an owned
    player, playing card associated with it, and a cell type.



SOURCE ORGANIZATION

SRC FOLDER:

Main Method Class:
    [src folder -> cs3500.threetrios package]


Controller Related Class/Info:
    Includes filereader package, Features interface, HintsToggleListener interface,
    ThreeTriosController class, ThreeTriosModelListener interface
    [src folder -> cs3500.threetrios.controller package]

    filereader package:
    Includes ConfigurationReader interface, CardReader class, and GridReader class
    [src folder -> cs3500.threetrios.controller.filereader]


Model Related Class/Info:
    Includes CellType enum, GamePhase enum, GameState enum, ThreeTriosModel interface,
    ReadonlyThreeTriosModel interface, cards package, grid package, player package,
    PlayerPlayerModel class, strategies package
    [src folder -> cs3500.threetrios.model package]

    cards package:
    Includes CardCompass enum, CardNumbers enum, Cards interface, and PlayingCard class
    [src folder -> cs3500.threetrios.model.cards package]

    grid package:
    Includes Grid interface, GridTile class
    [src folder -> cs3500.threetrios.model.grid package]

    player package:
    Includes Player interface, PlayerColor enum, and Player class
    [src folder -> cs3500.threetrios.model.player package]

    aistrategies package:
    Includes AbstractStrategies class, Strategies interface, Strategy1 class, Strategy1And2 class,
    and Strategy2 class
    [src folder -> cs3500.threetrios.model.aistrategies package]

    battlerules package:
    Includes BattleRules interface, NormalBattleRule class, PlusBattleRule class, SameBattleRule
    class
    [src folder -> cs3500.threetrios.model.battlerules package]

    battlestrategies package:
    Includes BattleStrategies interface, FallenaceBattleStrategy class, NormalBattleStrategy class,
    ReverseBattleStrategy class, ReverseFallenAceBattleStrategy class
    [src folder -> cs3500.threetrios.model.battlestrategies package]


View Related Class/Info:
    Includes ThreeTriosView interface, graphical package, and textual package
    [src folder -> cs3500.threetrios.view package]

    graphical package:
    Includes CardPanel class, GraphicalView class, GridLayoutPanel class, GridPanel class,
    PlayerCardsLayoutPanel class, HintDecorator class, ThreeTriosCardPanelView class,
    ThreeTriosLayoutView class
    [src folder -> cs3500.threetrios.view.graphical package]

    textual package:
    Includes TextualView class
    [src folder -> cs3500.threetrios.view.textual package]


Provider Related Class/Info:
    Includes all related code from our providers
    [src folder -> cs3500.threetrios.providers package]


Card Config Related Files/Info:
    Includes various card configuration files to be used within the game. More may be created, you
    are not limited to what is inside the package.
    [src folder -> cs3500.threetrios.cardconfigs package]


Grid Config Related Files/Info:
    Includes various grid configuration files to be used within the game. More may be created, you
    are not limited to what is inside the package.
    [src folder -> cs3500.threetrios.gridconfigs package]


Card Testing Related Class/Info:
    Includes the PlayingCardTests class
    [test folder -> cs3500.threetrios.card package]


TEST FOLDER:

Examples Class:
    [test folder]


Card Testing Config Related Files/Info:
    Includes various card configuration files to be used for testing. More may be created, you
    are not limited to what is inside the package.
    [test folder -> cs3500.threetrios.cardconfigs package]


Controller Testing Related Class/Info:
    Includes filereader package and ControllerTests class
    [test folder -> cs3500.threetrios.controller package]

    filereader package:
    Includes CardReaderTests and GridReaderTests
    [test folder -> cs3500.threetrios.controller.filereader package]


Grid Testing Config Related Files/Info:
    Includes various grid configuration files to be used for testing. More may be created, you
    are not limited to what is inside the package.
    [test folder -> cs3500.threetrios.gridconfigs package]


Model Testing Related Class/Info:
    Includes AbstractVariantModelTests class, GameModelTests class, aistrategies package,
    battlerules package, battle strategies package, card package, grid package, and player package
    [test folder -> cs3500.threetrios.model package]

    aistrategies package:
    Includes MockModelEmptyCorners class, MockModelStrategy1 class, MockStrategy1Tests class,
    MockStrategy2Tests class, Strategy1And2Tests class, Strategy1Tests class, Strategy2Tests class
    [test folder -> cs3500.threetrios.model.aistrategies package]

    battlerules package:
    Includes NormalBattleRuleTest class, PlusBattleRuleTest class, SameBattleRuleTest class
    [test folder -> cs3500.threetrios.model.battlerules package]

    battlestrategies package:
    Include FallenAceBattleStrategyTest class, NormalBattleStrategyTest class,
    ReverseBattleStrategyTest class, ReverseFallenAceBattleStrategyTest class
    [test folder -> cs3500.threetrios.model.battlestrategies package]

    card package:
    Includes PlayingCardTests class
    [test folder -> cs3500.threetrios.model.card package]

    grid package:
    Includes GridTileTests class
    [test folder -> cs3500.threetrios.model.grid package]

    player package:
    Includes PlayerTests class
    [test folder -> cs3500.threetrios.model.player package]


View Testing Related Class/info
    Includes TextualViewTests class
    [test folder -> cs3500.threetrios.view package]


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



CHANGES FROM PREVIOUS WORK:
    - Created a read-only model interface class which only holds immutable data
    - Altered the View Interface
    - Added a getColor method within the PlayerColor enum and a Color object associated to each
      PlayerColor object within the enum
    - Made un-private fields private
    - Refactored Source Organization to match MVC more
    - Added getValueString() method to get the String object of the value from a CardNumbers enum
      object

EXTRA CREDIT:
    - Created a chain version of Strategy 1 and Strategy 2 in the Strategy1And2 class.
      Priority is placed on finding the position on the grid that
     * will result in the most number of cards flipped. Then, it analyzes if one of those spots is a
     * corner or not. If it is places it in the corner, if it is not places it in the uppermost
     * leftmost cell. If there are two corners, places the card in the uppermost leftmost corner. If
     * there are no corners, places the card in the uppermost leftmost grid cell. The card closest to
     * index 0 that satisfies the greatest number of card flips will be played in the found position.


CHANGES FROM PREVIOUS WORK (HW5):
    - addition of public method in GraphicalView class to get the GridPanel and layout panels
    - removed all mouse related events from GraphicalView class and it's sub classes
    - and moved them to controller
    - Model added listeners and notification to all listeners
    - Added showing error messages to view interface

NEW IN THIS HOMEWORK:
    - Incorporation of a Features interface
        - Represents features that the controller manages to interact with the game. These methods
          permit players to perform features of the game through actions like selecting a card at a
          specific index or selecting a grid cell at a specific row and column.
    - Incorporation of an AIPLayerListener interface
        - Represents a listener for AI players in the Three Trios game. The listener is responsible
          for performing the AI player's next turn after the previous player plays. This turn is
           triggered by the model.
    - Incorporation of a ThreeTriosModelListener interface
        - Represents an interface for updating the view when a card has been played to the grid in
          the model. Whenever the model state changes, listeners will notify classes that implement
          this interface and perform an action accordingly.
    - Incorporation of a ThreeTriosController class
        - The controller class for a Three Trios game. Analyzes and reads user interactions and
          generates an output accordingly to the input. Manages communications between the model,
          view, and inputs.
    - Incorporated an AIPlayer class
        - Represents an AI-controlled player in the Three Trios game. The AI player works the same
          as a normal player, but has a specific strategy associated with it to perform its moves
          based off of when it is their turn. This is not user-controlled. An AI player is
          controlled by the computer.



CHANGES FOR PART 3:  (EXPLAIN TOO!!!!!)
    - Fixed the strategy-transcript.txt to include a visual of what the grid would look like before
      and after playing the strategy
    - Adds java docs to the ReadonlyThreeTriosModel interface
    - Added the method addViewListener in the ThreeTriosModel interface
        - Adds a listener to prepare the view to be updated when the model notifies it to.
    - Added the method addAITurnListener in the ThreeTriosModel interface
        - Adds a listener to prepare the AI to commence its turn when the model notifies it to.
    - Added private methods alertViewListener and alertAITurnListener to notify when to provoke the
      methods within our listeners
    - Added findWinningPlayerScore method to the ThreeTriosModel interface
        - Returns the winning player's score.
    - Added a private method to support the findWinningPlayerScore method
    - Changes some source organization around. It is reflected above.



HW 8 INFORMATION:

COMMAND-LINE OPTIONS:
    THIS REQUIRES 2 OF THE SPECIFIED ARGUMENTS:
        - "human" --> allows for an actual player to play the game
        - "strategy1" --> allows for an AI player to play the game using strategy 1
        - "strategy2" --> allows for an AI player to play the game using strategy 2
        - "strategy1and2" --> allows for an AI player to play the game using strategy 1 and 2
            combined

CHANGES IN VEW FOR CUSTOMERS - HW8:
    - There were no changes in my view for our customers.

REMOVED TO FIT UNDER LIMIT:
    - entire cardconfigs package in src/ and test/ folders
    - entire gridconfigs package in src/ and test/ folders
    - removed the entire test folder to fit under limit; tests can be found in previous submission


PROVIDER CODE REVIEW:
    - Done within the PeerReview.txt file


EXPERIENCE REVIEW:
    - Done within the SelfReview.txt file


WHICH FEATURES WE WERE ABLE TO GET WORKING AND WHICH DO NOT:
    WORKING:
        - The main features that work are the views appearing and card selection. In addition, the
        playToGrid methods work as expected for the red player (main view) and both views update as
        expected. In addition, all game functionality works perfectly fine except the listeners to
        manage playing to grid for the provider's view which is explained more below. This is a
        one-sided problem.

    NOT WORKING:
        - The feature we could not get working is the playing to grid and battling methods for
        specifically the provider's view (this works perfectly fine for the main view). This is
        primarily due to setting up listeners in different classes. With the idea of a mouse click
        being an input, we set up our mouse click listeners in the controller. On the contrary, our
        providers set it up in their view. With this being said, it became impossible to adapt the
        playToGrid functionality as we had no way to communicate the selection information from
        the view to the controller. Without the extra listeners our provider's had, there is no way
        for us to adapt the playToGrid functionality and send the required information from the view
        to the controller. If you consult the console, you can still tell that the grid cell clicks
        and card cell clicks are being processed, but the playing to the grid function is not being
        processed.


ADDITIONS TO HOMEWORK 9 EXTRA CREDIT:
    - Created "battle rules" which are rules for battling that only occur during prior to the combo
      step. Rules take priority over other types of battling (ex. strategies).
        - Associated files: BattleRules interface, NormalBattleRule class, PlusBattleRule
          class, SameBattleRule class
    - Created "battle strategies" which are strategies for battling that can occur prior to the
      combo step and after the combo step. Rules take priority over strategies, but if a rule fails
      then the strategy can take over.
        - Associated files: BattleStrategies interface, FallenAceBattleStrategy class,
          NormalBattleStrategy class, ReverseBattleStrategy class, ReverseFallenAceBattleStrategy
          class
    - Modified all the AI strategies to account for the current game's running rule and strategy
      to dictate the best move
    - Added a toggleable hints button that will tell the player how many cards they can flip if
      they put their currently selected card there (only if the button is toggled!). This accounts
      for the game's current running rule and strategy as well.
    - The ability for the battle rules and battle strategies to work in unison with each other.

WHAT I HAD TO DELETE FROM MY CODE TO PROCESS THE SUBMISSION (HOMEWORK 9):
    - SRC:
        - all card configuration files
        - all grid configuration files
        - all provider code/adapter files from HW8
    - TEST:
        - all card configuration files
        - all grid configuration files