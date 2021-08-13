package zsb

case class ZSBConfig(
    baseUrl: String,
    normalizeDetailUrl: String => String,
    normalizeImageUrl: String => String
)
