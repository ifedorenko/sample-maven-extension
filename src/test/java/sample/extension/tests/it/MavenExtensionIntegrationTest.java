package sample.extension.tests.it;

import io.takari.maven.testing.TestProperties;
import io.takari.maven.testing.TestResources;
import io.takari.maven.testing.executor.MavenExecutionResult;
import io.takari.maven.testing.executor.MavenRuntime;
import io.takari.maven.testing.executor.MavenRuntime.MavenRuntimeBuilder;
import io.takari.maven.testing.executor.MavenVersions;
import io.takari.maven.testing.executor.junit.MavenJUnitTestRunner;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MavenJUnitTestRunner.class)
@MavenVersions({"3.2.2", "3.2.5"})
public class MavenExtensionIntegrationTest {

  @Rule
  public final TestResources resources = new TestResources();

  public final TestProperties proprties = new TestProperties();

  public final MavenRuntime verifier;

  public MavenExtensionIntegrationTest(MavenRuntimeBuilder runtimeBuilder) throws Exception {
    this.verifier = runtimeBuilder.withExtensions(proprties.getRuntimeClasspath()) //
        .build();
  }

  @Test
  public void testBasic() throws Exception {
    File basedir = resources.getBasedir("basic-it");
    MavenExecutionResult result = verifier.forProject(basedir) //
        .withCliOption("-X") //
        .execute("package");
    result.assertErrorFreeLog();
    result.assertLogText("MavenExtension#init");
    result.assertLogText("MavenExtension#onEvent");
    result.assertLogText("MavenExtension#close");
  }

}
