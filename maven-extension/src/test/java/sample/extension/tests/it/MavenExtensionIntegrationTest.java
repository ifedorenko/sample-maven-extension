package sample.extension.tests.it;

import io.takari.maven.testing.TestDependencies;
import io.takari.maven.testing.TestProperties;
import io.takari.maven.testing.TestResources;
import io.takari.maven.testing.it.MavenVersions;
import io.takari.maven.testing.it.VerifierResult;
import io.takari.maven.testing.it.VerifierRuntime;

import java.io.File;

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

  public final TestProperties proprties = new TestProperties();

  public final TestDependencies dependencies = new TestDependencies(proprties);

  @Parameters(name = "maven-{0}")
  public static Iterable<Object[]> mavenVersions() {
    return MavenVersions.asJunitParameters("3.2.1", "3.2.2");
  }

  public MavenExtensionIntegrationTest(String mavenVersion) throws Exception {
    this.resources = new TestResources("src/test/projects", "target/it/" + mavenVersion + "/");
    this.verifier = VerifierRuntime.builder(mavenVersion) //
        .withExtensions(dependencies.getRuntimeClasspath()) //
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
