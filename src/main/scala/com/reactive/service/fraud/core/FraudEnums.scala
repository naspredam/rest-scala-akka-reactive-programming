package com.reactive.service.fraud.core

/**
 * Type of frauds on the scope of the application
 */
object FraudTypes extends Enumeration {
  val Amount, Geo, Product, None = Value
}
