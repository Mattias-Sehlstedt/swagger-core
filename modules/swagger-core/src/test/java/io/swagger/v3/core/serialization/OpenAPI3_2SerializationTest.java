package io.swagger.v3.core.serialization;

import com.fasterxml.jackson.core.JsonFactory;
import io.swagger.v3.core.matchers.SerializationMatchers;
import io.swagger.v3.core.util.*;
import io.swagger.v3.oas.models.OpenAPI;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class OpenAPI3_2SerializationTest {

    @Test
    public void testSerializeTag() throws Exception {

        final String jsonString = ResourceUtils.loadClassResource(getClass(), "specFiles/3.2.0/tag-3.2.json");
        final OpenAPI swagger = Yaml32.mapper().readValue(jsonString, OpenAPI.class);
        assertNotNull(swagger);
        SerializationMatchers.assertEqualsToYaml32(swagger, "openapi: 3.2.0\n" +
                                                            "info:\n" +
                                                            "  title: Flight API\n" +
                                                            "  version: 1.0.0\n" +
                                                            "tags:\n" +
                                                            "- name: flights\n" +
                                                            "  summary: Flights\n" +
                                                            "  description: Core flight operations\n" +
                                                            "  kind: nav\n" +
                                                            "- name: international\n" +
                                                            "  summary: International\n" +
                                                            "  description: Flights that cross country borders\n" +
                                                            "  parent: flights\n" +
                                                            "  kind: nav\n" +
                                                            "- name: domestic\n" +
                                                            "  summary: Domestic\n" +
                                                            "  description: Flights within a single country\n" +
                                                            "  parent: flights\n" +
                                                            "  kind: nav\n" +
                                                            "- name: delays\n" +
                                                            "  summary: Delays\n" +
                                                            "  description: Information about flight delays\n" +
                                                            "  externalDocs:\n" +
                                                            "    description: Delay compensation policies\n" +
                                                            "    url: https://docs.example.com/delay-policies\n" +
                                                            "  kind: badge\n" +
                                                            "paths:\n" +
                                                            "  /flights:\n" +
                                                            "    get:\n" +
                                                            "      tags:\n" +
                                                            "      - flights\n" +
                                                            "      summary: List all flights\n" +
                                                            "  /flights/international:\n" +
                                                            "    get:\n" +
                                                            "      tags:\n" +
                                                            "      - international\n" +
                                                            "      summary: List international flights\n" +
                                                            "  /flights/domestic:\n" +
                                                            "    get:\n" +
                                                            "      tags:\n" +
                                                            "      - domestic\n" +
                                                            "      summary: List domestic flights\n" +
                                                            "  /flights/delayed:\n" +
                                                            "    get:\n" +
                                                            "      tags:\n" +
                                                            "      - delays\n" +
                                                            "      summary: Get delayed flights");
        SerializationMatchers.assertEqualsToJson32(swagger, "{\n" +
                                                            "  \"openapi\" : \"3.2.0\",\n" +
                                                            "  \"info\" : {\n" +
                                                            "    \"title\" : \"Flight API\",\n" +
                                                            "    \"version\" : \"1.0.0\"\n" +
                                                            "  },\n" +
                                                            "  \"tags\" : [ {\n" +
                                                            "    \"name\" : \"flights\",\n" +
                                                            "    \"summary\" : \"Flights\",\n" +
                                                            "    \"description\" : \"Core flight operations\",\n" +
                                                            "    \"kind\" : \"nav\"\n" +
                                                            "  }, {\n" +
                                                            "    \"name\" : \"international\",\n" +
                                                            "    \"summary\" : \"International\",\n" +
                                                            "    \"description\" : \"Flights that cross country borders\",\n" +
                                                            "    \"parent\" : \"flights\",\n" +
                                                            "    \"kind\" : \"nav\"\n" +
                                                            "  }, {\n" +
                                                            "    \"name\" : \"domestic\",\n" +
                                                            "    \"summary\" : \"Domestic\",\n" +
                                                            "    \"description\" : \"Flights within a single country\",\n" +
                                                            "    \"parent\" : \"flights\",\n" +
                                                            "    \"kind\" : \"nav\"\n" +
                                                            "  }, {\n" +
                                                            "    \"name\" : \"delays\",\n" +
                                                            "    \"summary\" : \"Delays\",\n" +
                                                            "    \"description\" : \"Information about flight delays\",\n" +
                                                            "    \"externalDocs\" : {\n" +
                                                            "      \"description\" : \"Delay compensation policies\",\n" +
                                                            "      \"url\" : \"https://docs.example.com/delay-policies\"\n" +
                                                            "    },\n" +
                                                            "    \"kind\" : \"badge\"\n" +
                                                            "  } ],\n" +
                                                            "  \"paths\" : {\n" +
                                                            "    \"/flights\" : {\n" +
                                                            "      \"get\" : {\n" +
                                                            "        \"tags\" : [ \"flights\" ],\n" +
                                                            "        \"summary\" : \"List all flights\"\n" +
                                                            "      }\n" +
                                                            "    },\n" +
                                                            "    \"/flights/international\" : {\n" +
                                                            "      \"get\" : {\n" +
                                                            "        \"tags\" : [ \"international\" ],\n" +
                                                            "        \"summary\" : \"List international flights\"\n" +
                                                            "      }\n" +
                                                            "    },\n" +
                                                            "    \"/flights/domestic\" : {\n" +
                                                            "      \"get\" : {\n" +
                                                            "        \"tags\" : [ \"domestic\" ],\n" +
                                                            "        \"summary\" : \"List domestic flights\"\n" +
                                                            "      }\n" +
                                                            "    },\n" +
                                                            "    \"/flights/delayed\" : {\n" +
                                                            "      \"get\" : {\n" +
                                                            "        \"tags\" : [ \"delays\" ],\n" +
                                                            "        \"summary\" : \"Get delayed flights\"\n" +
                                                            "      }\n" +
                                                            "    }\n" +
                                                            "  }\n" +
                                                            "}");

    }

    @Test
    public void testJSONSerializeTagWithCustomFactory() throws Exception {

        //given
        final String jsonString = ResourceUtils.loadClassResource(getClass(), "specFiles/3.2.0/tag-3.2.json");
        JsonFactory jsonFactory = new JsonFactory();

        //when
        final OpenAPI swagger = ObjectMapperFactory.createJson32(jsonFactory).readValue(jsonString, OpenAPI.class);

        // then
        assertNotNull(swagger);
        SerializationMatchers.assertEqualsToJson32(swagger, jsonString);
    }
}
