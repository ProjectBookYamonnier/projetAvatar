package machine

import java.util.Collections

class RocketMultiple extends library.genreRocketMultiple {

  var BDD = BddInternational

  var BDDEtendue = BaseDeDonnees

  var requeteEnCour: Boolean = false

  var derniereListe: List[String] = List()

  val tf = new TolerancesFautes
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
  ): List[String] = {
    var a = formatageListe(entreeUtilisateur)
    genereReponseLieuxLangue(langue, a.length) match {
      case Nil => List()
      case h :: t => {
        derniereListe = h :: formatageListe(entreeUtilisateur) ::: t
        h :: formatageListe(entreeUtilisateur) ::: t
      }

    }
  }

  /** @param langue utilisee par l'utilisateur
    * @param nombreResultat nombre d'addresses qui vont etre présentées a l'utilisateur
    * @return les phrases de reponse ( nombre resultat et demande du choix )
    */
  def genereReponseLieuxLangue(
      langue: String,
      nombreResultat: Int
  ): List[String] = {
    var nbRep: String = BDD.bddNbrReponse.getOrElse(langue, "")
    nbRep = nbRep.replace("XXX", nombreResultat.toString())
    var listeReponseLieuxLangue: List[String] = List(nbRep)
    if (nombreResultat > 0) {
      requeteEnCour = true
      listeReponseLieuxLangue = List(nbRep, BDD.bddChoix.getOrElse(langue, ""))
    }
    listeReponseLieuxLangue
  }

  /** @param entreeUtilisateur chaine de caractère de l'utilisateur
    * @return renvoie la liste des lieux possible numeroté et classé par ordre alphabétique
    */
  def formatageListe(entreeUtilisateur: String): List[String] = {
    val liste: List[String] = listeDeCle(entreeUtilisateur)
    numeroString(liste, 1)
  }

  /** @param liste des lieux demandés
    * @param num numéro a attribuer en début de String
    * @return
    */
  def numeroString(liste: List[String], num: Int): List[String] = {
    liste match {
      case Nil          => List()
      case head :: next => num + ") " + head :: numeroString(next, num + 1)
    }
  }

  /** @param entreeUtilisateur
    * @return la liste compose des endroits pouvant répondre a la requete multiple à partir de la BDDEtendue
    */

  def listeDeCle(entreeUtilisateur: String): List[String] = {
    val listeDeMot: List[String] = entreeUtilisateur.split(" ").toList
    var listeDeCle: List[String] = List()
    var motDeRecherchePresent = false
    for (mot <- listeDeMot) {
      if (BDD.bddRecherche.contains(mot)) { motDeRecherchePresent = true }
    }
    var motCle: String = ""
    if (motDeRecherchePresent) {
      motCle = getDernierMot(listeDeMot)
      for ((k, v) <- BDDEtendue.data) {
        var cle: String = k
          .replaceAll("[éèêë]", "e")
          .replaceAll("[àâä]", "a")
          .replaceAll("[ûüù]", "u")
          .replaceAll("[ôö]", "o")
          .replaceAll("[îï]", "i")
          .toLowerCase
          .trim

        if (cle.contains(motCle)) {
          listeDeCle = k :: listeDeCle
        }
      }
      if (listeDeCle == List()) {
        // ULTRA BANCAL A OPTIMISER /!\
        var motClecorrige = tf.corrigerPhrase(motCle, BDD.bddEndroits)
        if (motClecorrige == motCle) {
          motClecorrige = BDD.bddEndroits.getOrElse(motCle, motCle)
        }
        if (motClecorrige != "") {
          for ((k, v) <- BDDEtendue.data) {
            var cle: String = k
              .replaceAll("[éèêë]", "e")
              .replaceAll("[àâä]", "a")
              .replaceAll("[ûüù]", "u")
              .replaceAll("[ôö]", "o")
              .replaceAll("[îï]", "i")
              .toLowerCase
              .trim
            if (cle.contains(motClecorrige)) {
              listeDeCle = k :: listeDeCle
            }
          }
        }
      }
    }
    listeDeCle.sorted
  }

  /** @param liste
    * @return les dernier String d'une liste
    */
  def getDernierMot(liste: List[String]): String = {
    liste match {
      case Nil          => ""
      case head :: Nil  => head
      case head :: next => getDernierMot(next)
    }
  }

  /*

  ================================================================================================

  Une fois que l'utilisateur a vus le choix qui s'offre à lui il peut faire savoir le numero choisi
   -> reponseUtilisateur

  getReponse(reponseUtilisateur, listeReponse, langue)
    Est la fonction qui donne a l'avatar la liste avec la reponse a afficher
    Elle utilise les sous fonctions suivantes :
      - removeFirstAndLast(listeReponse , cpt)
        Pour nettoyer la liste
      - isoleValeur(reponseUtilisateur)
        Pour recupérer le num choisi par l'utilisateur
      - cleanCle(choixUtilisateur)
        pour récuperer le lieux choisi par l'utilisateur

  ================================================================================================


   */

  /** @param reponseUtilisateur choix par rapport aux réponses de la requete multiple
    * @param listeReponse liste des réponses de la requete multiple
    * @param langue de l'avatar
    * @return la liste qui sera affichee par l'avatar
    */
  def getReponse(
      reponseUtilisateur: String,
      listeReponse: List[String],
      langue: String
  ): List[String] = {
    var reponse: String = ""
    // retirer les élément inutiles pour avoir plus que les clé
    val listeReponseClean: List[String] = removeFirstAndLast(listeReponse, 0)
    var trouve: Boolean = false
    var cle: String = ""
    var compteur: Int = 1
    val valeurChoisie: String = isoleValeur(reponseUtilisateur)
    //retrouver la séléction de l'utilisateur
    for (eleRep <- listeReponseClean) {
      if (valeurChoisie == compteur.toString()) {
        cle = eleRep
        trouve = true
      }
      compteur += 1
    }
    // nettoyer la cle pour avoir plus que les valeur de la cle
    // : "1) Piscine Br´equigny" -> "Piscine Br´equigny"
    if (valeurChoisie == "0") {
      reponse = BDD.bddJNCRP.getOrElse(langue, "")
    } else {
      cle = cleanCle(cle)
      if (trouve) {
        reponse =
          BDD.bddAdr1.getOrElse(langue, "") + cle + BDD.bddAdr2.getOrElse(
            langue,
            ""
          ) + BDDEtendue.data.getOrElse(cle, "Je trouve pas")
      } else {
        reponse = BDD.bddJNCRP.getOrElse(langue, "")
      }
    }
    requeteEnCour = false
    List(reponse)
  }

  /** @param listeReponse choix de l'utilisateur
    * @param cpt
    * @return la liste avec les choix de l'utilisateur  épurée des phrases intro et outro
    */
  def removeFirstAndLast(listeReponse: List[String], cpt: Int): List[String] = {
    listeReponse match {
      case Nil      => Nil
      case h :: Nil => Nil
      case h :: t => {
        if (cpt == 0) removeFirstAndLast(t, 1)
        else h :: removeFirstAndLast(t, 1)
      }
    }
  }

  /** @param reponseUtilisateur
    * @return Le string contenant seulement la valeur choisie par l'utilisateur
    */
  def isoleValeur(reponseUtilisateur: String): String = {
    var valeur: String = ""
    val listeDeMot: List[String] = reponseUtilisateur.split(" ").toList

    for (ele <- listeDeMot) {
      // Teste si le string est une valeur composé de 1 ou plusieurs chiffres
      if (ele.matches("\\d+")) valeur = ele
    }
    valeur
  }

  /** @param choixUtilisateur
    * @return le lieux choisi par l'utilisateur
    */
  def cleanCle(choixUtilisateur: String): String = {
    var cle: String = ""
    var listeCle: List[String] = choixUtilisateur.split(" ").toList
    listeCle = listeCle.drop(1)
    cle = concactEspace(listeCle)
    cle
  }

  /** @param listeCle
    * @return une clé formee a partir des morceaux de la liste
    */
  def concactEspace(listeCle: List[String]): String = {
    listeCle match {
      case Nil      => ""
      case h :: Nil => h
      case h :: t   => h + " " + concactEspace(t)
    }
  }
}

/*
  Note de l'auteur :
  RocketMultiple un Nom de deck clash royale ?
  Une sorte de Rocket miroir
 */
