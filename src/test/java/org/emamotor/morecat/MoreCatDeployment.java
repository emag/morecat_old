package org.emamotor.morecat;

import org.emamotor.morecat.repository.EntryRepository;
import org.emamotor.morecat.model.Entry;
import org.emamotor.morecat.service.EntryService;
import org.emamotor.morecat.util.Resources;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author Yoshimasa Tanabe
 */
public class MoreCatDeployment {

    public static WebArchive deployment() {
        return ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addPackage(Resources.class.getPackage())
                .addPackage(EntryService.class.getPackage())
                .addPackage(EntryRepository.class.getPackage())
                .addPackage(Entry.class.getPackage())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("import.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml");
    }
}
