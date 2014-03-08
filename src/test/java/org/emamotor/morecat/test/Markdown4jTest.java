package org.emamotor.morecat.test;

import org.junit.Test;
import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;

/**
 * @author tanabe
 * https://code.google.com/p/markdown4j/
 */
public class Markdown4jTest {

    @Test
    public void display() throws Exception {
        displayTransition("This is a **bold** text");

        displayTransition("<script>alert('alert!!!');</script>");

        String code = "~~~ java\n" +
                      "public class Test {\n" +
                      "    public static void main(String... args) {\n" +
                      "        System.out.println(\"Hello Markdown4j!\"\n" +
                      "    }\n" +
                      "}\n" +
                      "~~~\n";
        displayTransition(code);
    }

    private void displayTransition(String markdown) throws IOException {
        System.out.println("[Markdown]");
        System.out.println(markdown);
        System.out.println("[HTML]");
        System.out.println(new Markdown4jProcessor().process(markdown));
    }

}
