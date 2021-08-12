package di

import zsb.ZSBConfig

import javax.inject.Provider

class ZSBConfigProvider extends Provider[ZSBConfig] {
  override val get = {
    val baseUrlPrefix = "https://www.th-koeln.de"

    ZSBConfig(
      baseUrlPrefix + "/studium/angebote-fuer-studierende_20698.php",
      url => {
        if (url.startsWith("/studium") && !url.startsWith(baseUrlPrefix))
          baseUrlPrefix + url
        else
          url
      },
      url => {
        baseUrlPrefix + url
      }
    )
  }
}
