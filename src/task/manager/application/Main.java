package task.manager.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import task.manager.application.model.TaskDetails;
import task.manager.application.service.TaskRepository;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	TaskRepository repo = TaskRepository.getInstance();
	ObservableList<TaskDetails> tasks = repo.getTasks();

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			rootLayout = (BorderPane) FXMLLoader.load(getClass().getResource("RootLayout.fxml"));
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Taskora");
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			setHomeScene();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setHomeScene() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view/TaskOfTheDayLayout.fxml"));
		AnchorPane singleTaskDetails = loader.load();
		rootLayout.setCenter(singleTaskDetails);

	}

	public static void main(String[] args) {
		launch(args);
	}

	
}
