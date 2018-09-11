package Bookshop

import com.rabbitmq.client.ConnectionFactory

class SendHandler(BookList: scala.collection.mutable.Map[String, BookDetailsClass]) {

  var Controllers = new Controllers(BookList)

  val QUEUE_NAME: String = "Server2Client"
  val factory = new ConnectionFactory()
  factory.setHost("localhost")
  val connection = factory.newConnection()
  val channel = connection.createChannel()
  channel.queueDeclare(QUEUE_NAME, false, false, false, null)
  var message: String = ""

  def Allbooks() {
    var BookSet: Set[String] = Controllers.GetAllBooks();
    BookSet.foreach(book => message = book + " | " + message)
    MessageSender()
  }

  def BookDetails(BookName: String): Unit = {
    var BookDetails: List[Any] = Controllers.GetBookDetail(BookName);
    message = f"BookName: ${BookDetails.head} | Author: ${BookDetails(1)} | Price: ${BookDetails(2)}"
    MessageSender()
  }

  def BookAdd(BookName: String, Author: String, Price: String): Unit = {
    try {
      Controllers.AddBook(BookName, Author, Price.toDouble)
      message = "Successfully Added"
    }
    catch {
      case _: Exception => message = "Error : Cant add book"
    }
    MessageSender()
  }

  def BookRemove(BookName: String): Unit = {
    Controllers.RemoveBook(BookName);
    message = "Successfully Removed"
    MessageSender()
  }

  def MessageSender(): Unit = {
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"))
    println(" [x] Sent '" + message + "'")
    channel.close()
    connection.close()
  }
}
