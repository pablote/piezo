package com.lucidchart.piezo.admin

import ch.qos.logback.classic.pattern.TargetLengthBasedClassNameAbbreviator
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._
import play.api.{Logger, Play}

import scala.collection.JavaConversions._
import scala.util.control.NonFatal

object RequestStatCollector extends EssentialFilter {
  private val statLogger = Logger(this.getClass.getName + ".request")
  private val logger = Logger(this.getClass.getName)
//  private val defaultRequestHeaders = Play.current.configuration.getStringList("stats.request.headers.include").map(_.toList)
//    .getOrElse(List[String]())
//  private val abbreviator = new TargetLengthBasedClassNameAbbreviator(
//    Play.current.configuration.getInt("stats.controller.maxLength").getOrElse(50))
//  private val omitFields: List[String] = Play.current.configuration.getStringList("stats.fields.omit").map(_.toList)
//    .getOrElse(List[String]())
//  private val omitControllers: List[String] = Play.current.configuration.getStringList("stats.controllers.omit").map(_.toList)
//    .getOrElse(List[String]())

  def logStats(start: Long, request: RequestHeader, result: Result): Result = {
//    try {
//      val end = System.nanoTime()
//      val diff = (end - start) / (1000L * 1000L)
//
//      val controller: String = request.tags.getOrElse(play.api.Routes.ROUTE_CONTROLLER, "")
//      val controllerMethod: String = request.tags.getOrElse(play.api.Routes.ROUTE_ACTION_METHOD, "")
//
//      val requestHeaderStats: List[(String, String)] = defaultRequestHeaders.flatMap(header =>
//        request.headers.getAll(header).map(value => header -> value))
//      val controllerName = controller + "." + controllerMethod
//      val controllerShortName = abbreviator.abbreviate(controllerName)
//      val requestContentLength: Int = request.headers.get("Content-Length").map(_.toInt).getOrElse(0)
//      val responseContentLength: Int = result.header.headers.get("Content-Length").map(_.toInt).getOrElse(0)
//      val stats = scala.collection.mutable.Map[String, Any](
//        "hst" -> request.host,
//        "pth" -> request.path,
//        "cntrllr" -> controllerShortName,
//        "mthd" -> request.method,
//        "tm" -> diff,
//        "stts" -> result.header.status.toString
//      ) ++ requestHeaderStats
//      if (request.rawQueryString.length > 0) stats.put("qry", request.rawQueryString)
//      if (requestContentLength > 0) stats.put("rqcl", requestContentLength)
//      if (responseContentLength > 0) stats.put("rscl", responseContentLength)
//
//      val filteredStats = stats.filterNot(stat => omitFields.contains(stat._1))
//      if (!omitControllers.contains(controllerName)) {
//        log(filteredStats)
//      }
//    } catch {
//      case NonFatal(e) => logger.error("Exception caught calculating stats", e)
//    }

    result
  }

  protected def log(stats: collection.Map[String, Any]): String = {
    val message = stats.map { case (key: String, value: Any) =>
      val sb = new StringBuilder()
      sb.append('"')
        .append(key)
        .append('"')
        .append(":")
      value match {
        case stringVal: String =>
          sb.append('"')
            .append(value)
            .append('"')
        case intVal @ (_: Byte | _: Short | _: Int | _: Long | _: Float | _: Double) =>
          sb.append(value)
        case _ =>
          logger.warn("No case defined for key: " + key + " and value " + value)
          sb.append("\"\"")
      }
    } mkString(",")
    statLogger.info(message)
    message
  }
  
  def apply(next: EssentialAction) = EssentialAction { request: RequestHeader =>
      val start = System.currentTimeMillis
    
      next(request).map { result =>
        logStats(start, request, result)
      }
  }
}
