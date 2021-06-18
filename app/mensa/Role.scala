package mensa

import play.api.libs.json.{JsString, Writes}

sealed trait Role {
  def label: String
}

object Role {
  case object Student extends Role {
    override val label = "student"
  }

  case object Employee extends Role {
    override val label = "employee"
  }

  case object Guest extends Role {
    override val label = "guest"
  }

  implicit val writes: Writes[Role] =
    Writes[Role](r => JsString(r.label))
}
