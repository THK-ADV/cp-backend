package parser

import scala.xml.NodeSeq

object PrimitivesParser {
  def parseString(seq: NodeSeq): String =
    seq.text.trim

  def parseNonEmptyString(seq: NodeSeq): Option[String] = {
    val s = parseString(seq)
    if (s.nonEmpty) Some(s) else None
  }

  def parseInts(seq: NodeSeq): List[Int] = {
    val s = parseString(seq)
    if (s.isEmpty) Nil
    else
      s
        .split(", ")
        .map(_.toIntOption)
        .collect { case Some(a) => a }
        .toList
  }

  def parseStrings(seq: NodeSeq): List[String] = {
    val s = parseString(seq)
    if (s.isEmpty) Nil
    else s.split(", ").toList
  }

  def parseDouble(seq: NodeSeq): Option[Double] =
    parseString(seq)
      .replace(',', '.')
      .toDoubleOption
}
