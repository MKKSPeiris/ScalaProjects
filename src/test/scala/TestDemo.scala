import Bookshop.BookDetailsClass
import org.scalatest.FunSuite

class TestDemo extends FunSuite {

  test(testName = "Get book Details") {
    val bookList = scala.collection.mutable.Map("Harry Potter" -> BookDetailsClass("Rowling", 10))
    val controller = new Bookshop.Controllers(bookList)
    val list = controller.getBookDetail("Harry Potter")
    assert(list.head !== null)
    assert(list(1) !== null)
    assert(list(0) !== null)
  }
  test(testName = "Get All Book Names") {
    val bookList = scala.collection.mutable.Map("Harry Potter" -> new BookDetailsClass("Rowling", 10),
      "War And Peace" -> new BookDetailsClass("Leo Tolstoy", 100))
    val controller = new Bookshop.Controllers(bookList)
    val bookNameList: Set[String] = controller.getAllBooks()
    assert(!bookNameList.isEmpty)
    bookNameList.foreach(i => assert(i !== null))
  }
  test(testName = "Add a Book") {
    val bookList: scala.collection.mutable.Map[String, BookDetailsClass] = scala.collection.mutable.Map[String, BookDetailsClass]()
    val controller = new Bookshop.Controllers(bookList)
    val returnBookList = controller.addBook("Thor", "Rowling", 10)
    assert(returnBookList.contains("Thor"))

  }
  test(testName = "Remove Book") {
    val bookList = scala.collection.mutable.Map("Harry Potter" -> new BookDetailsClass("Rowling", 10))
    val controller = new Bookshop.Controllers(bookList)
    val returnBookList = controller.removeBook("Harry Potter")
    assert(!returnBookList.contains("Harry Potter"))
  }
}
