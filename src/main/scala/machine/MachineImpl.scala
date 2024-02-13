package machine

import machine.Traduction

object MachineImpl extends MachineDialogue {

  var AP = new AnalysePhrase()
  def ask(s: String): List[String] = AP.renvoieReponse(s)

  // Pour la partie test par le client
  def reinit(): Unit = {

    Traduction.langueActuelle = "Français"
    Traduction.langueEnCours = "Français"
    Traduction.changementEnCours = false
    AP = new AnalysePhrase()
  }
  def test(l: List[String]): List[String] = rec(l)

  def rec(l: List[String]): List[String] = {
    l match {
      case Nil         => List("")
      case head :: Nil => ask(head)
      case head :: tl  => ask(head) ++ rec(tl)
    }
  }
}
