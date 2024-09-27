module TaskManager {
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.base;

	exports task.manager.application; // Main application package
	exports task.manager.application.controller; // Controller package
	exports task.manager.application.model;

	opens task.manager.application to javafx.graphics, javafx.fxml;
	opens task.manager.application.controller to javafx.fxml;
}
