package io.swagger.v3.oas.annotations.tags;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPI32;
import io.swagger.v3.oas.annotations.extensions.Extension;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;

/**
 * The annotation may be applied at class or method level, or in {@link io.swagger.v3.oas.annotations.Operation#tags()} to define tags for the
 * single operation (when applied at method level) or for all operations of a class (when applied at class level).
 * <p>It can also be used in {@link io.swagger.v3.oas.annotations.OpenAPIDefinition#tags()} to define spec level tags.</p>
 * <p>When applied at method or class level, if only a name is provided, the tag will be added to operation only;
 * if additional fields are also defined, like description or externalDocs, the Tag will also be added to openAPI.tags
 * field</p>
 *
 * @see <a href="https://github.com/OAI/OpenAPI-Specification/blob/3.0.4/versions/3.0.4.md#tag-object">Tag (OpenAPI 3.0 specification)</a>
 * @see <a href="https://github.com/OAI/OpenAPI-Specification/blob/3.2.0/versions/3.2.0.md#tag-object">Tag (OpenAPI 3.2 specification)</a>
 * @see io.swagger.v3.oas.annotations.OpenAPIDefinition
 **/
@Target({METHOD, TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Tags.class)
@Inherited
public @interface Tag {

    /**
     * The name of this tag.
     *
     * @return the name of this tag
     */
    String name();

    /**
     * A short summary of the tag, used for display purposes.
     *
     * @since 2.2.X / OpenAPI 3.2
     * @return the summary
     */
    @OpenAPI32
    String summary() default "";

    /**
     * A short description for this tag.
     *
     * @return the description of this tag
     */
    String description() default "";

    /**
     * Additional external documentation for this tag.
     *
     * @return the external documentation for this tag
     */
    ExternalDocumentation externalDocs() default @ExternalDocumentation();

    /**
     * The list of optional extensions
     *
     * @return an optional array of extensions
     */
    Extension[] extensions() default {};

    /**
     * The name of a tag that this tag is nested under.
     * The named tag MUST exist in the API description, and circular references between parent and child tags MUST NOT be used.
     *
     * @since 2.2.X / OpenAPI 3.2
     * @return the parent tag
     */
    @OpenAPI32
    String parent() default "";

    /**
     * A machine-readable string to categorize what sort of tag it is. Any string value can be used;
     * common uses are {@code nav} for Navigation, {@code badge} for visible badges, {@code audience} for APIs used by different groups.
     * @see <a href="https://spec.openapis.org/registry/tag-kind/index.html">Tag Kinds Registry</a>
     *
     * @since 2.2.X / OpenAPI 3.2
     * @return the kind
     */
    @OpenAPI32
    String kind() default "";
}
