package interface

import scala.swing._
import java.awt.Color
import scala.io.StdIn._
import scala.swing.event._
import java.awt.Toolkit
import java.io.File
import javax.imageio.ImageIO
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter
import java.awt.GraphicsEnvironment
import machine.AnalysePhrase
import machine.SyntheseVocal
import machine.Traduction

class UI_v2 extends MainFrame {

  var height = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.85
  var width = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.35

  val AP = new AnalysePhrase()

  title = "Giga Tchad GPT"
  preferredSize = new Dimension(width.toInt, height.toInt)
  resizable = false
  background = Color.BLACK

  val titlePanel = new FlowPanel {
    background = Color.BLACK

    val image = ImageIO.read(new File("doc/LogoBannieres.png"))
    val imageWidth = image.getWidth
    val imageHeight = image.getHeight
    preferredSize = new Dimension(image.getWidth, image.getHeight)

    override def paintComponent(g: Graphics2D): Unit = {
      super.paintComponent(g)

      val image = ImageIO.read(new File("doc/LogoBannieres.png"))
      val imageWidth = image.getWidth
      val imageHeight = image.getHeight

      val scaledWidth =
        (size.width * 1).toInt // redimensionner l'image à 80% de la largeur de la fenêtre
      val scaledHeight = (scaledWidth * imageHeight) / imageWidth

      val scaledImage = image.getScaledInstance(
        scaledWidth,
        scaledHeight,
        java.awt.Image.SCALE_SMOOTH
      )

      val x = (size.width - scaledWidth) / 2
      val y = (size.height - scaledHeight) / 2

      g.drawImage(scaledImage, x, y, null)
    }
  }

  //créer un champ de saisie de texte en bas de page
  val textField = new TextField(columns = 25)

  //créer un bouton à droite du champ de saisie de texte
  val button = new Button("Envoyer")

  //créer un bouton à droite du bouton Envoyer
  val reint = new Button("Reinitialiser")

  //créer une zone de texte pour afficher le texte saisi par l'utilisateur
  val textAreaUtil = new TextArea(rows = 1, columns = 36) {
    background = Color.BLACK
    font = new Font("", Font.Serif(2), 15)
    text = ""
    editable = false
    lineWrap = true
    wordWrap = true
    peer.setPreferredSize(null)
  }

//créer un volet de défilement pour la zone de texte afin d'afficher le texte saisi par l'utilisateur
  val scrollPaneUtil = new ScrollPane() {
    contents = textAreaUtil
    verticalScrollBarPolicy = ScrollPane.BarPolicy.AsNeeded
    horizontalScrollBarPolicy = ScrollPane.BarPolicy.AsNeeded
    background = Color.BLACK
    border = null
    peer.setPreferredSize(null)
  }

//créer un panneau pour contenir la zone de texte et la boîte de dialogue
  val chatPanelUtil = new BoxPanel(Orientation.Vertical) {
    contents += scrollPaneUtil
    contents += Swing.VStrut(0)
  }

  contents = new BorderPanel {
    layout(new BorderPanel {
      add(titlePanel, BorderPanel.Position.North)
      add(chatPanelUtil, BorderPanel.Position.Center)
    }) = BorderPanel.Position.Center
    layout(
      new BorderPanel() {
        add(
          new FlowPanel(FlowPanel.Alignment.Center)(button, reint),
          BorderPanel.Position.East
        )
        add(
          new FlowPanel(FlowPanel.Alignment.Center)(textField),
          BorderPanel.Position.Center
        )
      }
    ) = BorderPanel.Position.South
  }

  val screenSize = java.awt.Toolkit.getDefaultToolkit.getScreenSize
  val widthTF = (screenSize.width * 0.6).toInt
  textField.preferredSize = new Dimension(widthTF, 30)

  listenTo(button, reint)

  var question = ""
// réaction à l'appui sur la touche Entrée

  textField.action = new Action("Send") {
    def apply {
      button.doClick()
    }
  }

  centerOnScreen()

  reactions += {
    case ButtonClicked(`button`) =>
      textAreaUtil.foreground = Color.WHITE
      textAreaUtil.append("Requète :\n")
      question = textField.text //VARIABLE DE STOCKAGE
      question = question.dropWhile(_.isWhitespace)
      textAreaUtil.append(
        question + "\n"
      )

      textAreaUtil.append("\n")
      textAreaUtil.append("Réponse de la machine :\n")

      var reponse = AP.renvoieReponse(question)
      val SV = new SyntheseVocal
      for (str <- reponse) {
        textAreaUtil.append(str + "\n")
      }

      //Car nous ne possèdons pas de voix pour l'espagnol
      if (Traduction.detecteLangue(reponse) != "Espagnol") { SV.say(reponse) }

      textAreaUtil.append(" \n")

      val fontMetrics = textAreaUtil.peer.getFontMetrics(textAreaUtil.font)
      val charWidth = fontMetrics.stringWidth("_")

      textAreaUtil.append(
        " \n"
      )
      textField.text = "\n"

      // créer un highlighter pour souligner le texte
      val highlightPainter = new DefaultHighlightPainter(Color.GRAY)
      val highlighter = textAreaUtil.peer.getHighlighter()

      // souligner toutes les occurrences de "Réponse de la machine :"
      val response = "Réponse de la machine :"
      var responseStart = textAreaUtil.text.indexOf(response)
      while (responseStart != -1) {
        val responseEnd = responseStart + response.length()
        highlighter.addHighlight(responseStart, responseEnd, highlightPainter)
        responseStart = textAreaUtil.text.indexOf(response, responseEnd)
      }

      // souligner toutes les occurrences de "Requète :"
      val request = "Requète :"
      var requestStart = textAreaUtil.text.indexOf(request)
      while (requestStart != -1) {
        val requestEnd = requestStart + request.length()
        highlighter.addHighlight(requestStart, requestEnd, highlightPainter)
        requestStart = textAreaUtil.text.indexOf(request, requestEnd)
      }
    case EditDone(`textField`) =>
      button.doClick()
    case ButtonClicked(`reint`) =>
      textField.text = ""
      textAreaUtil.text = ""
      textAreaUtil.text = ""
      Traduction.langueActuelle = "Français"
      Traduction.langueEnCours = "Français"
      Traduction.changementEnCours = false
  }

}
