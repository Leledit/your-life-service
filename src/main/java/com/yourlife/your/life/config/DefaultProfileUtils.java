package com.yourlife.your.life.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DefaultProfileUtils {
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(DefaultProfileUtils.class);

    private static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";

    private static final Properties BUILD_PROPERTIES = readProperties();

    public static String getDefaultActiveProfiles() {
        if (BUILD_PROPERTIES != null) {
            String activeProfile = BUILD_PROPERTIES.getProperty(SPRING_PROFILE_ACTIVE);
            if (activeProfile != null && !activeProfile.isEmpty() &&
                    (activeProfile.contains(Constants.SPRING_PROFILE_DEVELOPMENT) ||
                            activeProfile.contains(Constants.SPRING_PROFILE_PRODUCTION) ||
                            activeProfile.contains(Constants.SPRING_PROFILE_LOCAL))) {
                return activeProfile;
            }
        }
        log.warn("No Spring profile configured, running with default profile: {}", Constants.SPRING_PROFILE_LOCAL);
        return Constants.SPRING_PROFILE_LOCAL;
    }

    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties =  new HashMap<>();
        /*
         * The default profile to use when no other profiles are defined
         * This cannot be set in the <code>application.yml</code> file.
         * See https://github.com/spring-projects/spring-boot/issues/1219
         */
        defProperties.put(SPRING_PROFILE_ACTIVE, getDefaultActiveProfiles());
        app.setDefaultProperties(defProperties);
    }

    private static Properties readProperties() {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(new ClassPathResource("config/application.yml"));
            return factory.getObject();
        } catch (Exception e) {
            log.error("Failed to read application.yml to get default profile");
        }
        return null;
    }
}
