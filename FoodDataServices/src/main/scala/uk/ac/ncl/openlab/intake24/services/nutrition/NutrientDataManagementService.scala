package uk.ac.ncl.openlab.intake24.services.nutrition

import uk.ac.ncl.openlab.intake24.NutrientTable
import uk.ac.ncl.openlab.intake24.NutrientType
import uk.ac.ncl.openlab.intake24.NutrientTableRecord
import uk.ac.ncl.openlab.intake24.services.fooddb.errors.UnexpectedDatabaseError
import uk.ac.ncl.openlab.intake24.services.fooddb.errors.LookupError

trait NutrientDataManagementService {
  def allNutrientTables(): Either[UnexpectedDatabaseError, Seq[NutrientTable]]
  def nutrientTable(id: String): Either[LookupError, NutrientTable]
  def createNutrientTable(data: NutrientTable): Either[UnexpectedDatabaseError, Unit]
  def updateNutrientTable(id: String, data: NutrientTable): Either[LookupError, Unit]
  def deleteNutrientTable(id: String): Either[LookupError, Unit]
  
  //def nutrientTypes(): Seq[NutrientType]
  //def nutrientType(id: Int): Option[NutrientType]
  //def createNutrientTypes(record: Seq[NutrientType])
  //def updateNutrientType(id: Int, record: NutrientType)
  //def deleteNutrientType(id: Int)
    
  def createNutrientTableRecords(records: Seq[NutrientTableRecord]): Either[UnexpectedDatabaseError, Unit]
}