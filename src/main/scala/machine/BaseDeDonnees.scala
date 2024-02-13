package machine

import javax.xml.transform.Source
import scala.xml.XML

object BaseDeDonnees extends library.BaseDeDonnees {

  /** Renvoie une map de paires clé-valeur contenant des informations sur divers lieux et services.
    * La fonction lit les données d'un fichier texte et associe les noms similaires
    * de chaque lieu ou service aux adresses correspondantes.
    * adresses correspondantes. La fonction récupère également des informations supplémentaires
    * dans un fichier XML et les ajoute à la carte. Enfin, la fonction ajoute manuellement
    * des correspondances supplémentaires à la carte.
    *
    * @return une map de paires clé-valeur de chaînes de caractères contenant des informations sur divers lieux et services
    */
  val data: Map[String, String] = {
    var map = Map[String, String]()
    val fileName2 = "doc/DonneesInitiales.txt"
    val file2 = scala.io.Source.fromFile(fileName2)(scala.io.Codec.UTF8)
    try {
      for (line <- file2.getLines) {
        val tab = line.split(";")
        val lstSimili = similitude(tab(0))
        for (key <- lstSimili) {
          map += (key -> tab(1))
        }
      }
    } finally {
      file2.close()
    }
    val lstXML = getInfoXML()
    for ((lieux, adresse) <- lstXML) {
      map += (lieux -> adresse)
    }
    map += (("accompagnement femme enceinte" -> map.getOrElse(
      "Service d'accompagnement des femmes enceintes en difficulté – Département d’Ille-et-Vilaine (PMI) - CHU",
      "Nope"
    )), ("complexe sportif du moulin" -> map.getOrElse(
      "Complexe sportif Moulin du Comte",
      "Nope"
    )))
    map += ("Fret SNCF" -> map.getOrElse(
      "Service Fret - SNCF",
      "Nope"
    ))
    map
  }

  /** Renvoie une liste de chaînes de caractères représentant les variations possibles ou les noms similaires d'un mot donné.
    * La fonction supprime d'abord tout espace en tête de la chaîne d'entrée, puis vérifie si la chaîne
    * correspond à un nom prédéfini dans une liste de mots. Si une correspondance est trouvée,
    * la fonction renvoie une liste de toutes les variations possibles du mot correspondant,
    * sinon elle renvoie une liste de toutes les variations possibles du mot correspondant.
    * variations possibles du mot correspondant, sinon la fonction renvoie une liste contenant le mot original.
    *
    * @param mot le mot d'entrée pour trouver des noms similaires à
    * @return une liste de chaînes de caractères représentant les variations possibles ou les noms similaires du mot saisi
    */
  private def similitude(mot: String): List[String] = {
    var mots = mot.dropWhile(_.isWhitespace)
    var lst = List[String]()
    lst = List(
      "Mairie",
      "Mairie de Rennes",
      "Hôtel de ville"
    )
    if (lst.contains(mots)) {
      return (
        List(
          "Mairie",
          "Mairie de Rennes",
          "Hôtel de ville"
        )
      )
    }
    lst = List(
      "Gare",
      "Gare SNCF"
    )
    if (lst.contains(mots)) { return List("Gare", "Gare SNCF") }
    lst = List(
      "TNB",
      "Théâtre de Bretagne",
      "Théâtre National de Bretagne"
    )
    if (lst.contains(mots)) {
      return List(
        "TNB",
        "Théâtre de Bretagne",
        "Théâtre National de Bretagne"
      )
    }
    lst = List(
      "Théâtre La Paillette",
      "la Paillette"
    )
    if (lst.contains(mots)) {
      return List(
        "Théâtre La Paillette",
        "la Paillette"
      )
    }
    List(mots)
  }

  /** @return une liste de tuples représentant le nom et l'adresse de chaque organisation dans un fichier XML.
    */
  def getInfoXML(): List[(String, String)] = {
    val rootNode = XML.loadFile("doc/vAr.xml")
    // Selection de ts les elements organisation du XML
    val organisations = (rootNode \\ "data" \ "organization").toList
    // Selection des organisations a Rennes
    val organisationsRennes = organisations.filter(e =>
      (e \\ "addresses" \ "address" \ "city").text == "Rennes"
    )
    organisationsRennes.map(e =>
      (
        (e \ "name").text,
        if ((e \\ "street" \ "name").text == "") {
          "indéfini"
        } else {
          if ((e \\ "street" \ "number").text == "") {
            (e \\ "street" \ "name").text
          } else {
            (e \\ "street" \ "number").text + ", " + (e \\ "street" \ "name").text
          }
        }
      )
    )
  }

  /*
  def getInfoXML(): List[(String, String)] = {
    val rootNode = XML.loadFile("doc/vAr.xml")
    // Selection de ts les elements organisation du XML
    val organisations = (rootNode \\ "data" \ "organization").toList

    organisations.map(e =>
      (
        (e \ "name").text,
        if ((e \\ "street" \ "name").text == "") {
          "indéfini"
        } else {
          if ((e \\ "street" \ "number").text == "") {
            (e \\ "street" \ "name").text
          } else {
            (e \\ "street" \ "number").text + ", " + (e \\ "street" \ "name").text
          }
        }
      )
    )
  }
   */

}
