package machine
import org.junit.Test
import org.junit.Assert._

class TestBaseDeDonnees {

  // initialisation des objets sous test
  val m = MachineImpl
  m.reinit

  @Test
  def testBDD_initial {
    val inst = BaseDeDonnees

    // On crée une instance de la base de données
    val bdd = inst.data

    println(bdd)

    // On teste la récupération de l'adresse d'un lieu
    assertEquals("Place de la Mairie", bdd.getOrElse("Hôtel de ville", "NULL"));
    assertEquals("1, Rue Saint-Hélier", bdd.getOrElse("TNB", "NULL"));
    assertEquals("19, Place de la Gare", bdd.getOrElse("Gare", "NULL"));
    assertEquals(
      "2, Rue du Pré de Bris",
      bdd.getOrElse("Théâtre La Paillette", "NULL")
    );

    //   // On teste que la base de données contient bien tous les lieux
    assertTrue(bdd.contains("Mairie"));
    assertTrue(bdd.contains("Mairie de Rennes"));
    assertTrue(bdd.contains("Théâtre National de Bretagne"));
    assertTrue(bdd.contains("Théâtre de Bretagne"));
    assertTrue(bdd.contains("Gare SNCF"));
    assertTrue(bdd.contains("la Paillette"));

    //   // On teste qu'un lieu qui n'est pas dans la base de données renvoie null
    assertEquals(None, bdd.get("Bibliothèque"));
    assertEquals(None, bdd.get("Gar"));
    assertEquals(None, bdd.get("Théâtre"));
    assertEquals(None, bdd.get("Mairi"));
  }

  @Test
  def testBDDacXML: Unit = {

  }

  @Test
  def testBDDInternational {
    
  }

}
