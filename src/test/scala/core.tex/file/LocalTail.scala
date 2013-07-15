package scala.pimps.file

import java.nio.file.{Paths, Path}

object LocalTail extends App {
  new Tail(Paths.get("src/test/resources/scala/core/tex/file/some.log"), (lines) => println("lines: " + lines), () => println("file not found"))
}
