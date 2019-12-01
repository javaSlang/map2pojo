package com.slang.map2pojo.core.filling.impl.baking;

import com.slang.map2pojo.TestPojoClass;
import org.junit.Test;

public class NoBakingTest {

    @Test(expected = UnsupportedOperationException.class)
    public void applyTest() {
        new NoBaking<>()
                .apply(
                        TestPojoClass.TEST_CLASS_FIELDS.get(TestPojoClass.TEST_FIELD),
                        new Object()
                );
    }

}
