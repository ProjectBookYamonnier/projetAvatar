package machine

import library.ToleranceFautes
import scala.util.matching.Regex
import scala.math.min
import java.util.Arrays
import scala.collection.mutable.ListBuffer
import java.text.Normalizer

class TolerancesFautes extends library.ToleranceFautes {

  /** Cette fonction permet de corriger la phrase de l'utilisateur en fonction de la base de données.
    * Si la base de données contient une clé "Mairie de Rennes" et que l'utilisateur entre "Ou est la Mairie Rennes",
    * la fonction renvoie "Ou est la Mairie de Rennes" en utilisant la distance de Levenshtein pour trouver les clés similaires
    * dans la base de données avec une tolérance de 1.
    *
    * @param phrase la phrase de l'utilisateur à corriger
    * @param baseDeDonnees la base de données contenant les clés et les valeurs associées
    * @return la phrase corrigée de l'utilisateur
    */
  def corrigerPhrase(
      phrase: String,
      baseDeDonnees: Map[String, String]
  ): String = {

    ////////////////////////Niveau 0//////////////////////////////
    //Une base de données de 1 seul mot

    val phraseInit = preparerPhrase(phrase)
    for ((cle, valeur) <- baseDeDonnees) {
      val cleInit = preparerPhrase(cle)
      val phraseDec = phraseInit.split(" ")
      for (element <- phraseDec) {
        if (element.length > 2 && distanceLevenshtein(element, cleInit) <= 1) {
          return cle
        }
      }
    }

    ////////////////////////Niveau 1//////////////////////////////
    //Manque d'une majuscule ou accent ou une lettre dans un mot simple

    for ((cle, valeur) <- baseDeDonnees) {
      val cleInit = preparerPhrase(cle)
      if (phraseInit.contains(cleInit) || phraseInit == cleInit) {
        return cle.trim()
      } else if (
        phraseInit.length > 2 && distanceLevenshtein(phraseInit, cleInit) <= 1
      ) {
        return cle
      }
    }

    ///////////////////////Niveau 2///////////////////////////////
    //Manque d'une lettre dans le mot
    val recherche = List("je recherche", "ou est donc", "je cherche")
    for (element <- recherche) {
      val elementInit = preparerPhrase(element)
      val phraseInitSansDet = preparerPhraseSansDet(phrase)
      if (
        phraseInitSansDet.contains(
          elementInit
        ) || phraseInitSansDet == elementInit
      ) {
        val res1 = enleverLdetTiret(
          preparerPhrase(remplacerChaine(phraseInitSansDet, element, ""))
        )
        for ((cle, valeur) <- baseDeDonnees) {
          val cleInit = enleverLdetTiret(preparerPhrase(cle))

          if (res1.length > 3 && distanceLevenshtein(res1, cleInit) <= 2) {
            return cle
          }

        }
        // return enleverLd(preparerPhrase(res1))
      }
    }
    return ""
  }

  /** Cette fonction prend une phrase en entrée et renvoie une nouvelle phrase
    * dans laquelle tous les mots commençant par "l'" ou "d'" ont été enlevés.
    *
    * @param phrase la phrase d'entrée à modifier
    * @return la nouvelle phrase sans les mots commençant par "l'" ou "d'"
    */
  def enleverLdetTiret(phrase: String): String = {
    val word = phrase.replaceAll("-", " ")
    val words = word.split("\\s+").filter(_.nonEmpty)
    val result = words.map(mot =>
      if (mot.toLowerCase().startsWith("l'")) {
        mot.substring(2)
      } else {
        mot
      }
    )
    val result2 = result.map(mot =>
      if (mot.toLowerCase().startsWith("d'")) {
        mot.substring(2)
      } else {
        mot
      }
    )
    result2.mkString(" ")
  }

  /** Cette fonction remplace toutes les occurrences d'une chaîne de caractères
    * dans une phrase par une autre chaîne de caractères donnée.
    * Les chaînes de caractères sont normalisées
    * en supprimant tous les caractères diacritiques et tous les caractères non-ASCII.
    * La recherche de la chaîne à remplacer est insensible à la casse et se limite aux mots entiers.
    *
    * @param phrase la phrase dans laquelle effectuer le remplacement
    * @param chaine la chaîne de caractères à remplacer dans la phrase
    * @param remplacement la chaîne de caractères à utiliser comme remplacement pour la chaîne à remplacer
    * @return  la phrase modifiée avec toutes les occurrences de la chaîne remplacées par la chaîne de remplacement
    */
  def remplacerChaine(
      phrase: String,
      chaine: String,
      remplacement: String
  ): String = {
    val chaineNormalisee = Normalizer
      .normalize(chaine, Normalizer.Form.NFD)
      .replaceAll("[^\\p{ASCII}]", "")
    val phraseNormalisee = Normalizer
      .normalize(phrase, Normalizer.Form.NFD)
      .replaceAll("[^\\p{ASCII}]", "")
    phraseNormalisee.replaceAll(s"(?i)\\b$chaineNormalisee\\b", remplacement)
  }

  /** Calcule la distance de Levenshtein entre deux chaînes de caractères, qui est définie comme
    * le nombre minimum de modifications à un caractère (ajouts, suppressions ou substitutions)
    * nécessaire pour transformer une chaîne en une autre.
    *
    * @param s1 la première chaîne de caractère
    * @param s2 la deuxième chaîne de caractères
    * @return la distance de Levenshtein entre s1 et s2
    */
  def distanceLevenshtein(s1: String, s2: String): Int = {
    if ((s1.length() <= 2 || s2.length() <= 2) && s1.equals(s2)) {
      0
    } else if (s1.length() <= 2 || s2.length() <= 2) {
      10
    } else {

      val m = s1.length
      val n = s2.length
      val d = Array.ofDim[Int](m + 1, n + 1)
      for (i <- 0 to m) d(i)(0) = i
      for (j <- 0 to n) d(0)(j) = j
      for (j <- 1 to n; i <- 1 to m) {
        val substitutionCost = if (s1(i - 1) == s2(j - 1)) 0 else 1
        d(i)(j) = min(
          min(d(i - 1)(j) + 1, d(i)(j - 1) + 1),
          d(i - 1)(j - 1) + substitutionCost
        )
      }
      d(m)(n)
    }
  }

  /** Cette fonction prend en entrée la phrase de l'utilisateur et remplace toutes les accents par leur lettre associé,
    * mets les Majuscules en minuscules et retire tous les déterminant
    *
    * @param s la phrase de l'utilisateur
    * @return une phrase sans majuscule, sans accent, sans déterminant et sans espaces de début et de fin
    */
  def preparerPhrase(s: String): String = {
    val determinantsRegex =
      "\\b(le|la|les|un|une|des|du|de|ce|cet|or|ni|car|cette|et|a|au|aux|mais|ou|donc)\\b"
    val sansAccents = s
      .replaceAll("[éèêë]", "e")
      .replaceAll("[àâä]", "a")
      .replaceAll("[ûüù]", "u")
      .replaceAll("[ôö]", "o")
      .replaceAll("[îï]", "i")
      .toLowerCase
      .trim // enlever les espaces de début et de fin

    val withoutDeterminants = sansAccents.replaceAll(determinantsRegex, " ")
    val words = withoutDeterminants.split("\\s+").filter(_.nonEmpty)
    val result = words.map(mot =>
      if (mot.toLowerCase().startsWith("l'")) {
        mot.substring(2)
      } else {
        mot
      }
    )
    val result2 = result.map(mot =>
      if (mot.toLowerCase().startsWith("d'")) {
        mot.substring(2)
      } else {
        mot
      }
    )
    result2.mkString(" ")
  }

  /** Cette fonction prend en entrée la phrase de l'utilisateur et remplace toutes les accents par leur lettre associé,
    * mets les Majuscules en minuscules et retire pas du tout les déterminant
    *
    * @param s la phrase de l'utilisateur
    * @return une phrase sans majuscule, sans accent, avec déterminant et sans espaces de début et de fin
    */
  def preparerPhraseSansDet(s: String): String = {
    val sansAccents = s
      .replaceAll("[éèêë]", "e")
      .replaceAll("[àâä]", "a")
      .replaceAll("[ûüù]", "u")
      .replaceAll("[ôö]", "o")
      .replaceAll("[îï]", "i")
      .toLowerCase
      .trim // enlever les espaces de début et de fin
    val words = sansAccents.split("\\s+").filter(_.nonEmpty)
    val result = words.map(mot =>
      if (mot.toLowerCase().startsWith("l'")) {
        mot.substring(2)
      } else {
        mot
      }
    )
    val result2 = result.map(mot =>
      if (mot.toLowerCase().startsWith("d'")) {
        mot.substring(2)
      } else {
        mot
      }
    )
    result2.mkString(" ")
  }

}
