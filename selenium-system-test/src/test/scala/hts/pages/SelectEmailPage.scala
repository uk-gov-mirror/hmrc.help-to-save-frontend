/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hts.pages

import hts.utils.Configuration
import org.openqa.selenium.WebDriver

object SelectEmailPage extends Page {

  val url: String = s"${Configuration.host}/help-to-save/select-email"

  def setNewEmail(email: String)(implicit driver: WebDriver): Unit = {
    val el = find(name("new-email"))
    el.foreach(_.underlying.clear())
    el.foreach(_.underlying.sendKeys(email))
  }

  //def verifyYourEmail()(implicit driver: WebDriver): Unit = click on xpath(".//*[@type='submit']")

  def setAndVerifyNewEmail(email: String)(implicit driver: WebDriver): Unit = {
    selectNewEmail()
    setNewEmail(email)
    clickContinue()
  }

  def selectGGEmail()(implicit driver: WebDriver): Unit = click on "registered-email"

  def selectNewEmail()(implicit driver: WebDriver): Unit = click on "add-new-email"

  def clickContinue()(implicit driver: WebDriver): Unit = click on xpath(".//*[@type='submit']")

}