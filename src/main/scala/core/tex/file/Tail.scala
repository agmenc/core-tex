package core.tex.file

import java.nio.file.{Files, Path}
import java.nio.charset.StandardCharsets
import scala.annotation.tailrec

/** The simplest possible low-level class for tailing a File, as per the Unix command line.
 *
 *  This implementation blocks waiting for new lines, so should be wrapped in, for example, an `Actor`.
 *
 * @param path The java.nio.file.Path of the file to be tailed
 * @param sideEffect Callback to receive lines as they are appended to the file
 * @param fileTruncated Callback to receive notification that the underlying file has been truncated, perhaps due to log rolling
 */
class Tail(path: Path, sideEffect: (List[String]) => Any, fileTruncated: () => Unit, pollingPeriodMillis: Int = 250) {
  val reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)
  val file = path.toFile

  @tailrec
  final def tail(lines: List[String], previousSize: Long): Unit = {
    Thread.sleep(pollingPeriodMillis)
    val PreviousSize = previousSize

    (file.length, reader.readLine()) match {
      case (size, line: String) => tail(line :: lines, size)
      case (PreviousSize, null) => if (!lines.isEmpty) sideEffect(lines); tail(Nil, previousSize)
      case (size, null) => fileTruncated()
    }
  }

  tail(Nil, file.length)
}