/* 
 * Copyright (C) 2009 Konstantin Tokarev <annulen@users.sourceforge.net>
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All I ask is that proper credit is given for my work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.openscience.jchempaint.controller;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Point2d;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.tools.ILoggingTool;
import org.openscience.cdk.tools.LoggingToolFactory;
import org.openscience.jchempaint.controller.RotateModule;
import org.openscience.jchempaint.controller.undoredo.IUndoRedoFactory;
import org.openscience.jchempaint.controller.undoredo.IUndoRedoable;
import org.openscience.jchempaint.controller.undoredo.UndoRedoHandler;
import org.openscience.jchempaint.renderer.BoundsCalculator;
import org.openscience.jchempaint.renderer.selection.IChemObjectSelection;
import org.openscience.cdk.interfaces.IAtomContainer;


public class Rotate3DModule extends RotateModule {

    private double rotationAnglePhi;
    private double rotationAnglePsi;
    private boolean horizontalFlip;
    private boolean verticalFlip;

    /**
     * Constructor 
     * @param chemModelRelay
     */
    public Rotate3DModule(IChemModelRelay chemModelRelay) {
        super(chemModelRelay);
        logger.debug("constructor");
        horizontalFlip = false;
        verticalFlip = false;
    }
    
    /**
     * On mouse drag, quasi-3D rotation around the center is done
     * (It isn't real 3D rotation because of truncation of transformation
     * matrix to 2x2)
     */
    @Override
    public void mouseDrag(Point2d worldCoordFrom, Point2d worldCoordTo) {

        if (selectionMade) {
            rotationPerformed=true;

            final int SLOW_DOWN_FACTOR=1;
            rotationAnglePhi += (worldCoordTo.x - worldCoordFrom.x)/SLOW_DOWN_FACTOR;
            rotationAnglePsi += (worldCoordTo.y - worldCoordFrom.y)/SLOW_DOWN_FACTOR;

            /* For more info on the mathematics, see Wiki at 
             * http://en.wikipedia.org/wiki/Rotation_matrix
             */
            double cosinePhi = Math.cos(rotationAnglePhi);
            double sinePhi = Math.sin(rotationAnglePhi);
            double cosinePsi = Math.cos(rotationAnglePsi);
            double sinePsi = Math.sin(rotationAnglePsi);
            
            IAtomContainer atc = selection.getConnectedAtomContainer();
            for (int i = 0; i < startCoordsRelativeToRotationCenter.length; i++) {
                double newX = startCoordsRelativeToRotationCenter[i].x*cosinePhi
                        + startCoordsRelativeToRotationCenter[i].y*sinePhi*sinePsi; 
                double newY = startCoordsRelativeToRotationCenter[i].y*cosinePsi;

                Point2d newCoords = new Point2d(newX + rotationCenter.x, newY
                        + rotationCenter.y);

                atc.getAtom(i).setPoint2d(newCoords);
            }

            if ((cosinePhi < 0) && (!horizontalFlip)) {
                horizontalFlip = true;
                chemModelRelay.invertStereoInSelection();
            }
            if ((cosinePhi >= 0) && (horizontalFlip)) {
                horizontalFlip = false;
                chemModelRelay.invertStereoInSelection();
            }
            if ((cosinePsi < 0) && (!verticalFlip)) {
                verticalFlip = true;
                chemModelRelay.invertStereoInSelection();
            }
            if ((cosinePsi >= 0) && (verticalFlip)) {
                verticalFlip = false;
                chemModelRelay.invertStereoInSelection();
            }
        }
        chemModelRelay.updateView();
    }

    
    public String getDrawModeString() {
        return "Rotate in space";
    }
}
