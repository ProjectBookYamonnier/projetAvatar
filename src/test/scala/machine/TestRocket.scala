package machine
import org.junit.Test
import org.junit.Assert._

class TestRocket {
  val analyse = new RocketMultiple()
  val listRep: List[String] = List(
    "Jai 4 réponses possibles :",
    "1) Piscine Bréquigny",
    "2) Piscine Gayeulles",
    "3) Piscine Saint-Georges",
    "4) Piscine Villejean",
    "Quel est votre choix?"
  )
  val list: List[String] = List(
    "Piscine Saint-Georges",
    "Piscine Gayeulles",
    "Piscine Villejean",
    "Piscine Bréquigny"
  )
  val listOrdonee: List[String] = List(
    "1) Piscine Bréquigny",
    "2) Piscine Gayeulles",
    "3) Piscine Saint-Georges",
    "4) Piscine Villejean"
  )

  @Test
  def testNumerotation: Unit = {
    assertEquals(
      listOrdonee,
      analyse.formatageListe("Je cherche une piscine")
    )
  }

  @Test
  def testGetReponse: Unit = {
    val listRep: List[String] = List(
      "J’ai 6 réponses possibles :",
      "1) Piscine Bréquigny",
      "2) Piscine Gayeulles",
      "3) Piscine Saint-Georges",
      "4) Piscine Villejean",
      "5) Piscine de Cesson-Sévigné",
      "6) Piscine intercommunale de la Conterie",
      "Quel est votre choix?"
    )
    assertEquals(
      List("L'adresse de Piscine Bréquigny est : 10, Boulevard Albert 1er"),
      analyse.getReponse("je choisi la 1", listRep, "Français")
    )
  }
  @Test
  def testGetReponse1: Unit = {
    val listRep: List[String] = List(
      "Tengo 4 opciones :",
      "1) Piscine Bréquigny",
      "2) Piscine Gayeulles",
      "3) Piscine Saint-Georges",
      "4) Piscine Villejean",
      "Cual es su eleccion?"
    )
    assertEquals(
      List("No comprendo"),
      analyse.getReponse("18", listRep, "Espagnol")
    )
  }

  // @Test
  // def testContain {
  //   assertTrue("1) Piscine Bréquigny".contains(1))
  //   println("1) Piscine Bréquigny".contains(1))
  // }

  @Test
  def listeDeCle: Unit = {
    assertEquals(
      List(
        "Piscine Bréquigny",
        "Piscine Gayeulles",
        "Piscine Saint-Georges",
        "Piscine Villejean"
      ),
      analyse.listeDeCle("Je cherche une piscine")
    )
    assertEquals(
      List(
        "Stade Alain Crubillé",
        "Stade Beauregard",
        "Stade Gros Malhon",
        "Stade Roger Salengro",
        "Stade de La Harpe",
        "Stade de la Bellangerais"
      ),
      analyse.listeDeCle("Je cherche un stade")
    )
  }
}
