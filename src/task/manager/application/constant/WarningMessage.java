/**
 * 
 */
package task.manager.application.constant;

/**
 * 
 */
public enum WarningMessage {
	
	EMPTY_TASK_NAME("Task name should not be empty!"),
	EMPTY_SPEND_HOURS("Spend hours should not be empty!"),
	INVALID_SPEND_HOURS_FORMAT("Invalid Spend hours format. Please use 'hour:min' format.");

	String message;
	WarningMessage(String message) {
		this.message = message; 
	} 
	
	@Override 
	public String toString() { 
	    return this.message; 
	}
}
