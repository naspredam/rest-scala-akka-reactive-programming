package com.reactive.service.model

import scala.slick.driver.PostgresDriver.simple._


// OperationInfo(id: String, ip: String, amount: Double, product: String)
class OperationalMessage (tag: Tag) extends Table[(Int, String, String, Double, String)](tag, "OPT_MESSAGES") {
  
  def id = column[Int]("id")
  def requestId = column[String]("request_id")
  def request_host_ip = column[String]("ip")
  def amount = column[Double]("amount")
  def product = column[String]("product")
  def * = (id, requestId, request_host_ip, amount, product)
  
}