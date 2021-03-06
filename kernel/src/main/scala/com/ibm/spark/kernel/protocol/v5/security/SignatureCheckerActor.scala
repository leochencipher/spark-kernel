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

package com.ibm.spark.kernel.protocol.v5.security

import akka.actor.Actor
import com.ibm.spark.security.Hmac
import com.ibm.spark.utils.LogLike

/**
 * Verifies whether or not a kernel message has a valid signature.
 * @param hmac The HMAC to use for signature validation
 */
class SignatureCheckerActor(
  private val hmac: Hmac
) extends Actor with LogLike {
  override def receive: Receive = {
    case (signature: String, blob: Seq[_]) =>
      val stringBlob: Seq[String] = blob.map(_.toString)
      val hmacString = hmac(stringBlob: _*)
      val isValidSignature = hmacString == signature
      logger.trace(s"Signature ${signature} validity checked against " +
        s"hmac ${hmacString} with outcome ${isValidSignature}")
      sender ! isValidSignature
  }
}
