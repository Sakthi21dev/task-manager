package taskora.task.manager.constants;

public enum DateConstants {

  DEFAULT_DATE_FORMAT("dd-MMM-yyyy");

  String value;

  DateConstants(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
