package com.jp.ka2kama.todolist

import cats.effect.*
import cats.syntax.all.*
import org.http4s.*
import org.http4s.dsl.Http4sDsl
import org.http4s.headers.*
import org.http4s.syntax.all.*

object TodoListRoutes:
    def routeRoutes[F[_] : Sync]: HttpRoutes[F] =
        val dsl = new Http4sDsl[F] {}
        import dsl.*
        HttpRoutes.of[F] {
            case GET -> Root =>
                PermanentRedirect(Location(uri"/hello/"))

            case _ -> Root =>
                /* The default route result is NotFound.
                 * Sometimes MethodNotAllowed is more appropriate. */
                MethodNotAllowed(Allow(GET))
        }

    def jokeRoutes[F[_] : Sync](J: Jokes[F]): HttpRoutes[F] =
        val dsl = new Http4sDsl[F] {}
        import dsl.*
        HttpRoutes.of[F] { case GET -> Root / "joke" =>
            for
                joke <- J.get
                resp <- Ok(joke)
            yield resp
        }

    def helloWorldRoutes[F[_] : Sync](H: HelloWorld[F]): HttpRoutes[F] =
        val dsl = new Http4sDsl[F] {}
        import dsl.*
        HttpRoutes.of[F] { case GET -> Root / "hello" / name =>
            for
                greeting <- H.hello(HelloWorld.Name(name))
                resp <- Ok(greeting)
            yield resp
        }
