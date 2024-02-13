package machine
import org.junit.Test
import org.junit.Assert._

class TestTraductionuction {

  @Test
  def testTraductionuction {

    //en français
    assertEquals("Français", Traduction.detecteLangue("bonjour" :: Nil))
    assertEquals("Français", Traduction.detecteLangue("bonjour" :: Nil))
    assertEquals(
      "Français",
      Traduction.detecteLangue(
        "bonjour" :: "je" :: "cherche" :: "la" :: "gare" :: Nil
      )
    )

    //dans d'autres langues
    assertEquals("Espagnol", Traduction.detecteLangue("hola" :: Nil))

    assertEquals("Allemand", Traduction.detecteLangue("hallo" :: Nil))

    assertEquals("Anglais", Traduction.detecteLangue("hi" :: Nil))

    assertEquals("Italien", Traduction.detecteLangue("buongiorno" :: Nil))

    assertEquals("Français", Traduction.detecteLangue("francais" :: Nil))

    assertEquals("Espagnol", Traduction.detecteLangue("espanol" :: Nil))

    assertEquals("Allemand", Traduction.detecteLangue("deutch" :: Nil))

    assertEquals("Anglais", Traduction.detecteLangue("englih" :: Nil))

    assertEquals("Italien", Traduction.detecteLangue("italiano" :: Nil))

    assertEquals("Français", Traduction.detecteLangue("bonsoir" :: Nil))

    assertEquals("Espagnol", Traduction.detecteLangue("buenos" :: Nil))

    assertEquals("Allemand", Traduction.detecteLangue("morgen" :: Nil))

    assertEquals("Anglais", Traduction.detecteLangue("evening" :: Nil))

    assertEquals("Italien", Traduction.detecteLangue("pomeriggio" :: Nil))

    assertEquals("Espagnol", Traduction.detecteLangue("busco" :: "Gare" :: Nil))

  }

  @Test
  def testDetecteBDD {
    var BDD = BddInternational
    assertEquals(
      "Français",
      Traduction.detecteBDD("bonjour" :: Nil, BDD.bddPolitesse)
    )
    assertEquals(
      "Allemand",
      Traduction.detecteBDD("hallo" :: Nil, BDD.bddPolitesse)
    )

  }

  @Test
  def testTraductionuctionAvecFautes {

    //en français
    assertEquals("Français", Traduction.detecteLangue("Français" :: Nil))
    assertEquals("Français", Traduction.detecteLangue("francais" :: Nil))
    assertEquals("Français", Traduction.detecteLangue("Franças" :: Nil))
    assertEquals(
      "Français",
      Traduction.detecteLangue(
        "bonjour" :: "francais" :: "je" :: "chrche" :: "la" :: "gare" :: Nil
      )
    )

  }

  @Test
  def testRepondOuiDansLangueCourante {
    Traduction.langueActuelle = "Français"
    assertEquals(true, Traduction.repondOuiDansLangueCourante("oui"))

    Traduction.langueActuelle = "Espagnol"
    assertEquals(true, Traduction.repondOuiDansLangueCourante("si"))

    Traduction.langueActuelle = "Italien"
    assertEquals(true, Traduction.repondOuiDansLangueCourante("Si"))

    Traduction.langueActuelle = "Anglais"
    assertEquals(true, Traduction.repondOuiDansLangueCourante("yEs"))

    Traduction.langueActuelle = "Allemand"
    assertEquals(true, Traduction.repondOuiDansLangueCourante("ja"))

  }

}
