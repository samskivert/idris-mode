//
// Scaled Idris Mode - a Scaled major mode for editing Idris code
// http://github.com/scaled/idris-mode/blob/master/LICENSE

package scaled.idris

import scaled._
import scaled.code.Indenter
import scaled.grammar._
import scaled.code.{CodeConfig, Commenter}

object IdrisConfig extends Config.Defs {
  import EditorConfig._
  import CodeConfig._
  import GrammarConfig._

  // maps TextMate grammar scopes to Scaled style definitions
  val effacers = List(
    effacer("comment.line", commentStyle),
    effacer("comment.block.string", stringStyle),
    effacer("comment.block", docStyle),
    effacer("constant", constantStyle),
    effacer("invalid", warnStyle),
    effacer("keyword", keywordStyle),
    effacer("string", stringStyle),
    effacer("variable", variableStyle),
    effacer("entity.name.function", functionStyle),
    effacer("entity.name.type.module", moduleStyle),
    effacer("entity.name.type.variant", typeStyle),
    effacer("support.other.module", moduleStyle),
    effacer("storage", variableStyle)
  )

  val grammars = resource("Idris.ndf")(Grammar.parseNDFs)
}

@Major(name="idris",
       tags=Array("code", "project", "idris"),
       pats=Array(".*\\.idr"),
       desc="A major mode for editing Idris code.")
class IdrisMode (env :Env) extends GrammarCodeMode(env) {

  override def dispose () {} // nada for now

  override def configDefs = IdrisConfig :: super.configDefs
  override def grammars = IdrisConfig.grammars.get
  override def effacers = IdrisConfig.effacers

  override def keymap = super.keymap.
    bind("self-insert-command", "'"); // don't auto-pair single quote

  // HACK: leave indent as-is
  override def computeIndent (row :Int) :Int = Indenter.readIndent(buffer, Loc(row, 0))

  override val commenter = new IdrisCommenter()
}
