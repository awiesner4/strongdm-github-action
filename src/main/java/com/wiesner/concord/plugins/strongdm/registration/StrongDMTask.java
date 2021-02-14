package com.wiesner.concord.plugins.strongdm.registration;

import com.strongdm.api.v1.*;
import com.walmartlabs.concord.runtime.v2.sdk.Task;
import com.walmartlabs.concord.runtime.v2.sdk.TaskResult;
import com.walmartlabs.concord.runtime.v2.sdk.Variables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@Named("sdmRegister")
public class StrongDMTask
        implements Task
{
    private final Logger log = LoggerFactory.getLogger(StrongDMTask.class);
    private ClientOptions opts = new ClientOptions();
    private HTTPBasicAuth website;
    private Client client;
    private static final String WEBSITE_FILTER = "type:httpBasic";
    private static final String EKS_FILTER = "type:amazoneks";

    @Override
    public TaskResult execute(Variables input)
            throws Exception
    {

        String apiAccessKey = input.assertString("apiAccessKey");
        String apiSecretKey = input.assertString("apiSecretKey");

        if (apiAccessKey == null || apiSecretKey == null) {
            return TaskResult.error("SDM_API_ACCESS_KEY and SDM_API_SECRET_KEY must be provided").value("created", false);
        } else {
            System.out.println("Get Client");
            client = new Client(apiAccessKey, apiSecretKey, opts);
        }

        if (input.assertString("type").equals("httpBasic")) {
            website = new ClassMapper().websiteReadYaml(input.assertMap("values"));
            if (resourceExists(website, WEBSITE_FILTER)) {
                System.out.println("RESOURCE EXISTS");
                return TaskResult.success().value("exists", true);
            } else {
                System.out.println("HTTP Basic Type");
                return create(website);
            }
        }

        return TaskResult.success();
    }

    private TaskResult create(Resource sdmResource) throws Exception
    {
        try {
            System.out.println("CREATION STARTED");
            ResourceCreateResponse response = client.resources()
                    .withDeadlineAfter(30, TimeUnit.SECONDS)
                    .create(sdmResource);

        } catch (Exception e) {
            e.printStackTrace();
            return TaskResult.error("Failed to create resource").value("created", false);
        }

        return TaskResult.success().value("created", true);
    }

    private Boolean resourceExists(Resource request, String FILTER) throws Exception
    {
        try {
            Iterable<Resource> sdmResources = client.resources().list(FILTER);

            for (Resource sdmResource : sdmResources) {
                if (request.getName().equals(sdmResource.getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Failed resourceExists check");
        }
        return false;
    }

}
