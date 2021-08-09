package mensa.legend

import parser.PrimitivesParser._

import scala.xml.{Elem, NodeSeq}

class LegendParser {

  def parseLegend(elem: Elem): Seq[Additive] = {
    val de = elem \ "de"
    val en = elem \ "en"
    val deAdditives = parseAdditives(de \ "additives")
    val enAdditives = parseAdditives(en \ "additives")
    val deAllergens = parseAdditives(de \ "allergens")
    val enAllergens = parseAdditives(en \ "allergens")
    val deOthers = parseAdditives(de \ "others")
    val enOthers = parseAdditives(en \ "others")

    mergeAdditives(deAdditives, enAdditives) ++
      mergeAdditives(deAllergens, enAllergens) ++
      mergeAdditives(deOthers, enOthers)
  }

  private def mergeAdditives(
      des: Seq[(String, Option[Int])],
      ens: Seq[(String, Option[Int])]
  ): Seq[Additive] = {
    if (des.size != ens.size) return Seq.empty

    des.zip(ens).collect {
      case (de, en) if de._2.isEmpty | en._2.isEmpty =>
        Additive(de._1, en._1, None)
      case (de, en) if de._2 == en._2 =>
        Additive(de._1, en._1, de._2)
    }
  }

  def parseAdditives(seq: NodeSeq): Seq[(String, Option[Int])] =
    (seq \ "item").map(parseItem)

  def parseItem(item: NodeSeq): (String, Option[Int]) = {
    val id = (item \@ "id").toIntOption
    val label = parseString(item)
    (label, id)
  }
}
