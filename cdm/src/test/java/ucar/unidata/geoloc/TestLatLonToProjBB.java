/*
 * Copyright 1998-2009 University Corporation for Atmospheric Research/Unidata
 *
 * Portions of this software were developed by the Unidata Program at the
 * University Corporation for Atmospheric Research.
 *
 * Access and use of this software shall impose the following obligations
 * and understandings on the user. The user is granted the right, without
 * any fee or cost, to use, copy, modify, alter, enhance and distribute
 * this software, and any derivative works thereof, and its supporting
 * documentation for any purpose whatsoever, provided that this entire
 * notice appears in all copies of the software, derivative works and
 * supporting documentation.  Further, UCAR requests that the user credit
 * UCAR/Unidata in any publications that result from the use of this
 * software or in any product that includes this software. The names UCAR
 * and/or Unidata, however, may not be used in any advertising or publicity
 * to endorse or promote any products or commercial entity unless specific
 * written permission is obtained from UCAR/Unidata. The user also
 * understands that UCAR/Unidata is not obligated to provide the user with
 * any support, consulting, training or assistance of any kind with regard
 * to the use, operation and performance of this software nor to provide
 * the user with any updates, revisions, new versions or "bug fixes."
 *
 * THIS SOFTWARE IS PROVIDED BY UCAR/UNIDATA "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UCAR/UNIDATA BE LIABLE FOR ANY SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package ucar.unidata.geoloc;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucar.unidata.geoloc.projection.LambertConformal;

import java.lang.invoke.MethodHandles;

/**
 * compare ProjectionImpl.latLonToProjBB against latLonToProjBB2
 * There are a lot of failures - not sure why, but probably latLonToProjBB2 is wrong.
 *
 * @author caron
 */
public class TestLatLonToProjBB extends TestCase {
  private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  void doTest(ProjectionImpl p, LatLonRect rect) {
    ProjectionRect prect = p.latLonToProjBB( rect);
    ProjectionRect prect2 = p.latLonToProjBB2( rect);
    if (!prect.nearlyEquals(prect2)) {
      System.out.println("\nFAIL Projection= " + p);
      System.out.println("  llbb= " + rect.toString2());
      System.out.println("  latLonToProjBB= " + prect);
      System.out.println("  latLonToProjBB2= " + prect2);
      assert false;
    }
  }

  void doTests(ProjectionImpl p, double mid) {
    LatLonPointImpl ptL = new LatLonPointImpl(-10, 0.0);
    double xinc = 22.5;
    double yinc = 20.0;
    for (double lon = mid - 90; lon < mid + 90; lon += xinc) {
      ptL.setLongitude(lon);
      LatLonRect llbb = new LatLonRect(ptL, yinc, xinc);
      
      ProjectionPoint pt1 = p.latLonToProj(ptL);
      ProjectionPoint pt2 = p.latLonToProj(llbb.getLowerRightPoint());
      if (!p.crossSeam(pt1, pt2))
        doTest(p, llbb);
    }
  }

  public void testLC() {
    doTests(new LambertConformal(40.0, 0, 20.0, 60.0), 0);
  }

  public void utestProblem() {
    ProjectionImpl p = new LambertConformal(40.0, 0, 20.0, 60.0);
    LatLonPointImpl ptL = new LatLonPointImpl(-10, 135);
    LatLonPointImpl ptL2 = new LatLonPointImpl(10, 157.5);
    LatLonRect llbb = new LatLonRect(ptL, ptL2);

    ProjectionPoint pt1 = p.latLonToProj(ptL);
    ProjectionPoint pt2 = p.latLonToProj(ptL2);
    System.out.println("pt1 = "+pt1);
    System.out.println("pt2 = "+pt2);

    ProjectionPoint lr = p.latLonToProj(llbb.getLowerRightPoint());
    System.out.println("lr = "+lr);
    if (!p.crossSeam(pt1, pt2))
      doTest(p, llbb);

  } 
}
