module taskora.taskora {
	exports taskora.task.manager.model;
	exports taskora.task.manager.constants;
	exports taskora.task.manager.controller;
	exports taskora.task.manager.utils;
	exports taskora.task.manager;
	exports taskora.task.manager.service;

	opens taskora.task.manager to javafx.fxml; 
	opens taskora.task.manager.controller to javafx.fxml; 
	   
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires transitive javafx.graphics;
}