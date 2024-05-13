package fr.iai.spellify
package game

import cats.syntax.functor._
import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._
import io.circe.syntax._

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

  object Guess {
    implicit val decodeGuess: Decoder[Guess] = 
      Decoder[LetterGuess].widen or Decoder[ManaGuess].widen or Decoder[NameGuess].widen

    implicit val encodeGuess: Encoder[Guess] = Encoder.instance {
      case g : LetterGuess => g.asJson
      case g : ManaGuess => g.asJson
      case g : NameGuess => g.asJson
    }
  }

  sealed trait GameState
  case object Going extends GameState
  case object Win extends GameState
  case object Loss extends GameState
  case object FinalGuess extends GameState

  object GameState {
    implicit val decodeGameState: Decoder[GameState] = Decoder[String].emap {
      case "going" => Right(Going)
      case "win" => Right(Win)
      case "loss" => Right(Loss)
      case "final_guess" => Right(FinalGuess)
      case other => Left(s"Invalid Game State : $other")
    }

    implicit val encodeGameState: Encoder[GameState] = Encoder[String].contramap {
      case Going => "going"
      case Win => "win"
      case Loss => "loss"
      case FinalGuess => "final_guess"
    }
  }
}
