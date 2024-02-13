package machine

import java.io.IOException
import javax.sound.sampled.AudioInputStream
import marytts.LocalMaryInterface
import marytts.MaryInterface
import marytts.exceptions.MaryConfigurationException
import marytts.exceptions.SynthesisException
import marytts.util.data.audio.AudioPlayer

class SyntheseVocal extends library.SyntheseVocal {

  var langue: String = ""
  private var marytts: MaryInterface = null
  private var ap: AudioPlayer = null
  val tf = new TolerancesFautes

  marytts = new LocalMaryInterface()

  /** Synthétise et prononce à haute voix une liste de phrases.
    *
    * @param inputList une liste de chaînes de caractères représentant les phrases à synthétiser et prononcer.
    */
  def say(inputList: List[String]): Unit = {
    var lang = Traduction.langueActuelle match {
      case "Italien"  => marytts.setVoice("istc-lucia-hsmm")
      case "Anglais"  => marytts.setVoice("cmu-slt-hsmm")
      case "Allemand" => marytts.setVoice("dfki-pavoque-neutral-hsmm")
      case _          => marytts.setVoice("upmc-pierre-hsmm")
    }
    ap = new AudioPlayer()
    val mergeList = enleveCharSpe(concat(inputList))

    try {
      val audio: AudioInputStream = marytts.generateAudio(mergeList)
      ap.setAudio(audio)
      ap.start()
    } catch {
      case ex: SynthesisException =>
        System.err.println("Error saying phrase.")
        println(ex)
      case ex: IOException =>
        System.err.println("IO error playing audio.")
        println(ex)
    }
  }

  /** Concatène les éléments d'une liste de chaînes de caractères avec ", ".
    *
    * @param inputList la liste de chaînes de caractères à concaténer
    * @return la chaîne de caractères résultante de la concaténation de tous les éléments de la liste
    */
  private def concat(inputList: List[String]): String = {
    inputList match {
      case Nil          => ""
      case head :: next => head + ", " + concat(next)
    }
  }

  /** Enlève les caractères spéciaux de la chaîne de caractères spécifiée et la renvoie.
    *
    * @param str la chaîne de caractères contenant les caractères spéciaux à retirer
    * @return la chaîne de caractères sans les caractères spéciaux
    */
  private def enleveCharSpe(str: String): String = {
    var strN = str.replaceAll("–", "-")
    strN = strN.replaceAll("’", "'")
    strN
  }
}
