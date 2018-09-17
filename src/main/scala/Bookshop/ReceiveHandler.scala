package Bookshop

class ReceiveHandler(bookList: scala.collection.mutable.Map[String, BookDetailsClass], message: String,sender: Sender) {

  val sendHandler = new SendHandler(bookList,sender)
  if (message == "AllBooks") {
    println(" [x] Received '" + message + "'")
    sendHandler.allBooks()
  }
  else if (message.contains("BookDetail")) {
    println(" [x] Received '" + message + "'")
    val detailList: List[String] = message.split("\\+").toList
    sendHandler.bookDetails(detailList(1))
  }
  else if (message.contains("BookAdd")) {
    println(" [x] Received '" + message + "'")
    val detailList: List[String] = message.split("\\+").toList
    sendHandler.bookAdd(detailList(1), detailList(2), detailList(3))
  }
  else if (message.contains("BookRemove")) {
    println(" [x] Received '" + message + "'")
    val detailList: List[String] = message.split("\\+").toList
    sendHandler.bookRemove(detailList(1))
  }
}
