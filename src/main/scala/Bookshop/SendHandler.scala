package Bookshop

class SendHandler(bookList: scala.collection.mutable.Map[String, BookDetailsClass]) {

  val controllers = new Controllers(bookList)
  val sender = new Sender()

  def allBooks() {
    val bookSet: Set[String] = controllers.getAllBooks()
    sender.messageSender(bookSet.mkString(" | "))
  }

  def bookDetails(BookName: String): Unit = {
    try {
      val bookDetails: List[Any] = controllers.getBookDetail(BookName)
      sender.messageSender(f"BookName: ${bookDetails.head} | Author: ${bookDetails(1)} | Price: ${bookDetails(2)}")
    }
    catch {
      case _: Exception => sender.messageSender("Cant find details of this book")
    }

  }

  def bookAdd(BookName: String, Author: String, Price: String): Unit = {
    try {
      controllers.addBook(BookName, Author, Price.toDouble)
      sender.messageSender("Successfully Added")
    }
    catch {
      case _: Exception => sender.messageSender("Error : Cant add this book")
    }
  }

  def bookRemove(BookName: String): Unit = {
    try {
      controllers.removeBook(BookName)
      sender.messageSender("Successfully Removed")
    }
    catch {
      case _: Exception => sender.messageSender("Cant Remove this book")
    }
  }
}
