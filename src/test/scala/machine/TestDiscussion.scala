package machine

import org.junit.Test
import org.junit.Assert._

class TestDiscussion {
  var disc = new Discussion

  @Test
  def testRenvoieReponseF3 {
    assertEquals(
      "Bonjour",
      disc.salutation("Bonjour")
    )
    assertEquals(
      "Bonjour",
      disc.salutation("Bonour")
    )
    assertEquals(
      "Bonjour",
      disc.salutation("bonour")
    )
    assertEquals(
      "Salut",
      disc.salutation("Salut")
    )
    assertEquals(
      "Salut",
      disc.salutation("Saut")
    )
    assertEquals(
      "Salut",
      disc.salutation("saut")
    )
    assertEquals(
      "Bonsoir",
      disc.salutation("Bonsoir")
    )
    assertEquals(
      "Aucune salutation dans l'entree utilisateur",
      disc.salutation("Ou est la mairie de rennes")
    )

  }
}
