package machine

class Discussion extends library.Discussion {
  val ExceptionMotSalutationNonTrouve: String =
    "Aucune salutation dans l'entree utilisateur"

  val bddSalutation: Map[String, String] = Map(
    ("Bonjour" -> ""),
    ("Salut" -> ""),
    ("Bonsoir" -> ""),
    ("Hi" -> ""),
    ("Hello" -> ""),
    ("Morning" -> ""),
    ("Evening" -> ""),
    ("Afternoon" -> ""),
    ("Hey" -> ""),
    ("Hola" -> ""),
    ("Buenos" -> ""),
    ("Guten" -> ""),
    ("Morgen" -> ""),
    ("Abend" -> ""),
    ("Buongiorno" -> ""),
    ("Ciao" -> ""),
    ("Salve" -> ""),
    ("Buon" -> ""),
    ("Pomeriggio" -> ""),
    ("Buonasera" -> ""),
    ("Incantato" -> "")
  )

  /** @param entreeUtilisateur phrase entree corrigee
    * @return Bonjour si une salutation est presente dans l'entree
    */
  def salutation(entreeUtilisateur: String): String = {
    val tf = new TolerancesFautes
    val cor = tf.corrigerPhrase(entreeUtilisateur, bddSalutation)
    if (cor != "") cor
    else ExceptionMotSalutationNonTrouve
  }
}
