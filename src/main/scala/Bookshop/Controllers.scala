package Bookshop

import java.util.EmptyStackException
import scala.collection.mutable

class Controllers(bookList: scala.collection.mutable.Map[String, BookDetailsClass]) {

  def addBook(bookName: String, Writer: String, BookPrice: Double): mutable.Map[String, BookDetailsClass] = {
    bookList += (bookName -> BookDetailsClass(Writer, BookPrice))
    bookList
  }

  def removeBook(bookName: String): mutable.Map[String, BookDetailsClass] = {
    if (bookList(bookName) != null) {
      bookList -= bookName
      bookList
    }
    else throw new EmptyStackException
  }

  def getBookDetail(bookName: String): List[Any] = {
    val book = bookList(bookName)
    val list = List(bookName, book.writer, book.price)
    list
  }

  def getAllBooks(): Set[String] = {
    val bookSet: Set[String] = bookList.keys.toSet
    bookSet
  }
}
