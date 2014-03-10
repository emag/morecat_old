package org.emamotor.morecat.test;

import am.ik.marked4j.Marked;
import am.ik.marked4j.MarkedBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Yoshimasa Tanabe
 */
public class Marked4JTest {

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

        code = "* foo\n" +
               "* fuga\n" +
               " * fuga_fuga\n" + // no support nested list
               "* hoge\n";
        displayTransition(code);

        code = "| Left align | Right align | Center align |\n" +
               "|:-----------|------------:|:------------:|\n" +
               "| This       |        This |     This     |\n" +
               "| column     |      column |    column    |\n" +
               "| will       |        will |     will     |\n" +
               "| be         |          be |      be      |\n" +
               "| left       |       right |    center    |\n" +
               "| aligned    |     aligned |   aligned    |\n";
        displayTransition(code);
    }

    private void displayTransition(String markdown) throws IOException {
        Marked marked = new MarkedBuilder().gfm(true).sanitize(true).build();

        System.out.println("[Markdown]");
        System.out.println(markdown);
        System.out.println("[HTML]");
        System.out.println(marked.marked(markdown));
    }
}
