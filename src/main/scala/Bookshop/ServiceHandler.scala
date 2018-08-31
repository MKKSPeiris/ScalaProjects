package Bookshop

import java.io.IOException
import java.util
import com.sun.net.httpserver.{HttpExchange, HttpHandler}

class ServiceHandler(var BookList:Map[String,BookDetailsClass]) {

  var Controllers =new Controllers(BookList)

  class GetAllBooksHandler extends HttpHandler {
    @throws[IOException]
    override def handle(t: HttpExchange): Unit = {
      val response = new StringBuilder
      response.append("<html><body>")
      response.append(Controllers.GetAllBooks())
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
        Controllers.RemoveBook(parms.get("bookname"))
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
        val list:List[Any] = Controllers.GetBookDetail(parms.get("bookname"))
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
        Controllers.AddBook(parms.get("bookname"), parms.get("writer"), parms.get("price").toDouble)
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
    result}
}
