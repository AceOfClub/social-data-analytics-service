package com.harish.sdas;

import com.harish.sdas.health.SocialDataAnalyticsServiceCheck;
import com.harish.sdas.resource.DataResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class SocialDataAnalyticsService extends Application<SocialDataAnalyticsServiceConfiguration> {
    public static void main(String[] args) throws Exception {
        new SocialDataAnalyticsService().run(args);
    }

    @Override
    public void run(SocialDataAnalyticsServiceConfiguration configuration, Environment environment) throws Exception {
        registerResources(environment);
        registerHealthChecks(environment);
    }

    private void registerResources(Environment environment) {
        environment.jersey().register(DataResource.class);
    }

    private void registerHealthChecks(Environment environment) {
        environment.healthChecks().register("social-data-analytics-service", new SocialDataAnalyticsServiceCheck());
    }
}
