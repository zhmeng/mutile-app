package com.teamclub.app.scontrollers

import javax.validation.Valid

import com.fasterxml.jackson.databind.JsonNode
import com.teamclub.app.sforms.AppSubjectUserForm
import com.teamclub.app.sservices.LoginService
import com.teamclub.util.libs.Eithers
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestBody, RestController}

/**
  * Created by ilkkzm on 17-4-27.
  */
@RestController("com.teamclub.app.scontrollers.LoginController")
class LoginController {
  val logger = LoggerFactory.getLogger(classOf[LoginController])

  @Autowired
  val loginService: LoginService = null

  def login(@RequestBody @Valid form: AppSubjectUserForm): JsonNode = {
    Eithers.toJson(loginService.exits(form))
  }
}
