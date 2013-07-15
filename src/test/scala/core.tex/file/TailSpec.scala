package scala.pimps.file

import org.scalatest.WordSpec
import scala.collection.mutable.ListBuffer
import java.nio.file.Files
import java.nio.charset.StandardCharsets
import scala.collection.JavaConversions._

class TailSpec extends WordSpec {
  "By default, Tail shows us all previous lines of the target file" in {
    val collector = ListBuffer[List[String]]()
    val tempFile = Files.createTempFile("some" + Math.random(), "txt")
    Files.write(tempFile, List("Line1", "Line2", "Line3"), StandardCharsets.UTF_8)

    new Thread(new Runnable() {
      def run() {
        new Tail(tempFile, (lines) => collector += lines, () => println("file not found"))
      }
    }).start()

    Thread.sleep(1500)
    println("collector: " + collector)
    assert(collector(0) === List("Line3", "Line2", "Line1"))
  }

  "Tail doesn't call the callback with an empty list of lines" in {
    // It did until I fixed it
  }

  "Tail reverses the order of lines read" in {
    // we probably shouldn't reverse them
  }

  "Works when the file is truncated" in {
    //
  }
}