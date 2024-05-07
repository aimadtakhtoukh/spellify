package fr.iai.spellify
package game.obfuscation

import game.model.ManaGuess

import org.scalatest.wordspec.AnyWordSpec

class ManaObfuscatorTest extends AnyWordSpec {

  "ManaObfuscatorTest" when {
    "no guesses are made" when {

      val manaObfuscator = ManaObfuscator(guesses = Nil)
      "a symbol between accolades is passed" should {

        val original = "{B}"
        "obfuscate" in {
          val expected = "*"
          val result = manaObfuscator.obfuscate(original)
          assert(result === expected)
        }
      }

      "no symbol is passed" should {
        val original = "B"
        "not obfuscate" in {
          val result = manaObfuscator.obfuscate(original)
          assert(result === original)
        }
      }
    }

    "a correct guess is made" when {
      val manaObfuscator = ManaObfuscator(guesses = ManaGuess("R") :: Nil)
      "a related symbol between accolades is passed" should {
        val original = "{R}"
        "reveal" in {
          val expected = "{R}"
          val result = manaObfuscator.obfuscate(original)
          assert(result === expected)
        }
      }

      "an unrelated symbol between accolades is passed" should {
        val original = "{B}"
        "obfuscate" in {
          val expected = "*"
          val result = manaObfuscator.obfuscate(original)
          assert(result === expected)
        }
      }

      "no symbol is passed" should {
        val original = "B"
        "not obfuscate" in {
          val result = manaObfuscator.obfuscate(original)
          assert(result === original)
        }
      }
    }
  }
}
