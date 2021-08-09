package controllers

import play.api.mvc.{AnyContent, Request}

trait QueryStringParser {
  def parseBoolQueryParam(key: String)(implicit r: Request[AnyContent]) =
    r.getQueryString(key).flatMap(_.toBooleanOption) getOrElse false
}
