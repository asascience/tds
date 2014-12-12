package ucar.nc2.util;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Test  TableParser.readTable
 *
 * @author caron
 * @since 4/19/12
 */
public class TestTableParser {

    ////////////////////////////////////////////////////////////////////////////////////
  static final String testName3 = "/resources/nj22/tables/nexrad.tbl";
  static final String testRepeat = "C:\\data\\ghcnm\\ghcnm.v3.0.0-beta1.20101207.qae.dat"; // LOOK put this into cdmUnitTest

  @Test
  public void testReadNexradTable() throws IOException {
    Class c = TableParser.class;
    InputStream is = c.getResourceAsStream(testName3);
    List<TableParser.Record> recs = TableParser.readTable(is, "3,15,54,60d,67d,73d", 50000);
    for (TableParser.Record record : recs) {
      for (int j = 0; j < record.values.size(); j++) {
        Object s = record.values.get(j);
        System.out.print(" " + s.toString());
      }
      System.out.println();
    }
  }

  @Test
  @Ignore("ghcnm.v3.0.0-beta1.20101207.qae.dat isn't in /share/testdata")
  public void testReadgGghcnmTable() throws IOException {
    List<TableParser.Record> recs = TableParser.readTable(testRepeat, "11L,15i,19,(24i,25,26,27)*10", 5);
    for (TableParser.Record record : recs) {
      for (int j = 0; j < record.values.size(); j++) {
        Object s = record.values.get(j);
        System.out.print(" " + s.toString());
      }
      System.out.println();
    }
  }


}
