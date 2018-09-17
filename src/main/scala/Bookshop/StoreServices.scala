package Bookshop

import java.net.InetSocketAddress
import com.sun.net.httpserver.HttpServer

case class BookDetailsClass(writer: String, price: Double)

object StoreServices {
  var bookList: scala.collection.mutable.Map[String, BookDetailsClass] = scala.collection.mutable.Map[String, BookDetailsClass]()

  def main(args: Array[String]): Unit = {

    bookList = scala.collection.mutable.Map("Harry Potter" -> BookDetailsClass("Rowling", 10),
      "War And Peace" -> BookDetailsClass("Leo Tolstoy", 100),
      "Hamlet" -> BookDetailsClass("Shakespeare", 150),
      "Lliad" -> BookDetailsClass("Homer", 95.5),
      "Thor" -> BookDetailsClass("Wayne Smith", 20))

    val connectionClass = new ConnectionClass()
    val sender = new Sender(connectionClass)
    new Receiver(bookList, connectionClass, sender)

    val server = HttpServer.create(new InetSocketAddress(8001), 0)
    val handler = new ServiceHandler(bookList)
    server.createContext("/allbooks", new handler.GetAllBooksHandler)
    server.createContext("/addbook", new handler.AddHandler)
    server.createContext("/details", new handler.GetHandler)
    server.createContext("/remove", new handler.RemoveHandler)
    server.setExecutor(null)
    server.start()

  }
}
