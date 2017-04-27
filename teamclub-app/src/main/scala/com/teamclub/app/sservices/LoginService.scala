package com.teamclub.app.sservices

import com.avaje.ebean.{EbeanServer, Query}
import com.fasterxml.jackson.databind.JsonNode
import com.teamclub.app.sforms.AppSubjectUserForm
import com.teamclub.domain.AppSubjectUser
import com.teamclub.util.errors.ErrorCode
import com.teamclub.util.libs.{F, Json}
import com.teamclub.util.page.Pager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
  * Created by ilkkzm on 17-4-27.
  */
@Service
class LoginService {
  @Autowired
  private val server:EbeanServer = null

  def getList(form: AppSubjectUserForm): F.Either[JsonNode, ErrorCode] = {
    val query = server.find(classOf[AppSubjectUser])
    expression(query, form)
    F.Either.Left(Pager.genePagerJson(query, form))
  }

  def expression(query: Query[AppSubjectUser], form: AppSubjectUserForm): Query[AppSubjectUser] = {
    query
  }
}