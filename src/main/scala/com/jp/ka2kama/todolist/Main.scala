package com.jp.ka2kama.todolist

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple:
    val run: IO[Unit] = TodoListServer.run[IO]
