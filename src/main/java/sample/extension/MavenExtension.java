package sample.extension;

import javax.inject.Named;

import org.apache.maven.eventspy.EventSpy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
public class MavenExtension implements EventSpy {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public void init(Context context) throws Exception {
    log.info("MavenExtension#init {}", context);
  }

  @Override
  public void onEvent(Object event) throws Exception {
    log.info("MavenExtension#onEvent {}", event);
  }

  @Override
  public void close() throws Exception {
    log.info("MavenExtension#close {}");
  }

}
