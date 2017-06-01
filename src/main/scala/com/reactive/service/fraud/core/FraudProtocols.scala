package com.reactive.service.fraud.core

import spray.json.DefaultJsonProtocol
import spray.json.DefaultJsonProtocol._

case class OperationInfo(id: String, ip: String, amount: Double, product: String)

case class IpInfo(ip: String, country_name: Option[String], city: Option[String], latitude: Option[Double], longitude: Option[Double])

object FraudProtocols extends DefaultJsonProtocol {
  implicit val optInfoFormat = jsonFormat4(OperationInfo.apply)
  implicit val ipInfoFormat = jsonFormat5(IpInfo.apply)
}