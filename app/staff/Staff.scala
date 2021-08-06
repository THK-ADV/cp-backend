package staff

case class Staff(
    name: String,
    detailUrl: String,
    tel: Option[String],
    email: Option[String]
)
