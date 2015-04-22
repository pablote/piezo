package com.lucidchart.piezo

import java.io.InputStreamReader

import _root_.play.api.Configuration
import _root_.play.api.test.FakeApplication
import com.typesafe.config.ConfigFactory

package object play {
  val configStream = getClass().getResourceAsStream("/application.conf")
  val configReader = new InputStreamReader(configStream)
  val additionalConfig = ConfigFactory.parseReader(configReader)
  val additionalConfigurations = new Configuration(additionalConfig)

  class FakeApp(fakeConfig: Configuration) extends FakeApplication {
    def this() {
      this(additionalConfigurations)
    }

    override def configuration = {
      super.configuration ++ fakeConfig
    }
  }
}
