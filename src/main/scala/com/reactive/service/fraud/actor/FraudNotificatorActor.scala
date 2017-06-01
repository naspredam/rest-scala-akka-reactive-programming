package com.reactive.service.fraud.actor

import akka.actor.Actor
import com.reactive.service.fraud.core.ResultFraudMessage

class FraudNotificatorActor extends Actor {
  
	def receive = {
	case ResultFraudMessage(op, response) =>
    	println("\n\tDetectado operativa de fraude!!! id=%s -- %s".format(op.id, op toString))
	}
}