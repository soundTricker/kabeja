/*
   Copyright 2005 Simon Mieth

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
package org.kabeja.dxf;

import org.kabeja.dxf.helpers.SplinePoint;

import org.kabeja.svg.SVGPathBoundaryElement;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:simon.mieth@gmx.de>Simon Mieth</a>
 *
 */
public class DXFSpline extends DXFEntity implements SVGPathBoundaryElement {
    protected int degree;
    protected int nodePointsSize;
    protected int controlPointSize;
    protected int fitPointSize;
    protected double[] knots;
    protected double[] weights;
    protected List points = new ArrayList();
    protected double fitTolerance;
    protected double knotsTolerance;
    protected double controlPointTolerance;

    /*
     * (non-Javadoc)
     *
     * @see de.miethxml.kabeja.dxf.DXFEntity#getBounds()
     */
    public Bounds getBounds() {
        // not exactly but should work
        Iterator i = points.iterator();

        while (i.hasNext()) {
            SplinePoint p = (SplinePoint) i.next();
            bounds.addToBounds(p);
        }

        return bounds;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.miethxml.kabeja.dxf.DXFEntity#getType()
     */
    public String getType() {
        // TODO Auto-generated method stub
        return DXFConstants.ENTITY_TYPE_SPLINE;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.miethxml.kabeja.svg.SVGGenerator#toSAX(org.xml.sax.ContentHandler,
     *      java.util.Map)
     */
    public void toSAX(ContentHandler handler, Map svgContext)
        throws SAXException {
        if (points.size() > 0) {
            AttributesImpl attr = new AttributesImpl();
            super.setCommonAttributes(attr);

            StringBuffer d = new StringBuffer();
            Iterator i = points.iterator();
            SplinePoint first = (SplinePoint) i.next();
            d.append("M ");
            d.append(first.getX());
            d.append(' ');
            d.append(first.getY());
            d.append(' ');

            if (degree == 2) {
                //quadric
            } else if (degree == 3) {
                //cubic
            }
        }
    }

    public void addSplinePoint(SplinePoint p) {
        points.add(p);
    }

    public Iterator getSplinePointIterator() {
        return points.iterator();
    }

    public boolean isRational() {
        return (this.flags & 4) == 4;
    }

    public boolean isClosed() {
        return (this.flags & 1) == 1;
    }

    public boolean isPeriodic() {
        return (this.flags & 2) == 2;
    }

    public boolean isPlanar() {
        return (this.flags & 8) == 8;
    }

    public boolean isLinear() {
        return (this.flags & 16) == 16;
    }

    /**
     * @return Returns the controlPointSize.
     */
    public int getControlPointSize() {
        return controlPointSize;
    }

    /**
     * @param controlPointSize
     *            The controlPointSize to set.
     */
    public void setControlPointSize(int controlPointSize) {
        this.controlPointSize = controlPointSize;
    }

    /**
     * @return Returns the degree.
     */
    public int getDegree() {
        return degree;
    }

    /**
     * @param degree
     *            The degree to set.
     */
    public void setDegree(int degree) {
        this.degree = degree;
    }

    /**
     * @return Returns the fitPointSize.
     */
    public int getFitPointSize() {
        return fitPointSize;
    }

    /**
     * @param fitPointSize
     *            The fitPointSize to set.
     */
    public void setFitPointSize(int fitPointSize) {
        this.fitPointSize = fitPointSize;
    }

    /**
     * @return Returns the fitTolerance.
     */
    public double getFitTolerance() {
        return fitTolerance;
    }

    /**
     * @param fitTolerance
     *            The fitTolerance to set.
     */
    public void setFitTolerance(double fitTolerance) {
        this.fitTolerance = fitTolerance;
    }

    /**
     * @return Returns the knots.
     */
    public double[] getKnots() {
        return knots;
    }

    /**
     * @param knots
     *            The knots to set.
     */
    public void setKnots(double[] knots) {
        this.knots = knots;
    }

    /**
     * @return Returns the nodePointsSize.
     */
    public int getNodePointsSize() {
        return nodePointsSize;
    }

    /**
     * @param nodePointsSize
     *            The nodePointsSize to set.
     */
    public void setNodePointsSize(int nodePointsSize) {
        this.nodePointsSize = nodePointsSize;
    }

    /**
     * @return Returns the weights.
     */
    public double[] getWeights() {
        return weights;
    }

    /**
     * @param weights
     *            The weights to set.
     */
    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    /**
     * @return Returns the controlPointTolerance.
     */
    public double getControlPointTolerance() {
        return controlPointTolerance;
    }

    /**
     * @param controlPointTolerance
     *            The controlPointTolerance to set.
     */
    public void setControlPointTolerance(double controlPointTolerance) {
        this.controlPointTolerance = controlPointTolerance;
    }

    /**
     * @return Returns the knotsTolerance.
     */
    public double getKnotsTolerance() {
        return knotsTolerance;
    }

    /**
     * @param knotsTolerance
     *            The knotsTolerance to set.
     */
    public void setKnotsTolerance(double knotsTolerance) {
        this.knotsTolerance = knotsTolerance;
    }

    /* (non-Javadoc)
     * @see org.kabeja.svg.SVGPathBoundaryElement#getSVGPath()
     */
    public String getSVGPath() {
        return "";
    }
}