package sprest.examples.slick

import spray.routing.SimpleRoutingApp
import sprest.routing.RestRoutes

trait Routes extends RestRoutes { this: SimpleRoutingApp with DB =>
  import spray.routing.Directives._
  import spray.httpx.SprayJsonSupport._
  import spray.httpx.encoding.Gzip
  import spray.json._

  def js = pathPrefix("js" / Rest) { fileName =>
    get {
      encodeResponse(Gzip) { getFromResource(s"js/$fileName") }
    }
  }

  def css = pathPrefix("css" / Rest) { fileName =>
    get {
      getFromResource(s"css/$fileName")
    }
  }

  def index = path("") {
    get {
      getFromResource("html/index.html")
    }
  }

  def bootstrap = pathPrefix("bootstrap" / Rest) { fileName =>
    get {
      getFromResource(s"twitter/bootstrap/$fileName")
    }
  }

  def publicAssets = js ~ css ~ bootstrap

  def api = pathPrefix("api") {
    dynamic(restInt("todos", ToDos) ~
      restInt("reminders", Reminders))
  }

  def routes = index ~ publicAssets ~ api
}
