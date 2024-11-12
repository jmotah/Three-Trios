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

Card Related Class/Info:
    Includes CardCompass enum, CardNumbers enum, Cards interface, and PlayingCard class
    [src folder -> cs3500.threetrios.cards package]

Controller Related Class/Info:
    Currently empty; prepared for future use
    [src folder -> cs3500.threetrios.controller package]

File Reader Related Class/Info:
    Includes ConfigurationReader interface, CardReader class, and GridReader class
    [src folder -> cs3500.threetrios.filereader package]

Grid Related Class/Info:
    Includes Grid interface, GridTile class
    [src folder -> cs3500.threetrios.grid package]

Model Related Class/Info:
    Includes CellType enum, GamePhase enum, GameState enum, ThreeTriosModel interface,
    playervsplayer package, and playervscomputer package
    [src folder -> cs3500.threetrios.model package]

    playervsplayer package:
    Includes PlayerPlayerModel class
    [cs3500.threetrios.model.playervsplayer package]

    playervscomputer package:
    Currently empty; prepared for future use
    [cs3500.threetrios.model.playervscomputer package]

Player Related Class/Info:
    Includes Player interface, PlayerColor enum, and HumanPlayer class
    [src folder ->  cs3500.threetrios.player package]

View Related Class/info
    Includes ThreeTriosView interface, TextualView Class
    [src folder -> cs3500.threetrios.view package]

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

Controller Testing Related Class/Info:
    Currently empty; prepared for future use
    [test folder -> cs3500.threetrios.controller package]

File Reader Testing Related Class/Info:
    Includes CardReaderTests class and GridReaderTests class
    [test folder -> cs3500.threetrios.filereader package]

Grid Testing Related Class/Info:
    Includes GridTileTests class
    [test folder -> cs3500.threetrios.grid package]

Model Testing Related Class/Info:
    Includes AbstractVariantModelTests class and PlayerPlayerModelTests class
    [test folder -> cs3500.threetrios.model package]

Player Testing Related Class/Info:
    Includes HumanPlayerTests class
    [test folder ->  cs3500.threetrios.player package]

View Testing Related Class/info
    Includes TextualViewTests class
    [test folder -> cs3500.threetrios.view package]

Card Testing Config Related Files/Info:
    Includes various card configuration files to be used for testing. More may be created, you
    are not limited to what is inside the package.
    [test folder -> cs3500.threetrios.cardconfigs package]

Grid Testing Config Related Files/Info:
    Includes various grid configuration files to be used for testing. More may be created, you
    are not limited to what is inside the package.
    [test folder -> cs3500.threetrios.gridconfigs package]


CHANGES FROM PREVIOUS WORK:
- Moved the TextualView class into a sub package called text
- Added view interface
- Added a get color method within the PlayerColor enum and a color associated to each PlayerColor
    within the enum
- Made fields private
- Refactored Source Organization to match MVC more