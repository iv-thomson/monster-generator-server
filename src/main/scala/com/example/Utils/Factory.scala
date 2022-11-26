package com.example.Utils

import com.example.Models.Identifiable

abstract class Factory[T <: Identifiable, P]() {
  def from(creature: P, id: Option[String] = None): T
}
