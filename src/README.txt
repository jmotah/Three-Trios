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

        model.startGame(cardConfig, gridConfig);
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
    Includes filereader package
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

    strategies package:
    Includes AbstractStrategies class, Strategies interface, Strategy1 class, Strategy1And2 class,
    and Strategy2 class
    [src folder -> cs3500.threetrios.model.strategies package]

View Related Class/info
    Includes ThreeTriosView interface, graphical package, and textual package
    [src folder -> cs3500.threetrios.view package]

    graphical package:
    Includes CardPanel class, GraphicalView class, GridLayoutPanel class, GridPanel class,
    PlayerCardsLayoutPanel class
    [src folder -> cs3500.threetrios.view.graphical package]

    textual package:
    Includes TextualView class
    [src folder -> cs3500.threetrios.view.textual package]


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
    Includes filereader package
    [test folder -> cs3500.threetrios.controller package]

    filereader package:
    Includes CardReaderTests and GridReaderTests
    [test folder -> cs3500.threetrios.controller.filereader package]


Grid Testing Config Related Files/Info:
    Includes various grid configuration files to be used for testing. More may be created, you
    are not limited to what is inside the package.
    [test folder -> cs3500.threetrios.gridconfigs package]


Model Testing Related Class/Info:
    Includes AbstractVariantModelTests class, PlayerPlayerModelTests class, PlayerComputerModelTests
    class, card package, grid package, and player package
    [test folder -> cs3500.threetrios.model package]

    card package:
    Includes PlayingCardTests class
    [test folder -> cs3500.threetrios.model.card package]

    grid package:
    Includes GridTileTests class
    [test folder -> cs3500.threetrios.model.grid package]

    player package:
    Includes PlayerTests class
    [test folder -> cs3500.threetrios.model.player package]

    strategies package:
        Includes MockModelEmpty class, MockModelStrategy1 class, MockStrategy1 class,
        MockStrategy2 class, Strategy1And2Tests class
        [test folder -> cs3500.threetrios.model.strategies package]


View Testing Related Class/info
    Includes TextualViewTests class
    [test folder -> cs3500.threetrios.view package]



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