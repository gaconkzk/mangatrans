package org.theflies.discovery.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to The Flies.
 *
 * <p> Properties are configured in the application.yml file. </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}
