package fr.iai.spellify
package server

import game.SpellifyGame
import game.model.{Guess, MagicCard}
import server.jsonmodel.JsonGameHistory

object SpellifyGameService {

  def newGame(
             solution: MagicCard = MagicCard(
               name = "Door to Nothingness",
               typeList = "Artifact",
               text = "Door to Nothingness enters the battlefield tapped.\n{W}{W}{U}{U}{B}{B}{R}{R}{G}{G}, {T}, Sacrifice Door to Nothingness: Target player loses the game."
             ),
             guesses: List[Guess] = Nil,
             maximumGuesses: Int = 13
             ): SpellifyGame =
    SpellifyGame(
      solution,
      guesses,
      maximumGuesses
    )
    
  def fromHistory(history: JsonGameHistory) : SpellifyGame =
    history.toModel

}
