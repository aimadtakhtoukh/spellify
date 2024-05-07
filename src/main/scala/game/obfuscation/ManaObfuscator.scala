package fr.iai.spellify
package game.obfuscation

import game.model.ManaGuess

class ManaObfuscator(guesses: List[ManaGuess]) extends Obfuscator {
  private val obfuscatedManaPattern = "\\{.*?}"
  private val obfuscationManaCharacter = "*"

  private val guessedManaSymbols: List[String] =
    guesses.map(mana => s"\\{${mana.manaSymbol}")

  private def guessedManaSymbolsExclusionPattern: String =
    guessedManaSymbols match
      case Nil => ""
      case manaGuesses => manaGuesses.mkString("(?!", "|", ")")

  override def obfuscate(original: String): String =
    original
      .replaceAll(s"$guessedManaSymbolsExclusionPattern$obfuscatedManaPattern", obfuscationManaCharacter)
}
