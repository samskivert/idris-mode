//
// Scaled Idris Mode - a Scaled major mode for editing Idris code
// http://github.com/scaled/idris-mode/blob/master/LICENSE

package scaled.idris

import scaled._
import scaled.code.Commenter

/** Extends [[Commenter]] with some Idris smarts. */
class IdrisCommenter extends Commenter {
  import scaled.code.CodeConfig._

  override def linePrefix  = ""
  override def blockOpen = "(*"
  override def blockClose = "*)"
  override def blockPrefix = ""
  override def docPrefix   = ""

  // look for longer prefix first, then shorter
  override def commentDelimLen (line :LineV, col :Int) :Int = {
    if (line.matches(blockPrefixM, col)) blockPrefixM.matchLength
    else if (line.matches(linePrefixM, col)) linePrefixM.matchLength
    else 0
  }
}
