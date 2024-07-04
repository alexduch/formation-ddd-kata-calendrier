import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FilePresistence implements EventSaver {

  @Override
  public void save(Event e) {
    try {
      String serializedEvent = new ObjectMapper().writeValueAsString(e);
      Files.writeString(Path.of("events.json"), serializedEvent, StandardOpenOption.APPEND);
    } catch (JsonProcessingException ex) {
      throw new RuntimeException(ex);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }
}
