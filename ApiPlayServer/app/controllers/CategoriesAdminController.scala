/*
This file is part of Intake24.

Copyright 2015, 2016 Newcastle University.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package controllers

import scala.concurrent.Future

import javax.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.Controller
import security.DeadboltActionsAdapter
import security.Roles
import uk.ac.ncl.openlab.intake24.LocalCategoryRecordUpdate
import uk.ac.ncl.openlab.intake24.MainCategoryRecordUpdate
import uk.ac.ncl.openlab.intake24.NewMainCategoryRecord
import uk.ac.ncl.openlab.intake24.services.fooddb.admin.CategoriesAdminService
import upickle.default.read

class CategoriesAdminController @Inject() (service: CategoriesAdminService, deadbolt: DeadboltActionsAdapter) extends Controller
    with PickleErrorHandler
    with FoodDatabaseErrorHandler {

  def getCategoryRecord(code: String, locale: String) = deadbolt.restrict(Roles.superuser) {
    Future {
      translateResult(service.getCategoryRecord(code, locale))
    }
  }
  def isCategoryCodeAvailable(code: String) = deadbolt.restrict(Roles.superuser) {
    Future {
      translateResult(service.isCategoryCodeAvailable(code))
    }
  }

  def isCategoryCode(code: String) = deadbolt.restrict(Roles.superuser) {
    Future {
      translateResult(service.isCategoryCodeAvailable(code))
    }
  }

  def createMainCategoryRecord() = deadbolt.restrict(Roles.superuser)(parse.tolerantText) {
    request =>
      Future {
        tryWithPickle {
          translateResult(service.createMainCategoryRecords(Seq(read[NewMainCategoryRecord](request.body))))
        }
      }
  }

  def deleteCategory(categoryCode: String) = deadbolt.restrict(Roles.superuser) {
    Future {
      translateResult(service.deleteCategory(categoryCode))
    }
  }

  def updateMainCategoryRecord(categoryCode: String) = deadbolt.restrict(Roles.superuser)(parse.tolerantText) {
    request =>
      Future {
        tryWithPickle {
          translateResult(service.updateMainCategoryRecord(categoryCode, read[MainCategoryRecordUpdate](request.body)))
        }
      }
  }

  def updateLocalCategoryRecord(categoryCode: String, locale: String) = deadbolt.restrict(Roles.superuser)(parse.tolerantText) {
    request =>
      Future {
        tryWithPickle {
          translateResult(service.updateLocalCategoryRecord(categoryCode, read[LocalCategoryRecordUpdate](request.body), locale))
        }
      }
  }
}
