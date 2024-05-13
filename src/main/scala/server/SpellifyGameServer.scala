package fr.iai.spellify
package server

import game.model.MagicCard
import server.SpellifyGameService.*
import server.jsonmodel.{JsonGame, JsonGameHistory}

import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*
import zio.*
import zio.http.*
import zio.http.Method.*
import zio.http.codec.PathCodec
import zio.http.codec.PathCodec.*

object SpellifyGameServer extends ZIOAppDefault {
  private val routes: List[Route[Any, Nothing]] = List(
    GET / "spellify" / "new" ->
      handler(Response.json(JsonGame.fromModel(newGame()).asJson.noSpaces)),
    GET / "spellify" / "guess" ->
      handler {
        (req: Request) =>
          (
            for {
              body <- req.body.asString(Charsets.Utf8)
              parsed <- ZIO.fromEither(parse(body))
              history <- ZIO.fromEither(parsed.as[JsonGameHistory])
              response = Response.json(JsonGame.fromModel(fromHistory(history)).asJson.noSpaces)
            } yield response
          ).orElseFail(Response.badRequest)

      }
  )

  private val app = Routes.fromIterable(routes).toHttpApp

  override val run: ZIO[Any, Throwable, Nothing] = Server.serve(app).provide(Server.default)
}
