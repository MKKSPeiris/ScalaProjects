package Bookshop

import com.rabbitmq.client._

class ReceiveHandler(BookList: scala.collection.mutable.Map[String, BookDetailsClass]) {

  val factory = new ConnectionFactory()
  factory.setHost("localhost")
  val connection = factory.newConnection()
  val channel = connection.createChannel()
  val QUEUE_NAME2: String = "Client2Server"
  channel.queueDeclare(QUEUE_NAME2, false, false, false, null)
  val consumer = new DefaultConsumer(channel) {

    override def handleDelivery(consumerTag: String,
                                envelope: Envelope,
                                properties: AMQP.BasicProperties,
                                body: Array[Byte]) {
      var message = new String(body, "UTF-8")
      val Sender =new SenderHandler(BookList);
      if(message == "AllBooks"){
        println(" [x] Received '" + message + "'")
        Sender.Allbooks()
      }
      else if(message.contains("BookDetail")){
        println(" [x] Received '" + message + "'")
        var DetailList :List[String] = message.split("\\+").toList
        Sender.BookDetails(DetailList(1))
      }
      else if(message.contains("BookAdd")){
        println(" [x] Received '" + message + "'")
        var DetailList :List[String] = message.split("\\+").toList
        Sender.BookAdd(DetailList(1),DetailList(2),DetailList(3))
      }
      else if(message.contains("BookRemove")){
        println(" [x] Received '" + message + "'")
        var DetailList :List[String] = message.split("\\+").toList
        Sender.BookRemove(DetailList(1))
      }
    }
  }
  channel.basicConsume(QUEUE_NAME2, true, consumer)
}
