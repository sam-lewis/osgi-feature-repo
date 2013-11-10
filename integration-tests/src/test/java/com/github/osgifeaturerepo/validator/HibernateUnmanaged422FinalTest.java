package com.github.osgifeaturerepo.validator;


public class HibernateUnmanaged422FinalTest extends FeatureValidator {

    @Override
    protected String getMavenArtifact() {
        return "hibernate-feature";
    }

    @Override
    protected String getFeatureName() {
        return "hibernate-unmanaged";
    }

    @Override
    protected String getFeatureVersion() {
        return "4.2.2.Final";
    }
}
