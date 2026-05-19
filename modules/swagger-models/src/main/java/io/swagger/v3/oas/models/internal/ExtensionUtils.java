package io.swagger.v3.oas.models.internal;

/**
 * Internal utility class for handling vendor extensions.
 *
 * <p><strong>Warning:</strong> This class is part of the internal implementation and is not part
 * of the public API. It may be changed or removed without notice. Users should not depend on this
 * class directly.</p>
 */
public class ExtensionUtils {

    private static final String VENDOR_EXTENSION_PREFIX = "x-";
    private static final String VENDOR_31_OAS_EXTENSION_PREFIX = "x-oas-";
    private static final String VENDOR_31_OAI_EXTENSION_PREFIX = "x-oai-";

    private ExtensionUtils() {
        // Utility class - prevent instantiation
    }

    /**
     * Checks if a given extension name is a valid vendor extension.
     * Valid vendor extensions must start with {@code "x-"}.
     *
     * @param extensionName the extension name to check
     * @return true if the extension name is a valid vendor extension, false otherwise
     */
    public static boolean isVendorExtension(String extensionName) {
        return extensionName != null && extensionName.startsWith(VENDOR_EXTENSION_PREFIX);
    }

    /**
     * Checks if a given extension name is a reserved OpenAPI 3.1 vendor extension.
     * Reserved extensions start with {@code "x-oas-"} or {@code "x-oai-"}.
     *
     * @param extensionName the extension name to check
     * @return true if the extension name is a reserved OpenAPI 3.1 extension, false otherwise
     */
    public static boolean is31VendorExtension(String extensionName) {
        return extensionName != null &&
               (extensionName.startsWith(VENDOR_31_OAS_EXTENSION_PREFIX) ||
                extensionName.startsWith(VENDOR_31_OAI_EXTENSION_PREFIX));
    }

}

