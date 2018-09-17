package Bookshop

import com.rabbitmq.client._

class Receiver(BookList: scala.collection.mutable.Map[String, BookDetailsClass], connectionClass: ConnectionClass, sender: Sender) {


  val QUEUE_NAME: String = "Client2Server"
  connectionClass.channel.queueDeclare(QUEUE_NAME, false, false, false, null)
  val consumer: DefaultConsumer = new DefaultConsumer(connectionClass.channel) {

    override def handleDelivery(consumerTag: String,
                                envelope: Envelope,
                                properties: AMQP.BasicProperties,
                                body: Array[Byte]) {

      new ReceiveHandler(BookList, new String(body, "UTF-8"), sender)
    }
  }
  connectionClass.channel.basicConsume(QUEUE_NAME, true, consumer)
}
