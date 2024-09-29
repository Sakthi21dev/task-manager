package taskora.task.manager;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import taskora.task.manager.utils.FxmlUtils;

public class App extends Application {

  private static ConfigurableApplicationContext context;

  private static BorderPane rootLayout;
  public static ConfigurableApplicationContext getApplicationContext() {
    return context;
  }

  public static void main(String[] args) {
    launch(args);
  }

  public static void setRoot(String fxml) throws IOException {
    rootLayout.setCenter(FxmlUtils.loadFXML(fxml));
  }

  private Stage primaryStage;

  @Override
  public void init() {
    context = SpringApplication.run(MainApp.class);
  }

  public void setHomeScene() throws IOException {

    rootLayout.setCenter(FxmlUtils.loadFXML("TaskOfTheDayLayout"));

  }

  @Override
  public void start(Stage primaryStage) {
    try {
      this.primaryStage = primaryStage;
      rootLayout = (BorderPane) FxmlUtils.loadFXML("RootLayout");
      Scene scene = new Scene(rootLayout);
      scene.getStylesheets().add(getClass().getResource("style/application.css").toExternalForm());
      primaryStage.setTitle("Taskora");
      this.primaryStage.setScene(scene);
      this.primaryStage.show();
      setHomeScene();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void stop() {
    context.close();
  }

}
