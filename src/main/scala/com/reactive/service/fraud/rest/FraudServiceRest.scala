package com.reactive.service.fraud.rest

import com.reactive.service.fraud.actor.FraudNotificatorActor

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import akka.event.Logging
import akka.actor.Props
import akka.io.IO
import spray.can.Http
import com.reactive.service.fraud.actor.FraudServiceRestActor
import com.reactive.service.fraud.actor.MasterFraudAnalyzerActor

/**
 * App for the REST Service to listen the incoming operations that
 * can be fraud operations. With that it will invoke the fraud analyzers that
 * in case there is any fraud an event will report to the respective team
 */
object FraudServiceRest extends App {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val config = ConfigFactory.load()
  val logger = Logging(system, getClass)
  
  // create the result listener, which will print the result and 
	// shutdown the system
	val listener = system.actorOf(Props[FraudNotificatorActor], name = "fraud-notification-listener")

	// create the master
	val master = system.actorOf(Props(classOf[MasterFraudAnalyzerActor], listener), name = "master")
	
	val api = system.actorOf(Props(classOf[FraudServiceRestActor], master), "api-actor")
	
	val host = config.getString("http.interface")
  val port = config.getInt("http.port")
	IO(Http) ! Http.Bind(listener = api, interface = host, port = port)

}