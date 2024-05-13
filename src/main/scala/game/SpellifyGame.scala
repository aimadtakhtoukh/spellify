package fr.iai.spellify
package game

import game.obfuscation.{LetterObfuscator, ManaObfuscator}
import game.model.*

case class SpellifyGame(solution: MagicCard, guesses: List[Guess], maximumGuesses: Int) {
  private val manaObfuscator = ManaObfuscator(guesses = guesses.collect { case mg: ManaGuess => mg })
  private val letterObfuscator = LetterObfuscator(guesses = guesses.collect { case cg: LetterGuess => cg })

  extension(target: String)
    private def obfuscateMana : String = manaObfuscator.obfuscate(target)
    private def obfuscateLetters : String = letterObfuscator.obfuscate(target)

  private def obfuscateString(target: String): String =
    target
      .obfuscateMana
      .obfuscateLetters

  def obfuscatedCard(): MagicCard = {
    MagicCard(
      name = obfuscateString(solution.name),
      typeList = obfuscateString(solution.typeList),
      text = obfuscateString(solution.text)
    )
  }

  def add(g: Guess): SpellifyGame = this.copy(guesses = guesses :+ g)

  private def aNameGuessIsRight = {
    val nameGuesses: List[String] = guesses.collect { case ng: NameGuess => ng }.map(_.name.toLowerCase())
    val searchedName: String = solution.name.toLowerCase()
    nameGuesses.contains(searchedName)
  }

  def gameState(): GameState =
    if (aNameGuessIsRight) Win
    else if (guesses.size > maximumGuesses) Loss
    else if (guesses.size == maximumGuesses) FinalGuess
    else Going

}
