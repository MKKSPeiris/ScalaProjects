package Bookshop

import com.rabbitmq.client.{Channel, Connection, ConnectionFactory}

class ConnectionClass {

  val factory = new ConnectionFactory()
  factory.setHost("localhost")
  val connection: Connection = factory.newConnection()
  val channel: Channel = connection.createChannel()
}
