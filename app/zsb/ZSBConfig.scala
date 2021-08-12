package zsb

case class ZSBConfig(
    baseUrl: String,
    normalizeDetailUrl: String => String
)
