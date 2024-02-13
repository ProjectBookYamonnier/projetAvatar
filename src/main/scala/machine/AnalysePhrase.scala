package machine

class AnalysePhrase extends library.AnalysePhrase {

  var dictionnaireAdresse = BaseDeDonnees
  val trad = Traduction

  val multipleRqu = new RocketMultiple
  val lint = new Linternaute

  /** @param s La commande entree par l'utilisateur
    *
    * @return La phrase qui repond à l'utilisateur
    */
  def renvoieReponse(s: String): List[String] = {
    val langue: String = trad.detecteLangue(motDansListe(s.toLowerCase()))
    val resto: Boolean = lint.restoDansRequete(motDansListe(s))

    if (langue != "" && langue != trad.langueActuelle) {
      trad.changementEnCours = true
      trad.langueActuelle = langue
      trad.demandeConfirmation() :: Nil
    } else if (trad.changementEnCours && trad.repondOuiDansLangueCourante(s)) {
      trad.changementEnCours = false
      trad.confirmeDansLangueCourante() :: Nil
    } else if (trad.changementEnCours) {
      trad.changementLangueSuivante()
      trad.demandeConfirmation() :: Nil
    } else {
      var reponse: List[String] = List()
      if (multipleRqu.requeteEnCour) {
        reponse = multipleRqu.getReponse(
          s,
          multipleRqu.derniereListe,
          trad.langueActuelle
        )
      } else {
        val tf = new TolerancesFautes
        val extractPointInterro = s.replaceAll("\\?", "")
        val questionCorrige =
          tf.corrigerPhrase(extractPointInterro, dictionnaireAdresse.data)

        var motCle: String = trouveMotCleOUChaineDeMotCle(extractPointInterro)
        var motCleCorr: String = trouveMotCleOUChaineDeMotCle(questionCorrige)

        val salut = new Discussion
        val salutation: String =
          salut.salutation(s.replaceAll("\\,", ""))
        if (salutation != salut.ExceptionMotSalutationNonTrouve) {
          reponse = trad.salueDansLangueCourante() :: reponse
        }
        if (
          s.split(" ")
            .length > 1 && salutation != salut.ExceptionMotSalutationNonTrouve
          || salutation == salut.ExceptionMotSalutationNonTrouve
        ) {
          if (resto) {
            val nom = lint.nomDe(motDansListe(s))
            val adresse = lint.adresseDe(motDansListe(s))

            if (nom == "" || adresse == "") {
              reponse = reponse ++ List(trad.neComprendsPasDansLangueCourante())
            } else {
              reponse = reponse ++ List(
                (trad.donneAdresse1DansLangueCourante() + nom + trad
                  .donneAdresse2DansLangueCourante() + adresse)
              )
            }

          } else {
            if (motCle == ExceptionMotCleNonTrouve) {
              if (motCleCorr == ExceptionMotCleNonTrouve) {
                val listeReqMult: List[String] =
                  multipleRqu.generePossibiliteReponse(
                    s,
                    trad.langueActuelle
                  )
                if (multipleRqu.requeteEnCour) {
                  reponse = listeReqMult
                } else {
                  reponse =
                    reponse ++ List(trad.neComprendsPasDansLangueCourante())
                }
              } else {
                reponse = reponse ++ List(
                  (trad.donneAdresse1DansLangueCourante() + faitPartie(
                    motCleCorr
                  ) + trad.donneAdresse2DansLangueCourante() + retourneAdresse(
                    motCleCorr
                  ))
                )
              }
            } else {
              reponse = reponse ++ List(
                trad.donneAdresse1DansLangueCourante() + faitPartie(
                  motCle
                ) + trad
                  .donneAdresse2DansLangueCourante() + retourneAdresse(
                  motCle
                )
              )
            }
          }

        }
      }
      reponse
    }

  }

  /** @param mot un string étant l'objet de la requête de l'utilisateur
    *
    * @return le string d'importance pour chacun des objet de requête
    */
  def faitPartie(mot: String): String = {
    if (mot.contains("?")) mot.replaceAll("\\?", "")
    var lst = List[String]()
    lst = List(
      "Fret SNCF"
    )
    if (lst.contains(mot))
      return "Service Fret - SNCF"
    lst = List(
      "complexe sportif du moulin"
    )
    if (lst.contains(mot))
      return "Complexe sportif Moulin du Comte"
    lst = List(
      "accompagnement femme enceinte"
    )
    if (lst.contains(mot))
      return "Service d'accompagnement des femmes enceintes en difficulté – Département d’Ille-et-Vilaine (PMI) - CHU"
    lst = List(
      "Mairie",
      "Mairie de Rennes",
      "Hôtel de ville"
    )
    if (lst.contains(mot)) return "Mairie de Rennes"
    lst = List(
      "Gare",
      "Gare SNCF"
    )
    if (lst.contains(mot)) return "Gare SNCF"
    lst = List(
      "TNB",
      "Théâtre de Bretagne",
      "Théâtre National de Bretagne"
    )
    if (lst.contains(mot)) return "Théâtre National de Bretagne"
    lst = List(
      "Théâtre La Paillette",
      "la Paillette"
    )
    if (lst.contains(mot)) return "Théâtre La Paillette"
    mot
  }

  /** @param s La commande entree par l'utilisateur
    *
    * @return La liste de chaque mot dans la phrase.
    */
  def motDansListe(s: String): List[String] = {
    if (s.trim().length == 0) List()
    else
      s.replaceAll("[?.,!:']", " ")
        .replaceAll("  ", " ")
        .replaceAll("   ", " ")
        .split(" ")
        .toList
  }

  /** @param entreeUtilisateur La commande entree par l'utilisateur modifiee ou non
    *
    * @return Le mot clé trouvé dans la phrase composé ou non
    */
  def trouveMotCleOUChaineDeMotCle(entreeUtilisateur: String): String = {

    var continue: Boolean = true
    var motCle: String = ""
    while (continue) {
      for ((cle, valeur) <- dictionnaireAdresse.data) {
        if (entreeUtilisateur.contains(cle)) {
          motCle = cle
          continue = false
        }
      }
      continue = false
    }
    if (motCle == "") ExceptionMotCleNonTrouve
    else {
      motCle
    }
  }

  /** @param cle le mot cle entré par l'utilisateur
    *
    * @return vrai si il se trouve dans le dico et faux sinon
    */
  def trouveCleDansDico(cle: String): Boolean = {
    dictionnaireAdresse.data.contains(faitPartie(cle))
  }

  /** @param cle le mot cle entré par l'utilisateur
    *
    * @return la valeur correspondante au mot cle dans le dictionnaire
    */
  def retourneAdresse(cle: String): String = {
    dictionnaireAdresse.data.getOrElse(faitPartie(cle), "")
  }

}
