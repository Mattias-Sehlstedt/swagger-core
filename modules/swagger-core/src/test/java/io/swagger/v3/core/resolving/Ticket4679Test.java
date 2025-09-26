package io.swagger.v3.core.resolving;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.core.matchers.SerializationMatchers;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.core.util.Yaml31;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class Ticket4679Test extends SwaggerTestBase{

    @Test(description = "Custom schema implementation in property overrides type value")
    public void testCustomSchemaImplementation() {

        String expectedYaml = "ModelWithCustomSchemaImplementationInProperty:\n" +
                "  type: object\n" +
                "  properties:\n" +
                "    exampleField:\n" +
                "      type: integer\n" +
                "      format: int32\n" +
                "    secondExampleField:\n" +
                "      type: string\n";

        Map<String, io.swagger.v3.oas.models.media.Schema> stringSchemaMap = ModelConverters.getInstance(true).readAll(ModelWithCustomSchemaImplementationInProperty.class);
        SerializationMatchers.assertEqualsToYaml31(stringSchemaMap, expectedYaml);
    }

    @Test
    public void testOAS31SchemaWithImplementation() {
        String expectedYaml = "ListCarrier:\n" +
                              "  type: object\n" +
                              "  properties:\n" +
                              "    listItems:\n" +
                              // This is incorrectly an object instead of a list?
                              "      type: object\n" +
                              "      description: List of ListItem\n" +
                              "      properties:\n" +
                              "        itemInfo:\n" +
                              "          type: string\n" +
                              "ListItemWithDifferentSchemaDescription:\n" +
                              "  description: Different description\n" +
                              "  type: object\n" +
                              "  properties:\n" +
                              "    itemInfo:\n" +
                              "      type: string\n";

        Map<String, io.swagger.v3.oas.models.media.Schema> stringSchemaMap = ModelConverters.getInstance(true).readAll(ListCarrier.class);
        ResolvedSchema resolvedSchema3_0 = ModelConverters.getInstance(false)
                .resolveAsResolvedSchema(new AnnotatedType(ListCarrier.class));
        ResolvedSchema resolvedSchema3_1 = ModelConverters.getInstance(false)
                .resolveAsResolvedSchema(new AnnotatedType(ListCarrier.class));
        SerializationMatchers.assertEqualsToYaml31(stringSchemaMap, expectedYaml);
    }

    @Test
    public void testOAS31ArraySchemaWithImplementation() {
        String expectedYaml = "ListCarrierArrayItem:\n" +
                              "  type: object\n" +
                              "  description: ListItem Description\n" +
                              "  properties:\n" +
                              "    itemInfo:\n" +
                              "      type: string\n" +
                              "ListCarrierArraySchema:\n" +
                              "  type: object\n" +
                              "  properties:\n" +
                              "    listItems:\n" +
                              "      type: array\n" +
                              "      items:\n" +
                              // This Schema points to a non-existent ref?
                              "        $ref: \"#/components/schemas/ListArrayItemWithDifferentSchemaDescription\"\n" +
                              "        description: List of ListItem\n";

        Map<String, io.swagger.v3.oas.models.media.Schema> stringSchemaMap = ModelConverters.getInstance(true).readAll(ListCarrierArraySchema.class);
        ResolvedSchema resolvedSchema3_0 = ModelConverters.getInstance(false)
                .resolveAsResolvedSchema(new AnnotatedType(ListCarrierArraySchema.class));
        ResolvedSchema resolvedSchema3_1 = ModelConverters.getInstance(false)
                .resolveAsResolvedSchema(new AnnotatedType(ListCarrierArraySchema.class));
        SerializationMatchers.assertEqualsToYaml31(stringSchemaMap, expectedYaml);
    }

    static class ModelWithCustomSchemaImplementationInProperty {

        @Schema(implementation = Integer.class)
        private String exampleField;

        @Schema(type = "string")
        private Integer secondExampleField;

        public String getExampleField() {
            return exampleField;
        }

        public void setExampleField(String exampleField) {
            this.exampleField = exampleField;
        }

        public Integer getSecondExampleField() {
            return secondExampleField;
        }

        public void setSecondExampleField(Integer secondExampleField) {
            this.secondExampleField = secondExampleField;
        }


    }

    static class ListCarrier {

        @io.swagger.v3.oas.annotations.media.Schema(
                description = "List of ListItem",
                implementation = ListCarrierItem.ListItemWithDifferentSchemaDescription.class
        )
        private List<ListCarrierItem> listItems;

        public List<ListCarrierItem> getListItems() {
            return listItems;
        }
    }

    @io.swagger.v3.oas.annotations.media.Schema(description = "ListItem Description")
    static class ListCarrierItem {

        public String itemInfo;

        @io.swagger.v3.oas.annotations.media.Schema(description = "Different description")
        static class ListItemWithDifferentSchemaDescription extends ListCarrierItem {
            // same as Thing but uses different @Schema annotations
        }
    }

    static class ListCarrierArraySchema {

        @ArraySchema(
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                        description = "List of ListItem",
                        implementation = ListCarrierArrayItem.ListArrayItemWithDifferentSchemaDescription.class
                ))
        private List<ListCarrierArrayItem> listItems;

        public List<ListCarrierArrayItem> getListItems() {
            return listItems;
        }
    }

    @io.swagger.v3.oas.annotations.media.Schema(description = "ListItem Description")
    static class ListCarrierArrayItem {

        public String itemInfo;

        @io.swagger.v3.oas.annotations.media.Schema(description = "Different description")
        static class ListArrayItemWithDifferentSchemaDescription extends ListCarrierArrayItem {
            // same as Thing but uses different @Schema annotations
        }
    }
}
