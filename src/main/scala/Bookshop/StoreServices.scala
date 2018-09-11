package Bookshop

import java.net.InetSocketAddress
import com.sun.net.httpserver.HttpServer

case class BookDetailsClass(Writer: String, Price: Double)

object StoreServices {
  var BookList: scala.collection.mutable.Map[String, BookDetailsClass] = scala.collection.mutable.Map[String, BookDetailsClass]()

  def main(args: Array[String]): Unit = {

    BookList = scala.collection.mutable.Map("Harry Potter" -> new BookDetailsClass("Rowling", 10),
      "War And Peace" -> new BookDetailsClass("Leo Tolstoy", 100),
      "Hamlet" -> new BookDetailsClass("Shakespeare", 150),
      "Lliad" -> new BookDetailsClass("Homer", 95.5),
      "Thor" -> new BookDetailsClass("Wayne Smith", 20))


    var RsvMsgHandler = new ReceiveHandler(BookList);

    val server = HttpServer.create(new InetSocketAddress(8001), 0)
    val Handler = new ServiceHandler(BookList);
    server.createContext("/allbooks", new Handler.GetAllBooksHandler)
    server.createContext("/addbook", new Handler.AddHandler)
    server.createContext("/details", new Handler.GetHandler)
    server.createContext("/remove", new Handler.RemoveHandler)
    server.setExecutor(null)
    server.start()

  }
}
