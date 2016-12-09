package uk.ac.ncl.openlab.intake24.foodsql.user

import scala.Left
import scala.Right

import anorm.Macro
import anorm.NamedParameter.symbol
import anorm.SQL
import anorm.sqlToSimple
import uk.ac.ncl.openlab.intake24.foodsql.FoodDataSqlService
import uk.ac.ncl.openlab.intake24.services.fooddb.errors.LookupError
import uk.ac.ncl.openlab.intake24.services.fooddb.errors.RecordNotFound
import uk.ac.ncl.openlab.intake24.services.fooddb.user.ImageMapService
import uk.ac.ncl.openlab.intake24.services.fooddb.user.UserGuideImage
import uk.ac.ncl.openlab.intake24.sql.SqlResourceLoader
import uk.ac.ncl.openlab.intake24.services.fooddb.user.UserImageMap
import uk.ac.ncl.openlab.intake24.services.fooddb.user.UserImageMapObject

trait ImageMapUserImpl extends ImageMapService with FoodDataSqlService with SqlResourceLoader {
  private case class ResultRow(id: String, base_image_path: String, object_id: Long, description: String, outline: Array[Double], overlay_image_path: String)

  private lazy val imageMapObjectsQuery = sqlFromResource("user/get_image_map_objects.sql")
   
  def getImageMaps(ids: Seq[String]): Either[LookupError, Map[String, UserImageMap]] = tryWithConnection {
    implicit conn =>
      val result = SQL(imageMapObjectsQuery).on('ids -> ids).as(Macro.namedParser[ResultRow].*).groupBy(_.id).map {
        case (id, rows) =>
          val objects = rows.map {
            row => UserImageMapObject(row.object_id.toInt, row.description, row.overlay_image_path, row.outline)
          }
          id -> UserImageMap(rows.head.base_image_path, objects)
      }
      
      if (ids.exists(!result.contains(_)))
        Left(RecordNotFound(new RuntimeException()))
      else
        Right(result)
  }
}
