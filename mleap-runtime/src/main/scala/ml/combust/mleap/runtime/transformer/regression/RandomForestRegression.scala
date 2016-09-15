package ml.combust.mleap.runtime.transformer.regression

import ml.combust.mleap.core.regression.RandomForestRegressionModel
import ml.combust.mleap.runtime.transformer.Transformer
import ml.combust.mleap.runtime.transformer.builder.TransformBuilder
import ml.combust.mleap.runtime.types.{DoubleType, TensorType}

import scala.util.Try

/**
  * Created by hwilkins on 11/8/15.
  */
case class RandomForestRegression(uid: String = Transformer.uniqueName("random_forest_regression"),
                                  featuresCol: String,
                                  predictionCol: String,
                                  model: RandomForestRegressionModel) extends Transformer {
  override def transform[TB <: TransformBuilder[TB]](builder: TB): Try[TB] = {
    builder.withInput(featuresCol, TensorType.doubleVector()).flatMap {
      case (b, featuresIndex) =>
        b.withOutput(predictionCol, DoubleType)(row => model(row.getVector(featuresIndex)))
    }
  }
}
