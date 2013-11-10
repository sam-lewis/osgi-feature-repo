package com.github.osgifeaturerepo.validator;


public class Aries100Test extends FeatureValidator {

    @Override
    protected String getMavenArtifact() {
        return "aries-feature";
    }

    @Override
    protected String getFeatureName() {
        return "aries";
    }

    @Override
    protected String getFeatureVersion() {
        return "1.0.0";
    }
}
