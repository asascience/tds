/* Copyright */
package thredds.server.catalog.builder;

import thredds.client.catalog.DatasetNode;
import thredds.client.catalog.builder.DatasetBuilder;
import thredds.server.catalog.CatalogScan;

/**
 * CatalogScan Builder
 *
 * @author caron
 * @since 6/17/2015
 */
public class CatalogScanBuilder extends DatasetBuilder {
  String path, location, watch;
  String context = "thredds"; // default servlet context

  public CatalogScanBuilder(DatasetBuilder parent, String name, String path, String location, String watch,
      String context) {
    super(parent);
    this.name = name;
    this.path = path;
    this.location = location;
    this.watch = watch;
    this.context = context;
  }

  public CatalogScanBuilder(DatasetBuilder parent, CatalogScan from) {
    super(parent, from);
    this.path = from.getPath();
    this.location = from.getLocation();
    this.watch = from.getWatch();
  }

  public CatalogScan makeDataset(DatasetNode parent) {
    String xlink = "/" + context + "/catalog/" + path + "/catalogScan.xml";
    return new CatalogScan(parent, name, xlink, flds, path, location, watch);
  }
}
