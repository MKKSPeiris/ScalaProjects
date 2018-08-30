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
    val server = HttpServer.create(new InetSocketAddress(8001), 0)
    server.createContext("/allbooks", new GetAllBooksHandler)
    server.createContext("/addbook" ,new AddHandler)
    server.createContext("/details",new GetHandler)
    server.createContext("/remove",new RemoveHandler)
    server.setExecutor(null) // creates a default executor
    server.start()

    BookList = Map("Harry Potter"->new BookDetailsClass("Rowling",10),
      "War And Peace"->new BookDetailsClass("Leo Tolstoy",100),
      "Hamlet"->new BookDetailsClass("Shakespeare",150),
      "Lliad"->new BookDetailsClass("Homer",95.5),
      "Thor"->new BookDetailsClass("Wayne Smith",20))
    }

  def AddBook(BookName:String,Writer:String,BookPrice:Double): Unit ={
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

  class GetAllBooksHandler extends HttpHandler {
    @throws[IOException]
    override def handle(t: HttpExchange): Unit = {
      val response = new StringBuilder
      response.append("<html><body>")
      response.append(GetAllBooks())
      response.append("</body></html>")
      writeResponse(t, response.toString)
    }
  }

  class RemoveHandler extends HttpHandler {
    @throws[IOException]
    override def handle(t: HttpExchange):Unit = {
      val response = new StringBuilder
      val parms = queryToMap(t.getRequestURI.getQuery)
      try {
        RemoveBook(parms.get("bookname"))
        response.append("<html><body>")
        response.append("<font color=\"Green\">Successfully removed </font>".format() + parms.get("bookname"))
        response.append("</body></html>")
        writeResponse(t, response.toString)
      }
      catch{
        case  _: Exception => response.append("<font color=\"Red\">Cannot Remove this book</font>".format())
          writeResponse(t, response.toString)
      }
    }
  }

  class GetHandler extends HttpHandler {
    @throws[IOException]
    override def handle(t: HttpExchange):Unit = {
      val response = new StringBuilder
      val parms = queryToMap(t.getRequestURI.getQuery)
      try {
        val list:List[Any] = GetBookDetail(parms.get("bookname"))
        response.append("<html><body>")
        response.append("Bookname : " + list.head + "<br/>")
        response.append("Writer : " + list(1)+ "<br/>")
        response.append("Price : " + list(2) + "<br/>")
        response.append("</body></html>")
        writeResponse(t, response.toString)
      }
      catch{
        case  _: Exception => response.append("<font color=\"Red\">Cannot find this book</font>".format())
          writeResponse(t, response.toString)
      }
    }
  }

  class AddHandler extends HttpHandler {
    @throws[IOException]
     override def handle(t: HttpExchange):Unit = {
      val response = new StringBuilder
      val parms = queryToMap(t.getRequestURI.getQuery)
      try {
        AddBook(parms.get("bookname"), parms.get("writer"), parms.get("price").toDouble)
        response.append("<html><body>")
        response.append("Bookname : " + parms.get("bookname") + "<br/>")
        response.append("Writer : " + parms.get("writer") + "<br/>")
        response.append("Price : " + parms.get("price") + "<br/>")
        response.append("</body></html>")
        writeResponse(t, response.toString)
      }
      catch{
        case  _: Exception => response.append("<font color=\"Red\">Cannot add this book</font>".format())
          writeResponse(t, response.toString)
      }
    }
  }

  @throws[IOException]
  def writeResponse(t: HttpExchange, response: String): Unit = {
    t.sendResponseHeaders(200, response.length)
    val os = t.getResponseBody
    os.write(response.getBytes)
    os.close()
  }

  def queryToMap(query: String): util.HashMap[String, String] = {
    val result = new util.HashMap[String,String]()
    for (param <- query.split("&")) {
      val pair = param.split("=")
      if (pair.length > 1) result.put(pair(0), pair(1))
      else result.put(pair(0),"")
    }
    result
  }

}
