package fr.iai.spellify
package game

object model {
  case class MagicCard(
    name: String,
    typeList: String,
    text: String
  )

  sealed trait Guess
  case class LetterGuess(letter: Char) extends Guess
  case class ManaGuess(manaSymbol: String) extends Guess
  case class NameGuess(name: String) extends Guess

  sealed trait PlayState
  case object Going extends PlayState
  case object Win extends PlayState
  case object Loss extends PlayState
  case object FinalGuess extends PlayState
}
