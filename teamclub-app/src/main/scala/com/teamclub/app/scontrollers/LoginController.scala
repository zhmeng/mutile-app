package com.teamclub.app.scontrollers

import com.fasterxml.jackson.databind.JsonNode
import com.teamclub.app.sforms.AppSubjectUserForm
import com.teamclub.app.sservices.LoginService
import com.teamclub.domain.AppSubjectUser
import com.teamclub.util.errors.ErrorCodes
import com.teamclub.util.libs.{Eithers, F}
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
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

  def login(@RequestBody form: AppSubjectUserForm): JsonNode = {
    logger.info(form.userName)
    val subjectUser = new AppSubjectUser()
    BeanUtils.copyProperties(form, subjectUser)
    logger.info(subjectUser.getUserName)
    Eithers.toJson(loginService.getList(form))
  }
}
