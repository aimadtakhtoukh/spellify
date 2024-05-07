package fr.iai.spellify
package game

import game.model.{LetterGuess, FinalGuess, Going, Guess, Loss, MagicCard, ManaGuess, NameGuess, Win}
import game.obfuscation.{LetterObfuscator, ManaObfuscator}

import org.scalatest.wordspec.AnyWordSpec

class SpellifyGameTest extends AnyWordSpec {

  val defaultCard: MagicCard = MagicCard(
    name = "NameTest",
    typeList = "TypeTest",
    text = "TextTest{R}"
  )

  val defaultGame : SpellifyGame = SpellifyGame(defaultCard, Nil, 13)

  val defaultCharacterGuess: LetterGuess = LetterGuess('T')

  "obfuscated" when {
    extension (target: String)
      private def mockObfuscation(characterGuesses: List[LetterGuess] = Nil, manaGuesses: List[ManaGuess] = Nil) =
        val letterObfuscator = LetterObfuscator(characterGuesses)
        val manaObfuscator = ManaObfuscator(manaGuesses)
        letterObfuscator.obfuscate(manaObfuscator.obfuscate(target))

    "no guesses are made" should {
      val guesses = Nil
      "obfuscate the card" in {
        val result = defaultGame.obfuscated()
        assert(result.name === defaultCard.name.mockObfuscation())
        assert(result.typeList === defaultCard.typeList.mockObfuscation())
        assert(result.text === defaultCard.text.mockObfuscation())
      }
    }

    "a character guess is made" should {
      val characterGuess = defaultCharacterGuess :: Nil

      "obfuscate the card" in {
        val result = defaultGame.copy(guesses = characterGuess).obfuscated()
        assert(result.name === defaultCard.name.mockObfuscation(characterGuesses = characterGuess))
        assert(result.typeList === defaultCard.typeList.mockObfuscation(characterGuesses = characterGuess))
        assert(result.text === defaultCard.text.mockObfuscation(characterGuesses = characterGuess))
      }
    }

    "a mana guess is made" should {
      val manaGuess = ManaGuess("R") :: Nil

      "obfuscate the card" in {
        val result = defaultGame.copy(guesses = manaGuess).obfuscated()
        assert(result.name === defaultCard.name.mockObfuscation(manaGuesses = manaGuess))
        assert(result.typeList === defaultCard.typeList.mockObfuscation(manaGuesses = manaGuess))
        assert(result.text === defaultCard.text.mockObfuscation(manaGuesses = manaGuess))
      }
    }
  }

  "add" should {
    "add a new guess on a game" in {
      val guess = LetterGuess('T')
      val game = defaultGame.add(guess)
      assert(game.guesses.nonEmpty)
      assert(game.guesses.head === guess)
    }
  }

  "gameState" when {
    "there is a correct name guess" should {
      "win" in {
        val game = defaultGame.copy(guesses = NameGuess(defaultCard.name) :: Nil)
        assert(game.gameState() === Win)
      }
    }

    "there is an incorrect name guess" when {
      val incorrectNameGuess = NameGuess("No")
      "there are less guesses than tries" should {
        "keep going" in {
          val game = defaultGame.copy(guesses = incorrectNameGuess :: Nil)
          assert(game.gameState() === Going)
        }
      }

      "there are as many guesses as tries" should {
        "ask for a final guess" in {
          val game = defaultGame.copy(guesses = incorrectNameGuess :: Nil, maximumGuesses = 1)
          assert(game.gameState() === FinalGuess)
        }
      }

      "there are more guesses than tries" should {
        "lose" in {
          val game = defaultGame.copy(guesses = incorrectNameGuess :: Nil, maximumGuesses = 0)
          assert(game.gameState() === Loss)
        }
      }
    }

    "there are no name guesses" should {
      "there are less guesses than tries" should {
        "keep going" in {
          val game = defaultGame.copy(guesses = defaultCharacterGuess :: Nil)
          assert(game.gameState() === Going)
        }
      }

      "there are as many guesses as tries" should {
        "ask for a final guess" in {
          val game = defaultGame.copy(guesses = defaultCharacterGuess :: Nil, maximumGuesses = 1)
          assert(game.gameState() === FinalGuess)
        }
      }

      "there are more guesses than tries" should {
        "lose" in {
          val game = defaultGame.copy(guesses = defaultCharacterGuess :: Nil, maximumGuesses = 0)
          assert(game.gameState() === Loss)
        }
      }
    }
  }
}
