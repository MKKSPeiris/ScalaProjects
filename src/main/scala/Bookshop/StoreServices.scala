package Bookshop

import java.io.IOException
import java.net.InetSocketAddress

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.util
import java.util.EmptyStackException

case class BookDetailsClass(Writer:String,Price:Double)

object StoreServices {
  var BookList:Map[String,BookDetailsClass] =Map[String,BookDetailsClass]()

  def main(args: Array[String]): Unit = {

    BookList = Map("Harry Potter"->new BookDetailsClass("Rowling",10),
      "War And Peace"->new BookDetailsClass("Leo Tolstoy",100),
      "Hamlet"->new BookDetailsClass("Shakespeare",150),
      "Lliad"->new BookDetailsClass("Homer",95.5),
      "Thor"->new BookDetailsClass("Wayne Smith",20))

    val server = HttpServer.create(new InetSocketAddress(8001), 0)
    val Handler = new ServiceHandler(BookList);
    server.createContext("/allbooks", new Handler.GetAllBooksHandler
    server.createContext("/addbook" ,new Handler.AddHandler)
    server.createContext("/details",new Handler.GetHandler)
    server.createContext("/remove",new Handler.RemoveHandler)
    server.setExecutor(null) // creates a default executor
    server.start()


    }

  /*def AddBook(BookName:String,Writer:String,BookPrice:Double): Unit ={
    val BookDetailTemp = new BookDetailsClass(Writer,BookPrice)
    BookList += (BookName->BookDetailTemp)
  }

  def RemoveBook(BookName:String): Unit ={
      if (BookList(BookName) != null) {BookList -= BookName}
      else throw new EmptyStackException
  }

  def GetBookDetail(BookName:String): List[Any]={
    val book = BookList(BookName)
    val list = List(BookName,book.Writer,book.Price)
    list
  }

  def GetAllBooks(): String ={
    var booklist :String = ""
    BookList.keys.foreach{key =>
      booklist= key + "<br/>" +booklist
    }
    booklist
  }
*/

}
