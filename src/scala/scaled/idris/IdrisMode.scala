//
// Scaled Idris Mode - a Scaled major mode for editing Idris code
// http://github.com/scaled/idris-mode/blob/master/LICENSE

package scaled.idris

import scaled._
import scaled.code.Indenter
import scaled.grammar._
import scaled.code.{CodeConfig, Commenter}

@Plugin(tag="textmate-grammar")
class IdrisGrammarPlugin extends GrammarPlugin {
  import EditorConfig._
  import CodeConfig._

  override def grammars = Map("source.idris" -> "Idris.ndf")

  override def effacers = List(
    effacer("comment.line", commentStyle),
    effacer("comment.block.string", stringStyle),
    effacer("comment.line.triple-bar", docStyle),
    effacer("constant", constantStyle),
    effacer("invalid", warnStyle),
    effacer("keyword.directive", moduleStyle),
    effacer("keyword", keywordStyle),
    effacer("string", stringStyle),
    effacer("variable", variableStyle),
    effacer("entity.name.function", functionStyle),
    effacer("entity.name.type.module", moduleStyle),
    effacer("entity.name.type", typeStyle),
    effacer("support.other.module", moduleStyle),
    effacer("storage", variableStyle)
  )
}

@Major(name="idris",
       tags=Array("code", "project", "idris"),
       pats=Array(".*\\.idr"),
       desc="A major mode for editing Idris code.")
class IdrisMode (env :Env) extends GrammarCodeMode(env) {

  override def dispose () :Unit = {} // nada for now

  override def langScope = "source.idris"

  override def keymap = super.keymap.
    bind("self-insert-command", "'"); // don't auto-pair single quote

  // HACK: leave indent as-is
  override def computeIndent (row :Int) :Int = Indenter.readIndent(buffer, Loc(row, 0))

  override val commenter = new IdrisCommenter()
}
