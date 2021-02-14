package com.wiesner.concord.plugins.strongdm.registration;

import com.walmartlabs.concord.runtime.v2.sdk.MapBackedVariables;
import com.walmartlabs.concord.runtime.v2.sdk.Task;
import com.walmartlabs.concord.runtime.v2.sdk.TaskResult;
import com.walmartlabs.concord.runtime.v2.sdk.Variables;

import java.util.HashMap;
import java.util.Map;

public class GitHubAction {
    private Variables inputs;
    public static void main(String[] args) throws Exception {

        Map<String, String> env = System.getenv();
        HashMap sdmMap = new HashMap();
        HashMap sdmValuesMap = new HashMap();

        for (String envName : env.keySet()) {
            if (envName.contains("sdm_values_")) {
                sdmValuesMap.put(envName.replace("sdm_values_", ""), env.get(envName));
            }

            if (envName.contains("sdm_base_")) {
                sdmMap.put(envName.replace("sdm_base_", ""), env.get(envName));
            }
        }

        sdmMap.put("values", sdmValuesMap);

        MapBackedVariables input = new MapBackedVariables(sdmMap);

//        TestOutput testOutput = new TestOutput();
//        testOutput.execute(input);
        StrongDMTask sdmTask = new StrongDMTask();
        sdmTask.execute(input);
    }
}
