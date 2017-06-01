package com.reactive.service.model

import scala.slick.driver.PostgresDriver.simple._

class FraudMessage (tag: Tag) extends Table[(Int, String, String)](tag, "FRAUD_MESSAGES") {
  
  def id = column[Int]("id")
  def fraudType = column[String]("fraud_type")
  def fraudResponseMsg = column[String]("fraud_response_msg")
  def * = (id, fraudType, fraudResponseMsg)
  
}