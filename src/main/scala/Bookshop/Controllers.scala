package Bookshop

import java.util.EmptyStackException

class Controllers(var BookList:Map[String,BookDetailsClass]) {

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
}
