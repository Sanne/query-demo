import demo.workers.BallotWorker;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * @author - @navssurtani
 */
public class BallotWorkerTest {

	private static DefaultCacheManager defaultCacheManager;

	@BeforeClass
	public static void startInfinispan() throws IOException {
		defaultCacheManager = new DefaultCacheManager( "localonly-infinispan.xml" );
		Cache testCache = defaultCacheManager.getCache( "testCache", true );
	}

	@AfterClass
	public static void stopInfinispan() {
		if (defaultCacheManager != null)
			defaultCacheManager.stop();
	}

	@Test
	public void testCorrectJson() {
		String correct = "{\"name\":\"Another\",\"age\":\"75\"," + "\"governorVote\":\"Stewie Griffin\"," + "\"senateVote\":\"Homer Simpson\","
				+ "\"regionSelection\":2}";
		BallotWorker ballotWorker = new BallotWorker();

		try {
			ballotWorker.work( correct );
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail( "There shouldn't be an exception here. The worker should " + "run smoothly" );
		}
	}

}
