package interface

// Imports needed for working with Swing in Scala
import scala.swing._
import scala.swing.MainFrame
import java.awt.Color

import interface.UI_v2

/** Simple application for a smooth Scala/Swing introduction
  */
object InterfaceGraphique extends SimpleSwingApplication {

  /** top method : returns a Main Frame, that is open when the swing application
    * starts this method is required for extending a SimpleSwingApplication
    */
  def top: MainFrame = new UI_v2

  println("End of main function, but the program keeps running")
}
