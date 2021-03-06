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

package com.lightbend.modelserving.spark

import com.lightbend.model.winerecord.WineRecord
import com.lightbend.modelserving.model.{Model, ModelToServe}
import com.lightbend.modelserving.winemodel.DataRecord

// Exercise:
// Using nulls in Scala code is discouraged, although sometimes convenient, as here.
// Try refactoring the code use `data: Option[WinRecord]` and `model: Option[ModelToServe]`.

/**
  * Unified representation of model and data.
  * Ideally we could use DataToServe here as a generic representation of the data, but according
  * to https://stackoverflow.com/questions/42121649/schema-for-type-any-is-not-supported/42130708
  * Any and AnyVal are not supported by Spark Streaming.
  */
case class DataWithModel(dataType : String, data : WineRecord, model : ModelToServe)


// Additional data transformation methods
object DataWithModel{

  val emptyModel = DataWithModel("", null, null)

  def modelFromByteArrayStructured(message: Array[Byte]): DataWithModel = {

    ModelToServe.fromByteArray(message).toOption match {
      case Some(model) => DataWithModel(model.dataType, null, model)
      case _ => emptyModel
    }
  }

  def dataFromByteArrayStructured(message: Array[Byte]): DataWithModel =  {

    DataRecord.fromByteArray(message).toOption match {
      case Some(data) => DataWithModel(data.getType, data.getRecord, null)
      case _ => emptyModel
    }
  }

  def dataWineFromByteArrayStructured(message: Array[Byte]): WineRecord =  {

    DataRecord.fromByteArray(message).toOption match {
      case Some(data) => data.getRecord
      case _ => new WineRecord
    }
  }
}

// Model state used for storing existing models
case class ModelState(name : String, model : Model[WineRecord, Double])
