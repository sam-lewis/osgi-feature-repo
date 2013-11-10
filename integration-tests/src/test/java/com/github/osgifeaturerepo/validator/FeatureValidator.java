package com.github.osgifeaturerepo.validator;

import com.github.osgifeaturerepo.KarafTest;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

import static org.junit.Assert.fail;

public abstract class FeatureValidator extends KarafTest {

    @Before
    public void setup() throws Exception {
        addFeatureRepository(getMavenArtifact());
    }

    @Test
    public void testFeature() throws Exception {
        String name = getFeatureName();
        String version = getFeatureVersion();

        System.out.println("Installing Feature [" + name + "] v[" + version + "] on Apache Karaf....");

        featuresService.installFeature(name, version);

        System.out.println("Installed [" + name + "] v[" + version + "] OK!");

        checkNoUnVersionedBundles();
    }

    protected abstract String getMavenArtifact();

    protected abstract String getFeatureName();

    protected abstract String getFeatureVersion();


    private void checkNoUnVersionedBundles() {
        System.out.println("Checking that no bundles have version 0.0.0");
        for (Bundle bundle : bundleContext.getBundles()) {
            if (bundle.getVersion().toString().equals("0.0.0"))
                if (!bundle.getSymbolicName().matches(".*(junit|PAXEXAM).*")) {
                    fail("Bundle [" + bundle.getSymbolicName() + "] has version [0.0.0]");
                }
        }
        System.out.println("No bundles have version 0.0.0 :-)");
    }
}
