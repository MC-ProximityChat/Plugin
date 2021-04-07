package com.proximitychat.plugin.requests;

import com.proximitychat.plugin.requests.routes.QueryParam;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryParamTests {

    @Test
    public void assert_QueryParamToString_IsCorrect() {
        QueryParam queryParam = QueryParam.of("foo", "bar");
        assertEquals(queryParam.toString(), "foo=bar");
    }
}
