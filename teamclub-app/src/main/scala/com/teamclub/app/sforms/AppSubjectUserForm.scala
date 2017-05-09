package com.teamclub.app.sforms

import javax.validation.constraints.{NotNull, Size}

import com.teamclub.util.page.PageForm

import scala.beans.BeanProperty

/**
  * Created by ilkkzm on 17-4-27.
  */
class AppSubjectUserForm extends PageForm {
  @BeanProperty
  @NotNull
  @Size(min=5)
  var userName: String = _

  @BeanProperty
  var userPassword: String = _
}
