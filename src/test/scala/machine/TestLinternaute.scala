package machine
import org.junit.Test
import org.junit.Assert._
import library.Html
import library.Tag
import library.Texte
import library.OutilsWebObjet

class TestLinternaute {
  val lint = new Linternaute

  @Test
  def testRecupereNom {
    assertEquals(
      List("la", "tomate"),
      lint.recupereNom(List("Je", "cherche", "la", "pizzeria", "la", "tomate"))
    )

    assertEquals(
      List("pepperonni"),
      lint.recupereNom(List("Je", "cherche", "la", "pizzeria", "pepperonni"))
    )

    assertEquals(
      List("ouzh", "taol"),
      lint.recupereNom(List("Je", "cherche", "la", "creperie", "ouzh", "taol"))
    )

    assertEquals(
      List("ouzh", "taol"),
      lint.recupereNom(List("creperie", "ouzh", "taol"))
    )

    assertEquals(
      List("balthazar"),
      lint.recupereNom(List("restaurante", "balthazar"))
    )

    assertEquals(
      List("roadside"),
      lint.recupereNom(List("busco", "el", "ristorante", "roadside"))
    )

    assertEquals(
      List(),
      lint.recupereNom(List("busco", "el", "ristorante"))
    )
  }

  @Test
  def testCherchePageRequete {
    assertEquals(
      "https://www.linternaute.com/restaurant/guide/ville-rennes-35000/?name=la+tomate",
      lint.cherchePageRequete(List("la", "tomate"))
    )
    assertEquals(
      "https://www.linternaute.com/restaurant/guide/ville-rennes-35000/?name=roadside",
      lint.cherchePageRequete(List("roadside"))
    )
  }

  @Test
  def testCherchePageResto {
    assertEquals(
      "https://www.linternaute.com/restaurant/restaurant/144042/ouzh-taol.shtml",
      lint.cherchePageResto(
        OutilsWebObjet.obtenirHtml(
          "https://www.linternaute.com/restaurant/guide/ville-rennes-35000/?name=ouzh+taol"
        )
      )
    )
  }

  @Test
  def testChercheAdresseResto {
    assertEquals(
      "27, rue Saint Melaine",
      lint.chercheAdresseResto(
        OutilsWebObjet.obtenirHtml(
          "https://www.linternaute.com/restaurant/restaurant/144042/ouzh-taol.shtml"
        )
      )
    )
  }

  @Test
  def testadresseDe {
    assertEquals(
      "18, rue Saint Georges",
      lint.adresseDe(List("pizzeria", "la", "tomate"))
    )
    assertEquals(
      "27, rue Saint Melaine",
      lint.adresseDe(List("restaurant", "ouzh", "taol"))
    )
    assertEquals(
      "",
      lint.adresseDe(List("restaurant", "roadside"))
    )
  }

}
