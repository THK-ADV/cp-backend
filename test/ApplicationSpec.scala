import play.api.inject.guice.{GuiceApplicationBuilder, GuiceableModule}

trait ApplicationSpec {

  // import play.api.inject.bind
  protected def bindings: Seq[GuiceableModule] = Seq.empty

  val app = new GuiceApplicationBuilder()
    .overrides(bindings: _*)
    .build()
}
