package com.reactive.service.fraud.actor

import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.Props
import akka.routing.BalancingPool
import com.reactive.service.fraud.core.OperationInfo
import com.reactive.service.fraud.core.FraudTypes
import com.reactive.service.fraud.core.GeoLocalFraudMessage
import com.reactive.service.fraud.core.ResultFraudMessage
import com.reactive.service.fraud.core.ThresholdAmountFraudMessage
import com.reactive.service.fraud.core.ProductTypeFraudMessage
import com.reactive.service.fraud.core.AnalysisFraudMessage
import com.reactive.service.fraud.core.FraudResponse

class AnalizerFraudWorker extends Actor {
  def receive = {
    case GeoLocalFraudMessage(op) => sender ! ResultFraudMessage(op, analizeGeoLocal(op))
    case ThresholdAmountFraudMessage(op) => sender ! ResultFraudMessage(op, analizeThresholdAmount(op))
    case ProductTypeFraudMessage(op) => sender ! ResultFraudMessage(op, analizeProduct(op))
  }
  
  def analizeGeoLocal(op: OperationInfo) : FraudResponse = {
    new FraudResponse(isFraud = false)
  }
  
  def analizeThresholdAmount(op: OperationInfo) : FraudResponse = {
    var fraudResponse : FraudResponse = null
    if(op.amount > 1000000) {
      fraudResponse = new FraudResponse(isFraud = true, fraudType = FraudTypes.Amount, "Fraud for amount too big...")
    } else {
      fraudResponse = new FraudResponse(isFraud = false)
    }
    fraudResponse
  }
  
  def analizeProduct(op: OperationInfo) : FraudResponse = {
    var fraudResponse : FraudResponse = null
    val fraudProducts = Set("cash equities","commodities")
    if(fraudProducts.contains(op.product)) {
      fraudResponse = new FraudResponse(isFraud = true, fraudType = FraudTypes.Product, "Fraud for operated product...")
    } else {
      fraudResponse = new FraudResponse(isFraud = false)
    }
    fraudResponse
  }
}

class MasterFraudAnalyzerActor(notifierListener: ActorRef) extends Actor {
	val workerRouter = context.actorOf(BalancingPool(5).props(Props[AnalizerFraudWorker]), name = "analizer-fraud-workers")

	def receive = {
			case AnalysisFraudMessage(op) =>
			workerRouter ! GeoLocalFraudMessage(op)
			workerRouter ! ThresholdAmountFraudMessage(op)
			workerRouter ! ProductTypeFraudMessage(op)
			
			case ResultFraudMessage(op, response) =>
			if(response.isFraud) {
				notifierListener ! ResultFraudMessage(op, response)
			}
	}

}