package sample.extension.tests.it;

import io.takari.maven.testing.TestProperties;
import io.takari.maven.testing.TestResources;
import io.takari.maven.testing.it.VerifierResult;
import io.takari.maven.testing.it.VerifierRuntime;

import java.io.File;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MavenExtensionIntegrationTest {

  @Rule
  public final TestResources resources;

  public final VerifierRuntime verifier;

  public final TestProperties proprties;

  @Parameters(name = "maven-{0}")
  public static Iterable<Object[]> mavenVersions() {
    return Arrays.<Object[]>asList( //
        new Object[] {"3.2.1"} //
        , new Object[] {"3.2.2"} //
        );
  }

  public MavenExtensionIntegrationTest(String mavenVersion) throws Exception {
    this.resources = new TestResources("src/test/projects", "target/it/" + mavenVersion + "/");
    this.proprties = new TestProperties();
    this.verifier = VerifierRuntime.builder(mavenVersion) //
        .withExtension(new File("target/classes").getCanonicalFile()) //
        .build();
  }

  @Test
  public void testBasic() throws Exception {
    File basedir = resources.getBasedir("basic-it");
    VerifierResult result = verifier.forProject(basedir) //
        .withCliOption("-X") //
        .execute("package");
    result.assertErrorFreeLog();
  }

}
