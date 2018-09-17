package Bookshop

class Sender(connectionClass: ConnectionClass) {

  val QUEUE_NAME: String = "Server2Client"
  connectionClass.channel.queueDeclare(QUEUE_NAME, false, false, false, null)

  def messageSender(message: String): Unit = {
    connectionClass.channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"))
    println(" [x] Sent '" + message + "'")
  }
}
