package com.javaslang.map2pojo.core.filling.impl.baking.exceptions;

import org.junit.Test;

import java.math.BigDecimal;

import static com.javaslang.map2pojo.core.filling.impl.baking.BakingBigDecimalTest.NAN;
import static junit.framework.TestCase.assertEquals;

public class WrongTypeMappingExceptionTest {

    @Test
    public void errorMessageTest() {
        String errorMsg = new WrongTypeMappingException(NAN, BigDecimal.class).getMessage();
        assertEquals(
                "Unable to convert 'NaN' of 'class java.lang.String' type to 'java.math.BigDecimal'",
                errorMsg
        );
    }

}
