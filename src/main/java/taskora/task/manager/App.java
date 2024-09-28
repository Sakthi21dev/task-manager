package taskora.task.manager;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import taskora.task.manager.model.TaskDetails;
import taskora.task.manager.service.TaskRepository;
import taskora.task.manager.utils.FxmlUtils;

public class App extends Application {

	private Stage primaryStage;
	private static BorderPane rootLayout;

	TaskRepository repo = TaskRepository.getInstance();
	ObservableList<TaskDetails> tasks = repo.getTasks();

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

	public void setHomeScene() throws IOException {

		rootLayout.setCenter(FxmlUtils.loadFXML("TaskOfTheDayLayout"));

	}

	public static void setRoot(String fxml) throws IOException {
		rootLayout.setCenter(FxmlUtils.loadFXML(fxml));
	}

	

	public static void main(String[] args) {
		launch(args);
	}

}
