package fr.iai.spellify
package game.obfuscation

import game.model.LetterGuess

class LetterObfuscator(guesses: List[LetterGuess]) extends Obfuscator {
  private val obfuscatedLetterPattern = "[A-Za-z0-9](?![^{]*\\})"
  private val obfuscationLetterCharacter = "_"

  private def guessedLetters: List[Char] =
    guesses.map(_.letter).flatMap {
      case c if c.isLetter => List(c.toLower, c.toUpper)
      case c => List(c)
    }

  private def guessedLettersExclusionPattern =
    guessedLetters match
      case Nil => ""
      case charGuesses => charGuesses.mkString("(?![", "", "])")

  override def obfuscate(original: String): String =
    original
      .replaceAll(s"$guessedLettersExclusionPattern$obfuscatedLetterPattern", obfuscationLetterCharacter)
}
