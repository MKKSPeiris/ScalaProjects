package Bookshop

import com.rabbitmq.client._

class Receiver(BookList: scala.collection.mutable.Map[String, BookDetailsClass]) {

  val factory = new ConnectionFactory()
  factory.setHost("localhost")
  val connection: Connection = factory.newConnection()
  val channel: Channel = connection.createChannel()
  val QUEUE_NAME: String = "Client2Server"
  channel.queueDeclare(QUEUE_NAME, false, false, false, null)
  val consumer: DefaultConsumer = new DefaultConsumer(channel) {

    override def handleDelivery(consumerTag: String,
                                envelope: Envelope,
                                properties: AMQP.BasicProperties,
                                body: Array[Byte]) {

      new ReceiveHandler(BookList, new String(body, "UTF-8"))
    }
  }
  channel.basicConsume(QUEUE_NAME, true, consumer)
}
