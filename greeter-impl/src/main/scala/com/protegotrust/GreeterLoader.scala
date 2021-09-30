// Copyright 2021 Protego Trust Company
package com.protegotrust

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server._
import com.protegotrust.greeter.api.GreeterService
import com.softwaremill.macwire._
import play.api.libs.ws.ahc.AhcWSComponents

class GreeterLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new GreeterApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new GreeterApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[GreeterService])
}

abstract class GreeterApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = {
    serverFor[GreeterService](wire[GreeterServiceImpl])
      .additionalRouter(wire[GrpcGreeterServiceImpl])
  }
}
