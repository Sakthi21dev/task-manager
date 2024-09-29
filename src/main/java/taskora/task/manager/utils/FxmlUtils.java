package taskora.task.manager.utils;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import taskora.task.manager.App;

public class FxmlUtils {

  public static URL getURL(String fxml) throws IOException {
    return App.class.getResource(fxml + ".fxml");
  }

  public static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }
}
