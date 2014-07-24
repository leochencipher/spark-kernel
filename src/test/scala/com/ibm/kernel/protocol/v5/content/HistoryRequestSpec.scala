package com.ibm.kernel.protocol.v5.content

import org.scalatest.{FunSpec, Matchers}
import play.api.data.validation.ValidationError
import play.api.libs.json._

class HistoryRequestSpec extends FunSpec with Matchers {
  val historyRequestJson: JsValue = Json.parse("""
  {
    "output": true,
    "ras": true,
    "hist_access_type": "<STRING>",
    "session": 1,
    "start": 0,
    "stop": 5,
    "n": 1,
    "pattern": "<STRING>",
    "unique": true
  }
  """)

  val historyRequest = HistoryRequest(
    true, true, "<STRING>", 1, 0, 5, 1, "<STRING>", true
  )

  describe("HistoryRequest") {
    describe("implicit conversions") {
      it("should implicitly convert from valid json to a HistoryRequest instance") {
        // This is the least safe way to convert as an error is thrown if it fails
        historyRequestJson.as[HistoryRequest] should be (historyRequest)
      }

      it("should also work with asOpt") {
        // This is safer, but we lose the error information as it returns
        // None if the conversion fails
        val newCompleteRequest = historyRequestJson.asOpt[HistoryRequest]

        newCompleteRequest.get should be (historyRequest)
      }

      it("should also work with validate") {
        // This is the safest as it collects all error information (not just first error) and reports it
        val CompleteRequestResults = historyRequestJson.validate[HistoryRequest]

        CompleteRequestResults.fold(
          (invalid: Seq[(JsPath, Seq[ValidationError])]) => println("Failed!"),
          (valid: HistoryRequest) => valid
        ) should be (historyRequest)
      }

      it("should implicitly convert from a HistoryRequest instance to valid json") {
        Json.toJson(historyRequest) should be (historyRequestJson)
      }
    }
  }
}

