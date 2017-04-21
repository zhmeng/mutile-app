/**
  * Created by ilkkzm on 17-4-21.
  */
object App {
  def main(args: Array[String]): Unit = {
    val s = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><xml><retcode>66230533</retcode><retmsg>file not exist</retmsg></xml>"
    println(s.getBytes.length)
  }
}
