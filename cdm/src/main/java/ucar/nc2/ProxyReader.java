/*
 * Copyright 1997-2007 Unidata Program Center/University Corporation for
 * Atmospheric Research, P.O. Box 3000, Boulder, CO 80307,
 * support@unidata.ucar.edu.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package ucar.nc2;

import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.ma2.Section;
import ucar.nc2.util.CancelTask;

import java.io.IOException;

/**
 * An object that knows how to read the data for a Variable.
 *
 * @author caron
 */
public interface ProxyReader {
  
  /**
   * Read all the data for a Variable.

   * @param mainv the Variable
   * @param cancelTask allow user to cancel, may be null.
   * @return memory resident Array containing the data. Will have same shape as the Variable.
   * @throws IOException on error
   */
  public Array read(Variable mainv, CancelTask cancelTask) throws IOException;

  /**
   * Read a section of the data for a Variable.
   *
   * @param mainv the Variable
   * @param section the section of data to read.
   * @param cancelTask allow user to cancel, may be null.
   *
   * @return memory resident Array containing the data. Will have same shape as the Section.
   * @throws IOException on error
   * @throws ucar.ma2.InvalidRangeException if section has incorrect rank or shape.
   */
  public Array read(Variable mainv, Section section, CancelTask cancelTask) throws IOException, InvalidRangeException;
}
