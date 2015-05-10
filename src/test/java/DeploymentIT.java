import java.io.IOException;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.query.Search;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.spec.se.manifest.ManifestDescriptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DeploymentIT {

	private static final String CONFIG_NAME = "clustered-infinispan.xml";

	@Deployment(name="node1") @TargetsContainer("container.active-1")
	public static Archive<?> deploymentOne() {
		return createDeployment();
	}

	@Deployment(name="node2") @TargetsContainer("container.active-2")
	public static Archive<?> deploymentTwo() {
		return createDeployment();
	}

	private static Archive<?> createDeployment() {
		StringAsset manifest = new StringAsset(Descriptors.create(ManifestDescriptor.class)
				.attribute("Dependencies", "org.infinispan services, org.infinispan.query services, org.infinispan.commons")
				.exportAsString());
		return ShrinkWrap.create(WebArchive.class, "datagrid-queries.war")
				.addPackage("demo")
				.addPackage("demo.service")
				.add(manifest, "META-INF/MANIFEST.MF")
				.addAsResource(CONFIG_NAME, CONFIG_NAME);
	}

	@Test @InSequence(1) @OperateOnDeployment("node1")
	public void testDeploySuccessfull() throws IOException {
		System.out.println(" -- DEPLOY ONE: Start-- ");
		DefaultCacheManager gridNodeOne = startAndVerifyDeployment();

		//Verify there are is only one node running so far
		Assert.assertEquals(1, gridNodeOne.getTransport().getMembers().size());
		System.out.println(" -- DEPLOY ONE: Success-- ");
	}

	@Test @InSequence(2) @OperateOnDeployment("node2")
	public void testDeployIsClustered() throws IOException {
		System.out.println(" -- DEPLOY TWO: Start -- ");
		DefaultCacheManager gridNodeTwo = startAndVerifyDeployment();

		//Now we should have two nodes in the datagrid
		Assert.assertEquals(2, gridNodeTwo.getTransport().getMembers().size());
		System.out.println(" -- DEPLOY TWO: Success-- ");
	}

	private DefaultCacheManager startAndVerifyDeployment() throws IOException {
		DefaultCacheManager cacheManager = new DefaultCacheManager(CONFIG_NAME);
		cacheManager.start();

		Cache<Object, Object> candidatesCache = cacheManager.getCache("Candidates");

		//Verify it's an indexed cache (otherwise you get an exception):
		Search.getSearchManager(candidatesCache);

		Cache<Object, Object> votesCache = cacheManager.getCache("Votes");
		//Verify it's an indexed cache (otherwise you get an exception):
		Search.getSearchManager(votesCache);

		return cacheManager;
	}

}
