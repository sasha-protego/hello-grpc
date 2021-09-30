// Copyright 2021 Protego Trust Company
package com.protegotrust

import akka.actor.ActorSystem
import com.protegotrust.grpc.interop.{AbstractGreetingServiceRouter, HelloReply, HelloRequest}

import scala.concurrent.Future

class GrpcGreeterServiceImpl(system: ActorSystem) extends AbstractGreetingServiceRouter(system) {
  override def greetStranger(in: HelloRequest): Future[HelloReply] =
    Future.successful(HelloReply(s"Hi ${in.from}!"))
}
