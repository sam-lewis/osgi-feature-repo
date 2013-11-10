package com.github.osgifeaturerepo.hibernate;

import org.apache.karaf.features.FeaturesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import javax.inject.Inject;
import java.io.File;

import static org.apache.karaf.tooling.exam.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.apache.karaf.tooling.exam.options.KarafDistributionOption.keepRuntimeFolder;
import static org.junit.Assert.fail;
import static org.ops4j.pax.exam.CoreOptions.maven;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public abstract class FeatureValidator {

    @Inject
    private FeaturesService featuresService;

    @Inject
    private BundleContext bundleContext;

    @Configuration
    public Option[] config() {
        return new Option[]{karafDistributionConfiguration().frameworkUrl(
                maven().groupId("org.apache.karaf").artifactId("apache-karaf").type("zip").versionAsInProject())
                .karafVersion("2.3.2").name("Apache Karaf").unpackDirectory(new File("target")),
                keepRuntimeFolder()};
    }

    @Before
    public void setup() throws Exception {
        featuresService.addRepository(new File("../../src/main/feature/feature.xml").toURI());
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

    abstract protected String getFeatureName();

    abstract protected String getFeatureVersion();


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
