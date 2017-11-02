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

package uk.gov.hmrc.helptosavefrontend.models.eligibility

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.helptosavefrontend.models.eligibility.EligibilityCheckResult.Ineligible
import uk.gov.hmrc.helptosavefrontend.models.eligibility.IneligibilityType._

class IneligibilityTypeSpec extends WordSpec with Matchers with GeneratorDrivenPropertyChecks {

  "IneligibilityType" must {

    "have a method which converts from Ineligible to IneligibilityType" in {
        def ineligible(reasonCode: Int) = Ineligible(EligibilityCheckResponse("", 0, "", reasonCode))

      IneligibilityType.fromIneligible(ineligible(2)) shouldBe NotEntitledToWTC
      IneligibilityType.fromIneligible(ineligible(4)) shouldBe NotEntitledToWTC
      IneligibilityType.fromIneligible(ineligible(3)) shouldBe EntitledToWTCButNilTC
      IneligibilityType.fromIneligible(ineligible(5)) shouldBe EntitledToWTCButNilTC

      forAll{ reasonCode: Int ⇒
        whenever(!Set(2, 3, 4, 5).contains(reasonCode)){
          IneligibilityType.fromIneligible(ineligible(reasonCode)) shouldBe Unknown
        }

      }
    }

  }

}