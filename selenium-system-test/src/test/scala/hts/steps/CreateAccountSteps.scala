/*
 * Copyright 2019 HM Revenue & Customs
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

package hts.steps

import hts.browser.Browser
import hts.pages.emailPages.{SelectEmailPage, VerifyYourEmailPage}
import hts.pages.errorPages.{IncorrectDetailsPage, NoAccountPage}
import hts.pages.informationPages.HMRCChangeOfDetailsPage
import hts.pages._
import hts.pages.accountHomePages.AccessAccountLink
import hts.pages.registrationPages._
import hts.utils.EitherOps._
import hts.utils.{ScenarioContext, TestBankDetails}

import scala.hts.pages.informationPages.HelpandInformationPage

class CreateAccountSteps extends Steps {
  When("^they try to sign in without being logged in to GG$") {
    AccessAccountLink.navigate()
  }

  Given("^the authenticated user tries to sign in$|^they log in$") {
    AuthorityWizardPage.authenticateEligibleUser(AccessAccountLink.expectedURL, ScenarioContext.generateEligibleNINO())
  }

  When("^the authenticated user tries to sign in for help information$") {
    AuthorityWizardPage.authenticateEligibleUser(HelpandInformationPage.expectedURL)
  }

  Given("^they try to start creating an account$") {
    CheckEligibilityLink.navigate()
  }

  When("^they log in and proceed to create an account using their GG email$") {
    AuthorityWizardPage.authenticateEligibleUser(EligiblePage.expectedURL, ScenarioContext.generateEligibleNINO())
    createAccountUsingGGEmail()
  }

  When("^they choose to go ahead with creating an account$") {
    AuthorityWizardPage.enterUserDetails(200, "strong", ScenarioContext.userInfo().getOrElse(sys.error))
    createAccountUsingGGEmail()
  }

  When("^they see their details are incorrect and report it$") {
    EligiblePage.continue()

    SelectEmailPage.setAndVerifyNewEmail("newemail@mail.com")
    Browser.checkCurrentPageIs(VerifyYourEmailPage)

    Browser.goBack()
    SelectEmailPage.selectGGEmail()

    Browser.checkCurrentPageIs(BankDetailsPage)
    BankDetailsPage.enterDetails(TestBankDetails.ValidBankDetails)

    Browser.checkCurrentPageIs(CheckDetailsCreateAccountPage)
    CheckDetailsCreateAccountPage.detailsNotCorrect()

    Browser.checkCurrentPageIs(IncorrectDetailsPage)
    IncorrectDetailsPage.checkForOldQuotes()
    Browser.checkForLinksThatExistOnEveryPage(IncorrectDetailsPage)
    IncorrectDetailsPage.clickBack

    CheckDetailsCreateAccountPage.detailsNotCorrect()

    Browser.checkHeader(IncorrectDetailsPage)
  }

  Then("^they see the HMRC change of details page$") {
    Browser.clickLinkTextOnceClickable("Tell HMRC about a change to your personal details")
    Browser.checkCurrentPageIs(HMRCChangeOfDetailsPage, "GOV.UK")
  }

  Then("^they see the help information page$") {
    Browser.checkCurrentPageIs(HelpandInformationPage)
  }

  Then("^setup regular payment link shows correct account number$") {
    HelpandInformationPage.setupPaymentValidateHTSAccountNumber()
  }

  When("^they proceed to create an account using their GG email$") {
    EligiblePage.navigate()
    createAccountUsingGGEmail()
  }

  When("^they proceed to create an account$"){
    EligiblePage.navigate()
    createAccountError()
  }

  Then("^they are informed they don't have an account$") {
    Browser.checkCurrentPageIs(NoAccountPage)
    Browser.checkForLinksThatExistOnEveryPage(NoAccountPage)
  }

  Then("^they see that the account is created$") {
    Browser.checkHeader(AccountCreatedPage)
    AccountCreatedPage.retrieveHTSAccountNumber
  }

  And("^they are redirected to the account created page$") {
    Browser.checkCurrentPageIs(AccountCreatedPage)
  }

  When("^they click on the sign out link$"){
    AccountCreatedPage.clickSignOut
    Browser.checkPageIsLoaded()
  }

  Then("^they are redirected to the survey page$") {
    Browser.checkCurrentPageIs(SurveyPage)
  }

}
