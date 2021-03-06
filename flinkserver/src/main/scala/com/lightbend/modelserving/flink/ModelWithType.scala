/*
 * Copyright (C) 2017-2019  Lightbend
 *
 * This file is part of the Lightbend model-serving-tutorial (https://github.com/lightbend/model-serving-tutorial)
 *
 * The model-serving-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License Version 2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lightbend.modelserving.flink

import com.lightbend.modelserving.model.Model

/** Used for Flink checkpointing */
class ModelWithType[RECORD, RESULT](dType: String, model: (String,Model[RECORD, RESULT])) {

  val dataType = dType
  val modelWithName = model

  override def equals(obj: Any): Boolean = {
    obj match {
      case modelWithType: ModelWithType[RECORD, RESULT] =>
          modelWithType.dataType == dataType &&
          modelWithType.modelWithName._1 == modelWithName._1 &&
          modelWithType.modelWithName._2 == modelWithName._2
      case _ => false
    }
  }
}
