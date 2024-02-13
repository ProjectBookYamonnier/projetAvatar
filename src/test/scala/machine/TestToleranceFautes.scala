package machine
import org.junit.Test
import org.junit.Assert._
import machine.BaseDeDonnees

class TestToleranceFautes {

  val tf = new TolerancesFautes
  val bdd = BaseDeDonnees

  val bddSalutation = Map(
    "Bonjour" -> "",
    "Salut" -> "",
    "Bonsoir" -> ""
  )

  @Test
  def tFautes {
    assertEquals("azerty", tf.preparerPhrase("AZERTY"))
    assertEquals("eeaoui", tf.preparerPhrase("éèàôûî"))
    assertEquals("chanson", tf.preparerPhrase("la chanson"))
    assertEquals(
      "chansoncet etait jolie",
      tf.preparerPhrase("La chansoncet était JOLIE")
    )
    assertEquals("hotel", tf.preparerPhrase("l'hotel"))

    assertEquals(1, tf.distanceLevenshtein("mot", "mots"))
    assertEquals(1, tf.distanceLevenshtein("Mot", "mot"))
    assertEquals(0, tf.distanceLevenshtein("mot", "mot"))
    assertEquals(tf.distanceLevenshtein("mot", "frz"), 3)

    assertEquals("saint george", tf.enleverLdetTiret("saint-george"))
  }

  @Test
  def testCorigerPhraseF2 {
    assertEquals(
      "Mairie",
      tf.corrigerPhrase("Où est donc la mairie Rennes", bdd.data)
    )
    assertEquals(
      "Hôtel de ville",
      tf.corrigerPhrase("hôtel ville", bdd.data)
    )
    assertEquals(
      "la Paillette",
      tf.corrigerPhrase("Je recherche le théâtre paillette", bdd.data)
    )
    assertEquals(
      "Hôtel de ville",
      tf.corrigerPhrase("Où est donc l'htel de ville", bdd.data)
    )
    assertEquals(
      "Hôtel de ville",
      tf.corrigerPhrase("Où est donc l'hotil de ville", bdd.data)
    )
    assertEquals(
      "Hôtel de ville",
      tf.corrigerPhrase("Où est donc l'otel de ville", bdd.data)
    )
    assertEquals(
      "Hôtel de ville",
      tf.corrigerPhrase("Où est donc l'hote de ville", bdd.data)
    )
    assertEquals(
      "Hôtel de ville",
      tf.corrigerPhrase("Où est donc l'hote de valle", bdd.data)
    )
    assertEquals(
      "la Paillette",
      tf.corrigerPhrase("Je recherche le teatre paillete", bdd.data)
    )
    assertEquals(
      "Théâtre de Bretagne",
      tf.corrigerPhrase("Où se trouve le théâtre de bretagne", bdd.data)
    )
  }

  @Test
  def testCorigerPhraseF3 {
    assertEquals(
      "Bonjour",
      tf.corrigerPhrase("bonjour", bddSalutation)
    )
    assertEquals(
      "Bonjour",
      tf.corrigerPhrase("bonjour je cherche tnb", bddSalutation)
    )
    assertEquals(
      "Bonjour",
      tf.corrigerPhrase("bonour", bddSalutation)
    )
    assertEquals(
      "Bonjour",
      tf.corrigerPhrase("bojour je cherche tnb", bddSalutation)
    )
    assertEquals(
      "Bonjour",
      tf.corrigerPhrase("bojour je cherche hgshg", bddSalutation)
    )
    assertEquals(
      "",
      tf.corrigerPhrase("bijour djkslfdsklj", bddSalutation)
    )
    assertEquals(
      "Bonjour",
      tf.corrigerPhrase("bonjoir", bddSalutation)
    )
    assertEquals(
      "Salut",
      tf.corrigerPhrase("salut, je cherche tnb", bddSalutation)
    )
  }

  @Test
  def testCorigerPhraseF5 {
    assertEquals(
      "Piscine Saint-Georges",
      tf.corrigerPhrase("piscine Saint-Georges", bdd.data)
    )
    assertEquals(
      "Piscine Saint-Georges",
      tf.corrigerPhrase("je cherche la pisicine saint georges", bdd.data)
    )
  }
}
