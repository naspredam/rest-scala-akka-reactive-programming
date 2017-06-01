package com.reactive.service.fraud.core

sealed trait FraudMessage
case class AnalysisFraudMessage(op: OperationInfo) extends FraudMessage
case class GeoLocalFraudMessage(op: OperationInfo) extends FraudMessage
case class ThresholdAmountFraudMessage(op: OperationInfo) extends FraudMessage
case class ProductTypeFraudMessage(op: OperationInfo) extends FraudMessage
case class ResultFraudMessage(op: OperationInfo, response: FraudResponse) extends FraudMessage

case class FraudResponse(isFraud: Boolean, fraudType: FraudTypes.Value = FraudTypes.None, fraudResponseMsg: String = "")
