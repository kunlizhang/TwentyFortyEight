=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays: I used 2D arrays to represent the game board of 2048. The 2D array
     contains a Tile class. In order to manipulate the tile, I use the methods
     up, down, left, right. This is to ensure encapsulation.

  2. File I/O: I used two .txt files here in order to save game states. The first
     is used to save the board state, which occurs when you press save. This writes
     a file of your entire game history into the save file, so that when you press
     open save, the game state is restored, including the score at that point and
     before that point. The second saving allows the user to save their score upon
     losing or winning the game. The top 5 scores can then be displayed.

  3. Collections: I used collections to store existing game states to
     record because this will be a list that will constantly grow in size. This
     allows me to implement the "undo" functionality, which cannot be done with
     any other data structure. This can also keep track of the total number of
     moves made so far.

  4. JUnit testable component: I have written a number of test cases testing the core
     functionality of the game, as well as the behaviour of tiles. Because the game
     is built on a core logic component this means that I can call functions like
     left() in order to simulate a left click.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  There are 3 main classes: Tile.java, TwentyFortyEight.java, and GameBoard.java.

  Tile.java - Handles the tiles in the game. It stores an integer, and checks if the
  values are valid. It also keeps track of whether it has combined yet during a move.

  TwentyFortyEight.java - This handles the core logic of the game. It contains all the
  methods for moving the board, combining tiles, saving the game, keeping track of the
  score, spawning the tiles, resetting the game and more.

  GameBoard.java - This handles the GUI side of the game. It makes the buttons, draws the
  board based on the game state of TwentyFortyEight.

  There is a GameTest.java class with test cases, and TileTest.java class with test cases
  for the behaviour of tiles.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  Yes. It was a challenge writing the code for combining tiles given that tiles couldn't
  combine more than once. However, by adding a Tile class I was able to overcome this
  challenge.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  Yes, as the game state is private to the GUI, and so the private state is well
  encapsulated. Furthermore, getter methods also provide deep copies, preventing aliasing
  and encapulation violations.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

For tile images and colours.
https://blog.wolfram.com/2014/05/09/2048/