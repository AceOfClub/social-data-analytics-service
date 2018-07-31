package com.harish.sdas.health;

import com.codahale.metrics.health.HealthCheck;

public class SocialDataAnalyticsServiceCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
