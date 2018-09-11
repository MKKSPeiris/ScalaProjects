import Bookshop.BookDetailsClass
import org.scalatest.FunSuite

class TestDemo extends FunSuite {

  test(testName = "Get book Details") {
    val BookList = scala.collection.mutable.Map("Harry Potter" -> BookDetailsClass("Rowling", 10))
    val Controler = new Bookshop.Controllers(BookList)
    val list = Controler.GetBookDetail("Harry Potter")
    assert(list.head !== null)
    assert(list(1) !== null)
    assert(list(0) !== null)
  }

  test(testName = "Get All Book Names") {
    val BookList = scala.collection.mutable.Map("Harry Potter" -> new BookDetailsClass("Rowling", 10),
      "War And Peace" -> new BookDetailsClass("Leo Tolstoy", 100))
    val Controler = new Bookshop.Controllers(BookList)
    val BookNameList: Set[String] = Controler.GetAllBooks()
    assert(!BookNameList.isEmpty)
    BookNameList.foreach(i => assert(i !== null))
  }
  test(testName = "Add a Book") {
    val BookList: scala.collection.mutable.Map[String, BookDetailsClass] =scala.collection.mutable.Map[String, BookDetailsClass]()
    val Controler = new Bookshop.Controllers(BookList)
    val ReturnBookList = Controler.AddBook("Thor", "Rowling", 10)
    assert(ReturnBookList.contains("Thor"))

  }
  test(testName = "Remove Book") {
    val BookList = scala.collection.mutable.Map("Harry Potter" -> new BookDetailsClass("Rowling", 10))
    val Controler = new Bookshop.Controllers(BookList)
    val ReturnBookList = Controler.RemoveBook("Harry Potter")
    assert(!ReturnBookList.contains("Harry Potter"))
  }
}
