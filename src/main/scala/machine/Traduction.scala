package machine

object Traduction extends library.Traduction {

  var langueEnCours: String = "Français"

  //La langue initiale est le français
  var langueActuelle: String = "Français"

  //
  var changementEnCours: Boolean = false

  var BDD = BddInternational

  /** Cette fonction prend en entrée une liste de chaînes de caractères, correspondant à l'entrée de l'utilisateur,
    * et renvoie la langue détectée. Elle utilise les fonctions detecteSalutations, detecteNomLangue et detecteRecherche.
    *
    * @param entreeUtilisateur une liste de chaînes de caractères correspondant à l'entrée de l'utilisateur
    * @return la langue détectée sous forme de chaîne de caractères
    */
  def detecteLangue(entreeUtilisateur: List[String]): String = {
    var langue: String = ""

    val salutation: String = detecteSalutations(entreeUtilisateur)
    val nomLangue: String = detecteNomLangue(entreeUtilisateur)
    val recherche: String = detecteRecherche(entreeUtilisateur)
    if (salutation != "") {
      langue = salutation
     
    } else if (nomLangue != "") langue = nomLangue
    else if (recherche != "") langue = recherche
    if (langue != "") {
      langueEnCours = langue
    }
    langue
  }

  /** Cette fonction prend en entrée une liste de chaînes de caractères, correspondant à l'entrée de l'utilisateur,
    * et une map, correspondant à la base de données pour la recherche, et renvoie la réponse trouvée dans la base de données.
    *
    * @param entreeUtilisateur une liste de chaînes de caractères correspondant à l'entrée de l'utilisateur
    * @param bdd une map correspondant à la base de données pour la recherche
    * @return la réponse trouvée dans la base de données
    */
  def detecteBDD(
      entreeUtilisateur: List[String],
      bdd: Map[String, String]
  ): String = {
    val tf = new TolerancesFautes
    var rep = ""
    for ((k, v) <- bdd) {
      if (entreeUtilisateur.contains(k)) {
        rep = v
      }
    }

    if (rep == "" && bdd == BDD.bddNomDeLangue) {
      var phraseCor: List[String] = List()
      for (mot <- entreeUtilisateur) {
        phraseCor = tf.corrigerPhrase(mot, bdd) :: phraseCor
      }
      for ((k, v) <- bdd) {
        if (phraseCor.contains(k)) {
          rep = v
        }
      }
    }

    rep
  }

  /** Cette fonction prend en entrée une liste de chaînes de caractères, correspondant à l'entrée de l'utilisateur,
    * et renvoie la salutation détectée. Elle utilise la fonction detecteBDD avec la base de données des salutations.
    *
    * @param entreeUtilisateur une liste de chaînes de caractères correspondant à l'entrée de l'utilisateur
    * @return la salutation détectée
    */
  def detecteSalutations(entreeUtilisateur: List[String]): String = {
    detecteBDD(entreeUtilisateur, BDD.bddPolitesse)
  }

  /** Cette fonction prend en entrée une liste de chaînes de caractères, correspondant à l'entrée de l'utilisateur,
    * et renvoie la recherche détectée. Elle utilise la fonction detecteBDD avec la base de données des recherches.
    *
    * @param entreeUtilisateur une liste de chaînes de caractères correspondant à l'entrée de l'utilisateur
    * @return la recherche détectée
    */
  def detecteRecherche(entreeUtilisateur: List[String]): String = {
    detecteBDD(entreeUtilisateur, BDD.bddRecherche)
  }

  /** Cette fonction prend en entrée une liste de chaînes de caractères, correspondant à l'entrée de l'utilisateur,
    * et renvoie le nom de langue détecté. Elle utilise la fonction detecteBDD avec la base de données des noms de langues.
    *
    * @param entreeUtilisateur une liste de chaînes de caractères correspondant à l'entrée de l'utilisateur
    * @return le nom de langue détecté
    */
  def detecteNomLangue(entreeUtilisateur: List[String]): String = {
    detecteBDD(entreeUtilisateur, BDD.bddNomDeLangue)
  }

  /** @return la phrase envoyée à l'utilisateur pour demander confirmation dans la langue actuelle
    */
  def demandeConfirmation(): String = {
    BDD.bddDemandeLang.getOrElse(langueActuelle, "")
  }

  /** @param entreeUtilisateur une chaine de caracteres envoyée par l'utilisateur
    * @return vrai ssi l'utilisateur a confirmé (oui) dans la langue actuelle
    */
  def repondOuiDansLangueCourante(entreeUtilisateur: String): Boolean = {
    val corrOuiBdd: Map[String, String] = Map(
      ("oui" -> ""),
      ("yes" -> ""),
      ("ja" -> ""),
      ("si" -> ""),
      ("si" -> "")
    )
    val tf = new TolerancesFautes
    tf.corrigerPhrase(entreeUtilisateur, corrOuiBdd)
      .contains(BDD.bddOui.getOrElse(langueActuelle, ""))
  }

  /** @return renvoie la phrase demandant de confirmer le changement de langue dans la langue actuelle
    */
  def confirmeDansLangueCourante(): String = {
    BDD.bddDemande.getOrElse(langueActuelle, "")
  }

  /** @return renvoie la chaine de caractere de salutations dans la langue actuelle
    */
  def salueDansLangueCourante(): String = {
    BDD.bddBonjour.getOrElse(langueActuelle, "")
  }

  /** @return renvoie la chaine de caractere d'incompréhension de l'avatar dans la langue actuelle
    */
  def neComprendsPasDansLangueCourante(): String = {
    BDD.bddJNCRP.getOrElse(langueActuelle, "")
  }

  /** @return renvoie la chaine de caractere partie 1 de type "L'adresse de " dans la langue actuelle
    */
  def donneAdresse1DansLangueCourante(): String = {
    BDD.bddAdr1.getOrElse(langueActuelle, "")
  }

  /** @return renvoie la chaine de caractere partie 2 de type "est : " dans la langue actuelle
    */
  def donneAdresse2DansLangueCourante(): String = {
    BDD.bddAdr2.getOrElse(langueActuelle, "")
  }

  /** effectue un saut dans le cycle des changement de langues
    */
  def changementLangueSuivante(): Unit = {
    if (langueActuelle == "Français") {
      langueActuelle = "Anglais"
    } else if (langueActuelle == "Anglais") {
      langueActuelle = "Espagnol"
    } else if (langueActuelle == "Espagnol") {
      langueActuelle = "Allemand"
    } else if (langueActuelle == "Allemand") {
      langueActuelle = "Italien"
    } else if (langueActuelle == "Italien") {
      langueActuelle = "Français"
    }
  }

}
