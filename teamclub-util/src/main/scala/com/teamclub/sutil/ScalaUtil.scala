package com.teamclub.sutil

import org.slf4j.{Logger, LoggerFactory}

/**
  * Created by ilkkzm on 17-4-18.
  */
object ScalaUtil {
  val logger = LoggerFactory.getLogger(ScalaUtil.getClass)
  def show(): String = {
    logger.info("ScalaUtil...")
    "XX ScalaUtil..."
  }
}
