package machine
import org.junit.Test
import org.junit.Assert._

class TestAnalysePhrase {
  val analyse = new AnalysePhrase()

  @Test
  def testRenvoieReponseF1 {
    assertEquals(
      List("Bonjour"),
      analyse.renvoieReponse("Bonjour")
    )
    assertEquals(
      List("Bonjour"),
      analyse.renvoieReponse("onjour")
    )
    assertEquals(
      List("Bonjour"),
      analyse.renvoieReponse("Bonjours")
    )
    assertEquals(
      List("Bonjour"),
      analyse.renvoieReponse("Bonjour,")
    )
    assertEquals(
      List("Bonjour", "L'adresse de Mairie de Rennes est : Place de la Mairie"),
      analyse.renvoieReponse("Bonjou ou est la Mairie de Rennes")
    )
    assertEquals(
      List("L'adresse de Mairie de Rennes est : Place de la Mairie"),
      analyse.renvoieReponse("Où est donc la Mairie de Rennes")
    )
    assertEquals(
      List(
        "L'adresse de Théâtre National de Bretagne est : 1, Rue Saint-Hélier"
      ),
      analyse.renvoieReponse("Où se trouve le Théâtre National de Bretagne")
    )
    assertEquals(
      List("L'adresse de Mairie de Rennes est : Place de la Mairie"),
      analyse.renvoieReponse("Où est la Mairie")
    )
    assertEquals(
      List("L'adresse de Théâtre La Paillette est : 2, Rue du Pré de Bris"),
      analyse.renvoieReponse("Je recherche le Théâtre de la Paillette")
    )
    assertEquals(
      List(
        "L'adresse de Théâtre National de Bretagne est : 1, Rue Saint-Hélier"
      ),
      analyse.renvoieReponse("Où est le TNB")
    )
    assertEquals(
      List("Je ne comprends pas votre demande"),
      analyse.renvoieReponse("ou trouver")
    )
    assertEquals(
      List("Je ne comprends pas votre demande"),
      analyse.renvoieReponse("askdhlkajh")
    )
    assertEquals(
      List("Je ne comprends pas votre demande"),
      analyse.renvoieReponse("je cherche")
    )
    assertEquals(
      List("L'adresse de Gare SNCF est : 19, Place de la Gare"),
      analyse.renvoieReponse("Où est la Gare")
    )
  }

  @Test
  def testRenvoieReponseF2 {
    assertEquals(
      List("L'adresse de Mairie de Rennes est : Place de la Mairie"),
      analyse.renvoieReponse("Où est donc la Mairie Rennes")
    )
    assertEquals(
      List("L'adresse de Mairie de Rennes est : Place de la Mairie"),
      analyse.renvoieReponse("hôtel ville")
    )
    assertEquals(
      List("L'adresse de Théâtre La Paillette est : 2, Rue du Pré de Bris"),
      analyse.renvoieReponse("Je recherche le théâtre paillette")
    )
    assertEquals( //passe pas
      List("L'adresse de Mairie de Rennes est : Place de la Mairie"),
      analyse.renvoieReponse("Où est donc l'htel de ville")
    )
    assertEquals(
      List("L'adresse de Mairie de Rennes est : Place de la Mairie"),
      analyse.renvoieReponse("Où est donc l'hotil de ville")
    )
    assertEquals(
      List("L'adresse de Mairie de Rennes est : Place de la Mairie"),
      analyse.renvoieReponse("Où est donc l'otel de ville")
    )
    assertEquals(
      List("L'adresse de Mairie de Rennes est : Place de la Mairie"),
      analyse.renvoieReponse("Où est donc l'hote de ville")
    )
    assertEquals(
      List("L'adresse de Mairie de Rennes est : Place de la Mairie"),
      analyse.renvoieReponse("Où est donc l'hote de valle")
    )
    assertEquals(
      List("L'adresse de Théâtre La Paillette est : 2, Rue du Pré de Bris"),
      analyse.renvoieReponse("Je recherche le teatre paillete")
    )
    assertEquals(
      List(
        "L'adresse de Théâtre National de Bretagne est : 1, Rue Saint-Hélier"
      ),
      analyse.renvoieReponse("Où se trouve le théâtre de bretagne")
    )
  }

  @Test
  def testmotDansListeF1 {
    assertEquals(
      List("Bonjour", "je", "suis", "là", "m", "amamia"),
      analyse.motDansListe("Bonjour je suis là m'amamia")
    )
    assertEquals(
      List(): List[String],
      analyse.motDansListe("")
    )
    assertEquals(
      List("Bonjour", "je", "suis", "là", "m", "amamia"),
      analyse.motDansListe("Bonjour?je!suis.là:m'amamia")
    )
    assertEquals(
      List("Bonjour", "je", "suis", "là", "m", "amamia"),
      analyse.motDansListe("Bonjour? je !suis. là: m'amamia")
    )
  }

  @Test
  def testTrouveMotCle {
    assertEquals(
      "Mairie de Rennes",
      analyse.trouveMotCleOUChaineDeMotCle(
        "Bonjour, je cherche la Mairie de Rennes"
      )
    )
    assertEquals(
      "Aucun mot cle trouvée dans la liste",
      analyse.trouveMotCleOUChaineDeMotCle(
        "Bonjour, je cherche la mairie de rennes"
      )
    )
    assertEquals(
      "Mairie de Rennes",
      analyse.trouveMotCleOUChaineDeMotCle(
        "Bonjour, je cherche la Mairie de Renneses"
      )
    )

  }

  @Test
  def testFaitPartie1 {
    assertEquals("Mairie de Rennes", analyse.faitPartie("Mairie"))
    assertEquals("Gare SNCF", analyse.faitPartie("Gare"))
    assertEquals("Théâtre National de Bretagne", analyse.faitPartie("TNB"))
  }

  @Test
  def testRetourneAdresse1 {
    assertEquals("Place de la Mairie", analyse.retourneAdresse("Mairie"))
    assertEquals(
      "Place de la Mairie",
      analyse.retourneAdresse("Mairie de Rennes")
    )
    assertEquals(
      "Place de la Mairie",
      analyse.retourneAdresse("Hôtel de ville")
    )
    assertEquals("", analyse.retourneAdresse("rien"))
  }

  @Test
  def testTrouveCleDansDico(): Unit = {
    assertTrue(analyse.trouveCleDansDico("Mairie"))
    assertTrue(analyse.trouveCleDansDico("Hôtel de ville"))
    assertFalse(analyse.trouveCleDansDico("Cimetière"))
  }

  @Test
  def testRetourneAdresse() {
    assertEquals("Place de la Mairie", analyse.retourneAdresse("Mairie"))
    assertEquals("19, Place de la Gare", analyse.retourneAdresse("Gare"))
    assertEquals("", analyse.retourneAdresse("Cimetière"))
    assertEquals("", analyse.retourneAdresse(""))
  }
}
