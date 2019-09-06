package org.playground.late.events

import org.scalatest.{ Matchers, WordSpec }

class ApplicationTest extends WordSpec with Matchers {
  "A simple test" should {
    "be valid" in {
      val a = 5
      a shouldBe 5
    }
  }
}
