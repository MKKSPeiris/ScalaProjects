package Bookshop

import java.util.EmptyStackException

import scala.collection.mutable

class Controllers( BookList: scala.collection.mutable.Map[String, BookDetailsClass]) {

  def AddBook(BookName: String, Writer: String, BookPrice: Double): mutable.Map[String, BookDetailsClass] = {
    BookList += (BookName -> new BookDetailsClass(Writer, BookPrice))
    BookList
  }

  def RemoveBook(BookName: String): mutable.Map[String, BookDetailsClass] = {
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
