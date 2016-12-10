package com.yutong.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class EncodeUtilsTest {

    @Test
    public void testUrlEncode() {
        assertEquals("my+%E8%8B%B1%2B%E9%9B%84",
                EncodeUtils.urlEncode("my 英+雄", "UTF-8"));
    }

}
