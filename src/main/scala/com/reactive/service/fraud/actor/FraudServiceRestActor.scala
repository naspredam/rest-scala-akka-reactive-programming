package com.reactive.service.fraud.actor

import scala.concurrent.ExecutionContextExecutor

import com.reactive.service.fraud.core.OperationInfo
import com.reactive.service.fraud.core.FraudProtocols._

import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.stream.Materializer
import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller
import spray.routing.Directive.pimpApply
import spray.routing.HttpServiceActor
import spray.routing.Route
import akka.actor.Actor
import akka.stream.ActorMaterializer
import spray.routing.HttpService
import com.reactive.service.fraud.core.AnalysisFraudMessage

class FraudServiceRestActor(masterActor: ActorRef) extends HttpServiceActor {

  override def actorRefFactory = context
  
  val fraudRouter = new FraudServiceRoute {
    override implicit def actorRefFactory = context
    override implicit val master: ActorRef = masterActor
  }
  
  def receive = runRoute(fraudRouter.routes)

}

trait FraudServiceRoute extends HttpService {
  
  implicit val master: ActorRef
  implicit def executionContext: ExecutionContextExecutor = actorRefFactory.dispatcher

	def routes: Route =
    pathPrefix("fraud-service") {
      post {
        entity(as[OperationInfo]) { operationInfo =>
          master ! AnalysisFraudMessage(operationInfo)
          complete("Operation released...")
      }
    }
  }
}