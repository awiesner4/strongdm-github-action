package com.wiesner.concord.plugins.strongdm.registration;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.MapperFeature;
import com.strongdm.api.v1.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.util.Map;

public class ClassMapper {
    private final ObjectMapper yamlMapper;

    public ClassMapper()
    {
        yamlMapper = new ObjectMapper(new YAMLFactory());
        yamlMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    public HTTPBasicAuth websiteReadYaml(Map<String, Object> map) {
        return yamlMapper.convertValue(map, HTTPBasicAuth.class);
    };

    public AmazonEKS eksReadYaml(Map<String, Object> map) {
        return yamlMapper.convertValue(map, AmazonEKS.class);
    }
}
