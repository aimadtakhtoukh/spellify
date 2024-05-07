package fr.iai.spellify
package game.obfuscation

import game.model.LetterGuess

import org.scalatest.wordspec.AnyWordSpec

class LetterObfuscatorTest extends AnyWordSpec {

  "LetterObfuscatorTest" when {
    "no guesses are made" when {
      val letterObfuscator = LetterObfuscator(guesses = Nil)
      "a letter is passed" should {
        val original = "a"
        "obfuscate" in {
          val expected = "_"
          val result = letterObfuscator.obfuscate(original)
          assert(result === expected)
        }
      }

      "a number is passed" should {
        val original = "0"
        "obfuscate" in {
          val expected = "_"
          val result = letterObfuscator.obfuscate(original)
          assert(result === expected)
        }
      }

      "another symbol is passed" should {
        val original = ","
        "do nothing" in {
          val expected = ","
          val result = letterObfuscator.obfuscate(original)
          assert(result === expected)
        }
      }

      "a symbol between accolades is passed" should {
        val original = "{B}"
        "do nothing" in {
          val expected = "{B}"
          val result = letterObfuscator.obfuscate(original)
          assert(result === expected)
        }
      }
    }

    "proper guesses are made" when {
      val letterObfuscator = LetterObfuscator(
        guesses = List(
          LetterGuess('A'),
          LetterGuess('0')
        )
      )

      "a letter is passed" should {
        val original = "a"
        "obfuscate" in {
          val expected = "a"
          val result = letterObfuscator.obfuscate(original)
          assert(result === expected)
        }
      }

      "a number is passed" should {
        val original = "0"
        "obfuscate" in {
          val expected = "0"
          val result = letterObfuscator.obfuscate(original)
          assert(result === expected)
        }
      }

      "another symbol is passed" should {
        val original = ","
        "do nothing" in {
          val expected = ","
          val result = letterObfuscator.obfuscate(original)
          assert(result === expected)
        }
      }

      "a symbol between accolades is passed" should {
        val original = "{B}"
        "do nothing" in {
          val expected = "{B}"
          val result = letterObfuscator.obfuscate(original)
          assert(result === expected)
        }
      }

    }
  }
}
