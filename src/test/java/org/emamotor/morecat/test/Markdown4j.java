package org.emamotor.morecat.test;

import org.junit.Test;
import org.markdown4j.Markdown4jProcessor;

/**
 * @author tanabe
 */
public class Markdown4j {

    @Test
    public void display() throws Exception {
        String html = new Markdown4jProcessor().process("This is a **bold** text");
        System.out.println(html);
    }

}
