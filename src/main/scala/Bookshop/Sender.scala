package Bookshop

import com.rabbitmq.client.ConnectionFactory

class Sender() {

  val QUEUE_NAME: String = "Server2Client"
  val factory = new ConnectionFactory()
  factory.setHost("localhost")
  val connection = factory.newConnection()
  val channel = connection.createChannel()
  channel.queueDeclare(QUEUE_NAME, false, false, false, null)

  def messageSender(message: String): Unit = {
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"))
    println(" [x] Sent '" + message + "'")
    //channel.close()
    //connection.close()
  }
}
