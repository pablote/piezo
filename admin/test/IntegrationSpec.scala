package test

import com.lucidchart.piezo.play.FakeApp
import com.typesafe.config.ConfigFactory
import org.specs2.execute.AnyValueAsResult
import org.specs2.mutable._
import play.api.Configuration

import play.api.test._
import play.api.test.Helpers._

import scala.collection.JavaConversions
import scala.collection.JavaConversions._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
class IntegrationSpec extends Specification {
  def configure() = new Configuration(ConfigFactory.parseMap(mapAsJavaMap(Map(
    "statsd.prefix" -> "foo"
  ))))

  "Application" should {
    "work from within a browser" in {
      running(new FakeApp()) {
        new AnyValueAsResult().asResult({
          1 must be equalTo(1)
        })
      }
    }
    
  }
  
}