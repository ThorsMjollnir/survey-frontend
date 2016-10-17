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

This file is based on Intake24 v1.0.

© Crown copyright, 2012, 2013, 2014

Licensed under the Open Government Licence 3.0: 

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package net.scran24.dbtool.portion

import javax.swing.JPanel
import uk.ac.ncl.openlab.intake24.PortionSizeMethod
import org.workcraft.gui.SimpleFlowLayout
import javax.swing.JLabel
import javax.swing.JButton
import org.workcraft.gui.SimpleFlowLayout.LineBreak
import java.awt.Dimension
import java.awt.Color
import javax.swing.ImageIcon
import javax.swing.JCheckBox
import net.scran24.dbtool.SwingUtil._
import net.scran24.dbtool.Util._

import net.scran24.dbtool.SelectionDialog
import uk.ac.ncl.openlab.intake24.GuideImage
import java.awt.SystemColor
import uk.ac.ncl.openlab.intake24.PortionSizeMethodParameter

class GuidePortionSizeEditor(params: Seq[PortionSizeMethodParameter], guideImageDefs: Seq[GuideImage], changesMade: () => Unit) extends PortionSizeEditor {
  case class GuideWrapper(guide: GuideImage) {
    override def toString = guide.description + " (" + guide.id + ")"
  }

  val undefinedIcon = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("net/scran24/admintool/warn.png")))
  undefinedIcon.setToolTipText("This parameter must be defined!")

  val initParam = params.map(p => (p.name, p.value)).toMap

  val guideLabel = new JLabel("Guide image: ")

  guideLabel.setPreferredSize(new Dimension(150, 20))

  val guideValue = new JLabel(initParam.get("guide-image-id").getOrElse("(undefined)"))
  guideValue.setPreferredSize(new Dimension(100, 20))

  val chooseGuideButton = new JButton("Change...")

  chooseGuideButton.addActionListener(() => {
    val dialog = new SelectionDialog[GuideWrapper](ownerFrame(this), "Select a guide image", guideImageDefs.map(GuideWrapper(_)))
    dialog.setVisible(true)
    dialog.choice match {
      case Some(GuideWrapper(guide)) =>  {
        guideValue.setText(guide.id)
        changesMade()
      }
      case _ => {}
    }
  })

  add(guideLabel)
  add(guideValue)
  add(chooseGuideButton)

  def parameters = Seq(PortionSizeMethodParameter("guide-image-id", guideValue.getText()))

  val methodName = "guide-image"
}