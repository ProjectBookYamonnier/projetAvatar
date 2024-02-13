package machine

import library.OutilsWebObjet
import library.Html
import library.Tag
import library.Texte

class Linternaute extends library.Linternaute{

  case object ExceptionPasTrouve extends Exception

  val bddRestaurants: Map[String, String] = Map(
    ("pizzeria" -> ""),
    ("creperie" -> ""),
    ("restaurant" -> ""),
    ("ristorante" -> ""),
    ("restaurante" -> "")
  )

  val tf = new TolerancesFautes

  /** @param entreeUtilisateur la requete de l'utilisateur sous forme de liste de chacun de ses mots
    * @return l'adresse de la creperie/pizzeria/restaurant
    */
  def adresseDe(entreeUtilisateur: List[String]): String = {
    var urlRequete = cherchePageRequete(recupereNom(entreeUtilisateur))

    if (urlRequete != "") {
      var urlResto = cherchePageResto(
        OutilsWebObjet.obtenirHtml(
          cherchePageRequete(recupereNom(entreeUtilisateur))
        )
      )
      if (urlResto != "") {
        chercheAdresseResto(
          OutilsWebObjet.obtenirHtml(
            urlResto
          )
        )
      } else {
        ""
      }
    } else {
      ""
    }

  }

  /** @param entreeUtilisateur la requete de l'utilisateur sous forme de liste de chacun de ses mots
    * @return le nom de la creperie/pizzeria/restaurant
    */
  def nomDe(entreeUtilisateur: List[String]): String = {
    var urlRequete = cherchePageRequete(recupereNom(entreeUtilisateur))

    if (urlRequete != "") {
      var urlResto = cherchePageResto(
        OutilsWebObjet.obtenirHtml(
          cherchePageRequete(recupereNom(entreeUtilisateur))
        )
      )
      if (urlResto != "") {
        chercheNomResto(
          OutilsWebObjet
            .obtenirHtml(
              urlResto
            )
        ).replaceAll("&#039;", "'")
      } else {
        ""
      }
    } else {
      ""
    }

  }

  /** @param entreeUtilisateur la requete de l'utilisateur sous forme de liste de chacun de ses mots
    * @return le nom de la creperie/pizzeria/restaurant
    */
  def recupereNom(entreeUtilisateur: List[String]): List[String] = {
    entreeUtilisateur match {
      case head :: next =>
        if (bddRestaurants.contains(tf.corrigerPhrase(head, bddRestaurants))) {
          next
        } else { recupereNom(next) }
      case Nil => Nil
    }
  }

  /** @param entreeUtilisateur la requete de l'utilisateur sous forme de liste de chacun de ses mots
    * @return si il y a les mots de restaurants dans la requête
    */
  def restoDansRequete(entreeUtilisateur: List[String]): Boolean = {
    entreeUtilisateur match {
      case head :: next =>
        if (bddRestaurants.contains(tf.corrigerPhrase(head, bddRestaurants))) {
          true
        } else { restoDansRequete(next) }
      case Nil => false
    }
  }

  def listeMotsVersString(l: List[String]): String = {
    l match {
      case head :: next => head + " " + listeMotsVersString(next)
      case Nil          => ""
    }
  }

  /** @param nom nom du restaurant/... à chercher
    * @return l'adresse internet de la page web associée à la recherche Linternaute
    */
  def cherchePageRequete(nom: List[String]): String = {
    var res: String =
      "https://www.linternaute.com/restaurant/guide/ville-rennes-35000/?name="
    res + cherchePageRequeteRecursif(nom)
  }

  def cherchePageRequeteRecursif(nom: List[String]): String = {
    nom match {
      case head :: Nil  => head
      case head :: next => head + "+" + cherchePageRequeteRecursif(next)
      case Nil          => ""
    }
  }

  /** @param pageRequete la page html de la requete faite au site
    * @return le lien du premier restaurant
    */
  def cherchePageResto(pageRequete: Html): String = {
    pageRequete match {
      case Tag(name, attributes, children) =>
        if (name == "ul" && attributes.contains(("id", "jSearchResults"))) {
          cherchePremierResultatPageResto(children)
        } else { cherchePageRestos(children) }
      case Texte(content) => ""
    }
  }

  def cherchePageRestos(pageRequetes: List[Html]): String = {
    pageRequetes match {
      case head :: next => cherchePageResto(head) + cherchePageRestos(next)
      case Nil          => ""
    }
  }

  def cherchePremierResultatPageResto(pageRequete: List[Html]): String = {
    var lienrelatif = ""
    pageRequete match {
      case _ :: Tag(
            "li",
            _,
            Texte(_) :: Tag(
              _,
              _,
              Texte(_) :: Tag(
                _,
                _,
                Texte(_) :: Tag(
                  _,
                  List((_, lienrelatif)),
                  _
                ) :: _
              ) :: _
            ) :: _
          ) :: next => {
        "https://www.linternaute.com" + lienrelatif
      }
      case _ => {
        ""
      }
    }
  }

  /** @param pageRequete la page html d'un restaurant
    * @return l'adresse du restaurant
    */
  def chercheAdresseResto(pageRequete: Html): String = {
    pageRequete match {
      case Tag(name, attributes, children) =>
        if (
          name == "li" && attributes.contains(("class", "icomoon-location"))
        ) {
          chercheNomAdresseResto(children)
        } else { chercheAdresseRestos(children) }
      case Texte(content) => ""
    }
  }

  def chercheAdresseRestos(pageRequetes: List[Html]): String = {
    pageRequetes match {
      case head :: next =>
        chercheAdresseResto(head) + chercheAdresseRestos(next)
      case Nil => ""
    }
  }

  def chercheNomAdresseResto(pageRequete: List[Html]): String = {
    pageRequete match {
      case Texte(_) :: Tag(_, _, Texte(adresse) :: _) :: _ => adresse
      case _                                               => "pas trouvé"

    }
  }

  /** @param pageRequete la page html d'un restaurant
    * @return le nom du restaurant
    */
  def chercheNomResto(pageRequete: Html): String = {
    pageRequete match {
      case Tag(name, attributes, children) =>
        if (
          name == "h1" && (attributes.contains(
            ("class", "bu_restaurant_title_xl")
          ) || attributes.contains(
            ("itemprop", "name")
          ))
        ) {
          children match {
            case Texte(nom) :: _ => nom
            case _               => ""
          }
        } else { chercheNomRestos(children) }
      case Texte(content) => ""
    }
  }

  def chercheNomRestos(pageRequetes: List[Html]): String = {
    pageRequetes match {
      case head :: next =>
        chercheNomResto(head) + chercheNomRestos(next)
      case Nil => ""
    }
  }

}
