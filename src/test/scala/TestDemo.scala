import Bookshop.BookDetailsClass
import org.scalatest.FunSuite

class TestDemo extends FunSuite {
  test(testName = "First Test"){

    val BookList = Map("Harry Potter" -> BookDetailsClass("Rowling", 10))
    var Controler = new Bookshop.Controllers( BookList)
    assert(Controler.GetBookDetail("Harry Potter") !== null)
  }
}
