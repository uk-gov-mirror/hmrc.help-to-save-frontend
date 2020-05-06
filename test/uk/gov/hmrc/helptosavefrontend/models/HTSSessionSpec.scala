/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.helptosavefrontend.models

import play.api.libs.json.{JsSuccess, Json}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HTSSessionSpec extends AnyWordSpec with Matchers {

  "HTSSession" must {

    "have a reads instance" which {

      "can correctly determine default values" in {
        val json = Json.parse("""{ }""")
        HTSSession.htsSessionReads.reads(json) shouldBe JsSuccess(
          HTSSession(None, None, None, None, None, None, None, None, false, None, false, false, None)
        )
      }

    }
  }

}
