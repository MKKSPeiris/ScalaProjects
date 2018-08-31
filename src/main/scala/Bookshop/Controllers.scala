package Bookshop

import java.util.EmptyStackException

class Controllers(var BookList: Map[String, BookDetailsClass]) {

  def AddBook(BookName: String, Writer: String, BookPrice: Double): Map[String, BookDetailsClass] = {
    BookList += (BookName -> new BookDetailsClass(Writer, BookPrice))
    BookList
  }

  def RemoveBook(BookName: String): Map[String, BookDetailsClass] = {
    if (BookList(BookName) != null) {
      BookList -= BookName
      BookList
    }
    else throw new EmptyStackException
  }

  def GetBookDetail(BookName: String): List[Any] = {
    val book = BookList(BookName)
    val list = List(BookName, book.Writer, book.Price)
    list
  }

  def GetAllBooks(): Set[String] = {
    var booklist: Set[String] = Set[String]()
    BookList.keys.foreach { key =>
      booklist += key
    }
    booklist
  }
}
