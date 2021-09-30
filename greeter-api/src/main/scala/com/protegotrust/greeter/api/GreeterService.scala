// Copyright 2021 Protego Trust Company
package com.protegotrust.greeter.api

import com.lightbend.lagom.scaladsl.api.Service

trait GreeterService extends Service {
  override final def descriptor = {
    import Service._
    named("greeter-srvc")
  }
}
