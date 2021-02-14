package com.wiesner.concord.plugins.strongdm.registration;

import com.walmartlabs.concord.runtime.v2.sdk.Variables;

public class TestOutput {

    public void execute(Variables input) throws Exception
    {
        System.out.println(input.assertMap("values"));
    }
}
