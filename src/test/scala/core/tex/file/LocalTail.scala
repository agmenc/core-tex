package core.tex.file

import java.nio.file.{Paths, Path}

object LocalTail extends App {
  new Tail(Paths.get("src/test/resources/core/tex/file/some.log"), (lines) => println("lines: " + lines), () => println("file not found"))
}