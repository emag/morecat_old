package org.emamotor.morecat.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author tanabe
 */
public class PasswordUtilTest {

    @Test
    public void hasingTest() throws Exception {
        // Verify
        assertThat(PasswordUtil.hasing("morecat", "SHA-512"), is("e028814727bf3bb50c902a453ba358a1864ff46dca56160b3c2f3f49ca2de8027bc867a6efcf6e01e7dc15c31cde8b637c254b0bacf6df8ea20f7081f356991e"));
    }

}
