package org.emamotor.morecat;

import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.repository.EntryRepository;
import org.emamotor.morecat.service.EntryService;
import org.emamotor.morecat.util.Resources;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.gradle.archive.importer.embedded.EmbeddedGradleImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author Yoshimasa Tanabe
 */
public class MoreCatDeployment {

  public static WebArchive deployment() {
    return ShrinkWrap
      .create(WebArchive.class, "test.war")
      .addPackages(true, "org.emamotor.morecat")
      .addPackage(Resources.class.getPackage())
      .addPackage(EntryService.class.getPackage())
      .addPackage(EntryRepository.class.getPackage())
      .addPackage(Entry.class.getPackage())
//      .addAsLibraries(
//        Maven.resolver()
//          .loadPomFromFile("pom.xml")
//          .importRuntimeDependencies()
//          .resolve()
//          .withTransitivity()
//          .asFile())
      .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
      .addAsResource("import.sql")
      .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
      .addAsWebInfResource("test-ds.xml");
//    return ShrinkWrap
//      .create(EmbeddedGradleImporter.class, "test.war")
//      .forThisProjectDirectory()
//      .importBuildOutput().as(WebArchive.class)
//      .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
//      .addAsResource("import.sql")
//      .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
//      .addAsWebInfResource("test-ds.xml");
  }

}
