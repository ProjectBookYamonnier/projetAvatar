package library

trait BaseDeDonnees {

  /** Cette attributs stockera la base de données
    */
  val data: Map[String, String]
}

trait AnalysePhrase {

  val ExceptionAdresseNonTrouvee: String =
    "Aucune adresse trouvée dans la base de données"
  val ExceptionMotCleNonTrouve: String =
    "Aucun mot cle trouvée dans la liste"

  /** @param cle le mot cle entré par l'utilisateur
    *
    * @return la valeur correspondante au mot cle dans le dictionnaire
    */
  def retourneAdresse(cle: String): String

  /** @param cle le mot cle entré par l'utilisateur
    *
    * @return vrai si il se trouve dans le dico et faux sinon
    */
  def trouveCleDansDico(cle: String): Boolean

  /** @param entreeUtilisateur
    * @return le mot cle si il existe
    */
  def trouveMotCleOUChaineDeMotCle(entreeUtilisateur: String): String

  /** @param s La commande entree par l'utilisateur
    *
    * @return La liste de chaque mot dans la phrase.
    */

  def motDansListe(s: String): List[String]

  /** @param s La commande entree par l'utilisateur
    *
    * @return La phrase qui repond à l'utilisateur
    */
  def renvoieReponse(s: String): List[String]

}

trait ToleranceFautes {

  /** Cette fonction permet de corriger la phrase de l'utilisateur en fonction de la base de données.
    * Si la base de données contient une clé "Mairie de Rennes" et que l'utilisateur entre "Ou est la Mairie Rennes",
    * la fonction renvoie "Ou est la Mairie de Rennes" en utilisant la distance de Levenshtein pour trouver les clés similaires
    * dans la base de données avec une tolérance de 1.
    *
    * @param phrase la phrase de l'utilisateur à corriger
    * @param baseDeDonnees la base de données contenant les clés et les valeurs associées
    * @return la phrase corrigée de l'utilisateur
    */
  def corrigerPhrase(phrase: String, baseDeDonnees: Map[String, String]): String

  /** Calcule la distance de Levenshtein entre deux chaînes de caractères, qui est définie comme
    * le nombre minimum de modifications à un caractère (ajouts, suppressions ou substitutions)
    * nécessaire pour transformer une chaîne en une autre.
    *
    * @param s1 la première chaîne de caractère
    * @param s2 la deuxième chaîne de caractères
    * @return la distance de Levenshtein entre s1 et s2
    */
  def distanceLevenshtein(s1: String, s2: String): Int
}

trait Discussion {

  /** @param entreeUtilisateur phrase entree corrigee
    * @return Bonjour si une salutation est presente dans l'entree
    */
  def salutation(entreeUtilisateur: String): String
}

trait Traduction {

  var langueActuelle: String

  /** @param entreeUtilisateur
    *
    * @return le nom de la langue
    */
  def detecteLangue(entreeUtilisateur: List[String]): String

  /** @param entreeUtilisateur
    * @return un string contenant le nom de la langue
    */
  def detecteSalutations(entreeUtilisateur: List[String]): String

  /** @param entreeUtilisateur
    * @return un string contenant le nom de la langue
    */
  def detecteRecherche(entreeUtilisateur: List[String]): String

  /** @param entreeUtilisateur
    * @return un string contenant le nom de la langue
    */
  def detecteNomLangue(entreeUtilisateur: List[String]): String

}

trait bddInternational {
  val bddPolitesse: Map[String, String]
  val bddRecherche: Map[String, String]
  val bddNomDeLangue: Map[String, String]
  val bddOui: Map[String, String]
  val bddNon: Map[String, String]
  val bddAdr1: Map[String, String]
  val bddAdr2: Map[String, String]
  val bddJNCRP: Map[String, String]
  val bddDemandeLang: Map[String, String]
  val bddDemande: Map[String, String]
  val bddNbrReponse: Map[String, String]
  val bddChoix: Map[String, String]
  val bddResto: Map[String, String]
  val bddCrep: Map[String, String]
  val bddPiz: Map[String, String]

}

trait SyntheseVocal {

  /** Synthétise et prononce à haute voix une liste de phrases.
    *
    * @param inputList une liste de chaînes de caractères représentant les phrases à synthétiser et prononcer.
    */
  def say(inputList: List[String]): Unit
}

trait genreRocketMultiple {

  var requeteEnCour: Boolean

  /*

  ================================================================================================
  generePossibiliteReponse(entreeUtilisateur, langue)
    Est la fonction qui donne a l'avatar la liste des reponses a afficher
    Elle utilise les sous fonctions suivantes :
      - genereReponseLieuxLangue(langue, nombreResultat)
      - formatageListe(entreeUtilisateur)
        Pour former la liste en considérant le nombre de res et la langue
      - formatageListe(entreeUtilisateur)
      - numeroString(liste, num)
        Pour la mise en former des valeurs de la liste
      - listeDeCle(entreeUtilisateur)
        Pour fournir les valeurs de la liste
  ================================================================================================

   */

  /** @param entreeUtilisateur
    * @param langue de l'avatar
    * @return La liste des formatee des elements de reponse pour l'avatar
    */
  def generePossibiliteReponse(
      entreeUtilisateur: String,
      langue: String
  ): List[String]

  /** @param reponseUtilisateur choix par rapport aux réponses de la requete multiple
    * @param listeReponse liste des réponses de la requete multiple
    * @param langue de l'avatar
    * @return la liste qui sera affichee par l'avatar
    */
  def getReponse(
      reponseUtilisateur: String,
      listeReponse: List[String],
      langue: String
  ): List[String]

}

trait Linternaute {
  val bddRestaurants: Map[String, String]

  /** @param entreeUtilisateur la requete de l'utilisateur sous forme de liste de chacun de ses mots
    * @return l'adresse de la creperie/pizzeria/restaurant
    */
  def adresseDe(entreeUtilisateur: List[String]): String

  /** @param entreeUtilisateur la requete de l'utilisateur sous forme de liste de chacun de ses mots
    * @return le nom de la creperie/pizzeria/restaurant
    */
  def nomDe(entreeUtilisateur: List[String]): String
}
