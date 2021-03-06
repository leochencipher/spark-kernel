/*
 * Copyright 2014 IBM Corp.
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

package com.ibm.spark.magic.builtin

import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSpec, Matchers}
import com.ibm.spark.magic.MagicOutput
import com.ibm.spark.kernel.protocol.v5.MIMEType

class JavaScriptSpec extends FunSpec with Matchers with MockitoSugar {
  describe("JavaScript"){
    describe("#executeCell") {
      it("should return the entire cell's contents with the MIME type of text/javascript") {
        val javaScriptMagic = new JavaScript

        val code = "some code on a line" :: "more code on another line" :: Nil
        val expected = MagicOutput(MIMEType.ApplicationJavaScript -> code.mkString("\n"))
        javaScriptMagic.executeCell(code) should be (expected)
      }
    }

    describe("#executeLine") {
      it("should return the line's contents with the MIME type of text/javascript") {
        val javaScriptMagic = new JavaScript

        val code = "some code on a line"
        val expected = MagicOutput(MIMEType.ApplicationJavaScript -> code)
        javaScriptMagic.executeLine(code) should be (expected)
      }
    }
  }
}
