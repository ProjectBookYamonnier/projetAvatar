package machine

object BddInternational extends library.bddInternational {

  /*
      ENTREE UTILISATEUR
   */

  val bddNomDeLangue: Map[String, String] = Map(
    "français" -> "Français",
    "english" -> "Anglais",
    "español" -> "Espagnol",
    "deutsch" -> "Allemand",
    "italiano" -> "Italien"
  )

  val bddPolitesse: Map[String, String] = Map(
    ("bonjour" -> "Français"),
    ("Bonjour" -> "Français"),
    ("salut" -> "Français"),
    ("bonsoir" -> "Français"),
    ("hi" -> "Anglais"),
    ("hello" -> "Anglais"),
    ("morning" -> "Anglais"),
    ("evening" -> "Anglais"),
    ("afternoon" -> "Anglais"),
    ("hey" -> "Anglais"),
    ("hola" -> "Espagnol"),
    ("buenos" -> "Espagnol"),
    ("hallo" -> "Allemand"),
    ("guten" -> "Allemand"),
    ("morgen" -> "Allemand"),
    ("tag" -> "Allemand"),
    ("abend" -> "Allemand"),
    ("buongiorno" -> "Italien"),
    ("ciao" -> "Italien"),
    ("salve" -> "Italien"),
    ("buon" -> "Italien"),
    ("pomeriggio" -> "Italien"),
    ("buonasera" -> "Italien"),
    ("incantato" -> "Italien")
  )
  val bddRecherche: Map[String, String] = Map(
    "recherche" -> "Français",
    "cherche" -> "Français",
    "ou" -> "Français",
    "est" -> "Français",
    "donc" -> "Français",
    "trouve" -> "Français",
    "trouver" -> "Français",
    "seek" -> "Anglais",
    "seeking" -> "Anglais",
    "search" -> "Anglais",
    "searching" -> "Anglais",
    "look" -> "Anglais",
    "looking" -> "Anglais",
    "where" -> "Anglais",
    "find" -> "Anglais",
    "donde" -> "Espagnol",
    "esta" -> "Espagnol",
    "busco" -> "Espagnol",
    "buscando" -> "Espagnol",
    "wo" -> "Allemand",
    "ist" -> "Allemand",
    "suche" -> "Allemand",
    "suchen" -> "Allemand",
    "dove" -> "Italien",
    "trova" -> "Italien",
    "cerco" -> "Italien",
    "cercando" -> "Italien"
  )

  /*
      SORTIE AVATAR
   */
  val bddOui: Map[String, String] = Map(
    ("Français" -> "oui"),
    ("Anglais" -> "yes"),
    ("Allemand" -> "ja"),
    ("Italien" -> "si"),
    ("Espagnol" -> "si")
  )

  val bddChoix: Map[String, String] = Map(
    ("Français" -> "Quel est votre choix ?"),
    ("Anglais" -> "What is your choice ?"),
    ("Allemand" -> "Was ist Ihre Wahl ?"),
    ("Italien" -> "Qual è la vostra scelta ?"),
    ("Espagnol" -> "Cuál es su elección ?")
  )

  val bddResto: Map[String, String] = Map(
    ("Français" -> "restaurant"),
    ("Anglais" -> "restaurant"),
    ("Allemand" -> "restaurant"),
    ("Italien" -> "ristorante"),
    ("Espagnol" -> "restaurante")
  )

  val bddCrep: Map[String, String] = Map(
    ("Français" -> "creperie"),
    ("Anglais" -> "creperie"),
    ("Allemand" -> "creperie"),
    ("Italien" -> "creperie"),
    ("Espagnol" -> "creperie")
  )
  val bddPiz: Map[String, String] = Map(
    ("Français" -> "pizzeria"),
    ("Anglais" -> "pizzeria"),
    ("Allemand" -> "pizzeria"),
    ("Italien" -> "pizzeria"),
    ("Espagnol" -> "pizzeria")
  )

  val bddBonjour: Map[String, String] = Map(
    ("Français" -> "Bonjour"),
    ("Anglais" -> "Hello"),
    ("Espagnol" -> "Hola"),
    ("Allemand" -> "Hallo"),
    ("Italien" -> "Buongiorno")
  )

  val bddNon: Map[String, String] = Map(
    ("Français" -> "non"),
    ("Anglais" -> "no"),
    ("Espagnol" -> "no"),
    ("Allemand" -> "nein"),
    ("Italien" -> "no")
  )

  val bddAdr1: Map[String, String] = Map(
    ("Français" -> "L'adresse de "),
    ("Anglais" -> "The address of "),
    ("Espagnol" -> "La dirección de "),
    ("Allemand" -> "Die adresse von "),
    ("Italien" -> "Indirizzo di ")
  )

  val bddAdr2: Map[String, String] = Map(
    ("Français" -> " est : "),
    ("Anglais" -> " is : "),
    ("Espagnol" -> " es : "),
    ("Allemand" -> " ist : "),
    ("Italien" -> " è : ")
  )

  val bddJNCRP: Map[String, String] = Map(
    ("Français" -> "Je ne comprends pas votre demande"),
    ("Anglais" -> "I do not understand"),
    ("Espagnol" -> "No comprendo"),
    ("Allemand" -> "Ich verstehe nicht"),
    ("Italien" -> "No capisco")
  )
  val bddDemandeLang: Map[String, String] = Map(
    ("Français" -> "Parlez-vous français ?"),
    ("Anglais" -> "Do you speak english ?"),
    ("Espagnol" -> "Hablas español ?"),
    ("Allemand" -> "Sprechen Sie Deutsch ?"),
    ("Italien" -> "Parli italiano ?")
  )
  val bddDemande: Map[String, String] = Map(
    ("Français" -> "D'accord, quelle est votre demande ?"),
    ("Anglais" -> "OK, what is your query ?"),
    ("Espagnol" -> "Está bien, cuál es tu petición ?"),
    ("Allemand" -> "Okay, was ist Ihr Wunsch ?"),
    ("Italien" -> "Va bene, qual è la tua richiesta ?")
  )
  val bddNbrReponse: Map[String, String] = Map(
    ("Français" -> "J'ai XXX réponses possibles"),
    ("Anglais" -> "I found XXX answers"),
    ("Espagnol" -> "Tengo XXX opciones"),
    ("Allemand" -> "Ich habe XXX Antworten"),
    ("Italien" -> "Ho XXX risposte")
  )

  val bddEndroits: Map[String, String] = Map(
    ("piscine" -> "piscine"),
    ("theatre" -> "théâtre")
  )
}
