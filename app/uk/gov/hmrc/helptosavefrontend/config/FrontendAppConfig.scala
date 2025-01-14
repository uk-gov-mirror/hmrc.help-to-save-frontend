/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.helptosavefrontend.config

import java.net.URI

import javax.inject.{Inject, Singleton}
import uk.gov.hmrc.helptosavefrontend.models.iv.JourneyId
import uk.gov.hmrc.helptosavefrontend.util.urlEncode
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

import scala.concurrent.duration.Duration

@Singleton
class FrontendAppConfig @Inject() (servicesConfig: ServicesConfig) {

  val appName: String = servicesConfig.getString("appName")

  val version: String = servicesConfig.getString("microservice.services.nsi.version")

  val systemId: String = servicesConfig.getString("microservice.services.nsi.systemId")

  private def getUrlFor(service: String) = servicesConfig.getString(s"microservice.services.$service.url")

  val authUrl: String = servicesConfig.baseUrl("auth")

  val appleAppUrl: String  = servicesConfig.getString("get-the-app.apple-app-store")

  val androidAppUrl: String = servicesConfig.getString("get-the-app.google-play-store")

  val helpToSaveUrl: String = servicesConfig.baseUrl("help-to-save")

  val helpToSaveFrontendUrl: String = getUrlFor("help-to-save-frontend")

  val accessAccountUrl: String = s"${getUrlFor("help-to-save-frontend")}/access-account"

  val helpToSaveReminderUrl: String = servicesConfig.baseUrl("help-to-save-reminder")

  val reminderServiceFeatureSwitch: Boolean = servicesConfig.getBoolean("reminder-feature-switch")

  val ivJourneyResultUrl: String =
    s"${servicesConfig.baseUrl("identity-verification-journey-result")}/mdtp/journey/journeyId"

  val ivUpliftUrl: String = s"${getUrlFor("identity-verification-uplift")}/uplift"

  val ivFailedMatchingUrl: String = servicesConfig.getString("gov-uk.url.contact-us")

  def ivUrl(redirectOnLoginURL: String): String = {
    def encodedCallbackUrl(redirectOnLoginURL: String): String =
      urlEncode(s"$helpToSaveFrontendUrl/iv/journey-result?continueURL=$redirectOnLoginURL")

    new URI(
      s"$ivUpliftUrl" +
        s"?origin=$appName" +
        s"&completionURL=${encodedCallbackUrl(redirectOnLoginURL)}" +
        s"&failureURL=${encodedCallbackUrl(redirectOnLoginURL)}" +
        "&confidenceLevel=200"
    ).toString
  }

  val maintenanceSchedule: String = servicesConfig.getString("scheduled-maintenance-times")

  val basGatewayFrontendUrl: String = s"${getUrlFor("bas-gateway-frontend")}"

  val ggLoginUrl: String = s"$basGatewayFrontendUrl/sign-in"
  val ggContinueUrlPrefix: String =
    servicesConfig.getString("microservice.services.bas-gateway-frontend.continue-url-prefix")

  val feedbackSurveyUrl: String = s"${getUrlFor("feedback-survey")}"

  val signOutUrl: String = s"$basGatewayFrontendUrl/sign-out-without-state?continue=$feedbackSurveyUrl"

  val ggUserUrl: String =
    s"${getUrlFor("government-gateway-registration")}/government-gateway-registration-frontend?" +
      "accountType=individual&" +
      s"continue=${urlEncode(ggContinueUrlPrefix)}%2Fhelp-to-save%2Fcheck-eligibility&" +
      "origin=help-to-save-frontend&" +
      "registerForSa=skip"

  def ivJourneyResultUrl(journeyId: JourneyId): String = new URI(s"$ivJourneyResultUrl/${journeyId.Id}").toString

  val verifyEmailURL: String =
    s"${servicesConfig.baseUrl("email-verification")}/email-verification/verification-requests"

  val linkTTLMinutes: Int = servicesConfig.getInt("microservice.services.email-verification.linkTTLMinutes")

  val newApplicantContinueURL: String = s"$helpToSaveFrontendUrl/email-confirmed-callback"

  val accountHolderContinueURL: String = s"$helpToSaveFrontendUrl/account-home/email-confirmed-callback"

  val nsiManageAccountUrl: String = getUrlFor("nsi.manage-account")
  val nsiPayInUrl: String = getUrlFor("nsi.pay-in")

  val contactFormServiceIdentifier: String = "HTS"

  val contactBaseUrl: String = getUrlFor("contact-frontend")
  val reportAProblemPartialUrl: String =
    s"$contactBaseUrl/contact/problem_reports_ajax?service=$contactFormServiceIdentifier"
  val reportAProblemNonJSUrl: String =
    s"$contactBaseUrl/contact/problem_reports_nonjs?service=$contactFormServiceIdentifier"
  val betaFeedbackUrlNoAuth: String =
    s"$contactBaseUrl/contact/beta-feedback-unauthenticated?service=$contactFormServiceIdentifier"

  val govUkURL: String = servicesConfig.getString("gov-uk.url.base")
  val govUkEligibilityInfoUrl: String = s"$govUkURL/eligibility"
  val govUkCallChargesUrl: String = servicesConfig.getString("gov-uk.url.call-charges")
  val govUkDealingWithHRMCAdditionalNeedsUrl: String =
    servicesConfig.getString("gov-uk.url.dealing-with-hmrc-additional-needs")
  val hmrcAppGuideURL: String = servicesConfig.getString("gov-uk.url.hmrc-app-guide")

  val youtubeSavingsExplained: String = servicesConfig.getString("youtube-embeds.savings-explained")
  val youtubeWhatBonuses: String = servicesConfig.getString("youtube-embeds.what-bonuses")
  val youtubeHowWithdrawalsAffectBonuses: String =
    servicesConfig.getString("youtube-embeds.how-withdrawals-affect-bonuses")

  val enableLanguageSwitching: Boolean = servicesConfig.getBoolean("enableLanguageSwitching")

  val earlyCapCheckOn: Boolean = servicesConfig.getBoolean("enable-early-cap-check")

  val accessibilityStatementUrl = servicesConfig.getString("accessibility-statement.url")
  val accessibilityStatementToggle = servicesConfig.getConfBool("accessibility-statement.toggle", true)
  def accessibilityStatementUpdated = servicesConfig.getString("accessibility-statement.updated")
  def accessibilityStatementTested = servicesConfig.getString("accessibility-statement.tested")

  object BankDetailsConfig {
    val sortCodeLength: Int = servicesConfig.getInt("bank-details-validation.sort-code.length")
    val accountNumberLength: Int = servicesConfig.getInt("bank-details-validation.account-number.length")
    val rollNumberMinLength: Int = servicesConfig.getInt("bank-details-validation.roll-number.min-length")
    val rollNumberMaxLength: Int = servicesConfig.getInt("bank-details-validation.roll-number.max-length")
    val accountNameMinLength: Int = servicesConfig.getInt("bank-details-validation.account-name.min-length")
    val accountNameMaxLength: Int = servicesConfig.getInt("bank-details-validation.account-name.max-length")
  }

  val mongoSessionExpireAfter: Duration = servicesConfig.getDuration("mongodb.session.expireAfter")

}
