package io.swagger.v3.core.converting.override;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.jackson.TypeNameResolver;
import io.swagger.v3.core.matchers.SerializationMatchers;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.models.media.Schema;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

public class CustomResolverTest {

    @Test(description = "it should ignore properties with type Bar")
    public void testCustomConverter() {
        // add the custom converter
        final ModelConverters converters = new ModelConverters();
        converters.addConverter(new CustomConverter(Json.mapper()));

        Map<String, Schema> models = converters.readAll(Foo.class);
        Schema model = models.get("io.swagger.v3.core.converting.override.CustomResolverTest$Foo");
        assertNotNull(model);
        assertEquals(model.getProperties().size(), 2);

        final Schema barProperty = (Schema) model.getProperties().get("bar");
        assertEquals(barProperty.get$ref(), "#/components/schemas/io.swagger.v3.core.converting.override.CustomResolverTest$Bar");

        final Schema titleProperty = (Schema)model.getProperties().get("title");
        assertNotNull(titleProperty);

        model = models.get("io.swagger.v3.core.converting.override.CustomResolverTest$Bar");
        assertNotNull(model);

    }

    @Test(description = "it should set the required mode based upon the field type")
    public void testCustomRequiredModeBasedUponTypeConverter() {
        // add the custom converter
        final ModelConverters converters = new ModelConverters();
        converters.addConverter(new RequiredModeBasedUponFieldTypeResolver(Json.mapper()));

        Map<String, Schema> models = converters.readAll(SuperFoo.class);
        Schema model = models.get("SuperFoo");
        assertNotNull(model);
        assertEquals(model.getProperties().size(), 4);
        assertEquals(model.getRequired(), Collections.singletonList("bar"));

        final Schema fooProperty = (Schema) model.getProperties().get("foo");
        assertEquals(fooProperty.get$ref(), "#/components/schemas/Foo");
        Schema fooModel = models.get("Foo");
        assertEquals(fooModel.getRequired(), Collections.singletonList("bar"));

        final Schema optionalBarProperty = (Schema) model.getProperties().get("optionalBar");
        assertEquals(optionalBarProperty.get$ref(), "#/components/schemas/Bar");
        Schema barModel = models.get("Bar");
        assertNull(barModel.getRequired());

        final Schema barProperty = (Schema) model.getProperties().get("bar");
        assertEquals(barProperty.get$ref(), "#/components/schemas/Bar");
        assertNull(barModel.getRequired());

        final Schema titleProperty = (Schema) model.getProperties().get("string");
        assertNotNull(titleProperty);

    }

    @Test(description = "it should convert JsonSubTypes to OneOf")
    public void testJsonSubTypesToOneOfConverter() {
        // add the custom converter
        final ModelConverters converters = new ModelConverters();
        converters.addConverter(new JsonSubTypesToOneOfResolver(Json.mapper()));

        final Map<String, Schema> model = converters.readAll(BaseBean.class);
        final String expected = "BaseBean:\n" +
                                "  type: object\n" +
                                "  properties:\n" +
                                "    type:\n" +
                                "      type: string\n" +
                                "    a:\n" +
                                "      type: integer\n" +
                                "      format: int32\n" +
                                "    b:\n" +
                                "      type: string\n" +
                                "  description: BaseBean\n" +
                                "  discriminator:\n" +
                                "    propertyName: type\n" +
                                "    mapping:\n" +
                                "      SubBean1: \"#/components/schemas/SubBean1\"\n" +
                                "      SubBean2: \"#/components/schemas/SubBean2\"\n" +
                                "  oneOf:\n" +
                                "  - $ref: \"#/components/schemas/SubBean1\"\n" +
                                "  - $ref: \"#/components/schemas/SubBean2\"\n" +
                                "SubBean1:\n" +
                                "  type: object\n" +
                                "  allOf:\n" +
                                "  - $ref: \"#/components/schemas/BaseBean\"\n" +
                                "  - type: object\n" +
                                "    properties:\n" +
                                "      c:\n" +
                                "        type: integer\n" +
                                "        format: int32\n" +
                                "SubBean2:\n" +
                                "  type: object\n" +
                                "  allOf:\n" +
                                "  - $ref: \"#/components/schemas/BaseBean\"\n" +
                                "  - type: object\n" +
                                "    properties:\n" +
                                "      d:\n" +
                                "        type: integer\n" +
                                "        format: int32";
        SerializationMatchers.assertEqualsToYaml(model, expected);
    }

    class CustomConverter extends ModelResolver {

        public CustomConverter(ObjectMapper mapper) {
            super(mapper, new QualifiedTypeNameResolver());
        }
    }

    class QualifiedTypeNameResolver extends TypeNameResolver {

        @Override
        protected String nameForClass(Class<?> cls, Set<Options> options) {
            String className = cls.getName().startsWith("java.") ? cls.getSimpleName() : cls.getName();
            if (options.contains(Options.SKIP_API_MODEL)) {
                return className;
            }
            final io.swagger.v3.oas.annotations.media.Schema model = cls.getAnnotation(io.swagger.v3.oas.annotations.media.Schema.class);
            final String modelName = model == null ? null : StringUtils.trimToNull(model.name());
            return modelName == null ? className : modelName;
        }
    }

    class RequiredModeBasedUponFieldTypeResolver extends ModelResolver {

        public RequiredModeBasedUponFieldTypeResolver(ObjectMapper mapper) {
            super(mapper);
        }

        @Override
        protected io.swagger.v3.oas.annotations.media.Schema.RequiredMode resolveRequiredMode(
                io.swagger.v3.oas.annotations.media.Schema schema, JavaType type) {
            if (type.getRawClass().equals(Bar.class)) {
                return io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;
            }
            return super.resolveRequiredMode(schema, type);
        }
    }

    class JsonSubTypesToOneOfResolver extends ModelResolver {

        public JsonSubTypesToOneOfResolver(ObjectMapper mapper) {
            super(mapper);
        }

        @Override
        protected boolean resolveSubtypes(Schema model, BeanDescription bean, ModelConverterContext context, JsonView jsonViewAnnotation) {
            return super.resolveSubtypes(model, bean, context, jsonViewAnnotation);
        }
    }

    class SuperFoo {
        public Foo foo;
        public Optional<Bar> optionalBar;
        public Bar bar;
        public Optional<String> string;
    }

    class Foo {
        public Bar bar = null;
        public String title = null;
    }

    class Bar {
        public String foo = null;
    }

    @JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = SubBean1.class, name = "SubBean1"),
            @JsonSubTypes.Type(value = SubBean2.class, name = "SubBean2")
    })
    @io.swagger.v3.oas.annotations.media.Schema(
            description = "BaseBean",
            discriminatorProperty = "type",
            discriminatorMapping = {
                    @DiscriminatorMapping(value = "SubBean1", schema = SubBean1.class),
                    @DiscriminatorMapping(value = "SubBean2", schema = SubBean2.class)
            },
            oneOf = {SubBean1.class, SubBean2.class}
    )
    static class BaseBean {
        public String type;
        public int a;
        public String b;
    }

    static class SubBean1 extends BaseBean {
        public int c;
    }

    static class SubBean2 extends BaseBean {
        public int d;
    }

    @JsonTypeInfo(include = JsonTypeInfo.As.PROPERTY, use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = SubBeanInterface1.class, name = "SubBeanInterface1"),
            @JsonSubTypes.Type(value = SubBeanInterface2.class, name = "SubBeanInterface2")
    })
    static class BaseBeanData implements BaseBeanInterface {
        public String type;
        public int a;
        public String b;

        @Override
        public String getType() {
            return type;
        }
    }

    static class SubBeanInterface1 extends BaseBeanData {
        public int c;
    }

    static class SubBeanInterface2 extends BaseBeanData {
        public int d;
    }

    /* We would like the interface to be generated as a oneOf so that the spec can express BaseBeanInterface as oneOf child1, child2,
    and then that both children references the BaseBeanData with an allOf and that the data carries shared properties */
    interface BaseBeanInterface {
        String getType();
    }
}
