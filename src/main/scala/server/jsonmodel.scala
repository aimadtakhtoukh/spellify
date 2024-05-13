package fr.iai.spellify
package server

import game.SpellifyGame
import game.model.{GameState, Guess, MagicCard}

object jsonmodel {

  case class JsonGame(
                         solution: String,
                         obfuscated: MagicCard,
                         guesses: List[Guess],
                         maximumGuesses : Int,
                         gameState: GameState
                         )

  object JsonGame {
    def fromModel(model : SpellifyGame): JsonGame =
      import model.*
      JsonGame(solution.name, model.obfuscatedCard(), guesses, maximumGuesses, model.gameState())
  }

  case class JsonGameHistory(
                              solution: String,
                              guesses: List[Guess],
                              maximumGuesses : Int
                            ) {
    def toModel: SpellifyGame =
      SpellifyGame(
        solution = MagicCard(
          name = "Door to Nothingness",
          typeList = "Artifact",
          text = "Door to Nothingness enters the battlefield tapped.\n{W}{W}{U}{U}{B}{B}{R}{R}{G}{G}, {T}, Sacrifice Door to Nothingness: Target player loses the game."
        ),
        guesses = guesses,
        maximumGuesses = maximumGuesses
      )
  }



}
