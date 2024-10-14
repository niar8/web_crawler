package web_crawler

import org.jsoup.Jsoup
import zio._
import zio.http._
import zio.json._

object WebCrawlerApp extends ZIOAppDefault {

  final case class FetchTitlesRequest(urls: List[String])

  private object FetchTitlesRequest {
    implicit val decoder: JsonDecoder[FetchTitlesRequest] = DeriveJsonDecoder.gen
  }

  final case class UrlTitlePair(url: String, title: String)

  private object UrlTitlePair {
    implicit val encoder: JsonEncoder[UrlTitlePair] = DeriveJsonEncoder.gen
  }

  final case class FetchTitlesResponse(info: List[UrlTitlePair])

  private object FetchTitlesResponse {
    implicit val encoder: JsonEncoder[FetchTitlesResponse] = DeriveJsonEncoder.gen
  }

  private def fetchTitle(url: String): UIO[UrlTitlePair] =
    ZIO.succeed(Jsoup.connect(url).get()).map(doc => UrlTitlePair(url, doc.title()))

  private val app: Routes[Any, Nothing] = Routes(
    Method.POST / "fetch_titles" -> handler {
      (req: Request) =>
        for {
          body <- req.body.asString
          res <- body.fromJson[FetchTitlesRequest] match {
            case Left(e) =>
              ZIO
                .logErrorCause(s"Failed to parse the input", Cause.fail(e))
                .as(Response.text(e).status(Status.BadRequest))
            case Right(FetchTitlesRequest(urls)) =>
              ZIO.foreachPar(urls)(fetchTitle).map { urlTitlePairs =>
                Response.json(FetchTitlesResponse(info = urlTitlePairs).toJson)
              }
          }
        } yield res
    }
  ).sandbox

  val run: UIO[ExitCode] =
    Server.serve(app).provide(Server.default).exitCode
}