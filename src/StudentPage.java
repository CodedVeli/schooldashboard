import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class StudentPage extends Application {

    private int userId;
    private Db db;
    private ListView<String> unitListView;
    private ListView<String> gradeListView;
    private ListView<String> feesListView;

    public void setUserId(int userId) {
        this.userId = userId; // Set userId after login
    }

   

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Dashboard");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label unitLabel = new Label("Units:");
        unitListView = new ListView<>();
        refreshUnits();

        TextField unitIdField = new TextField();
        unitIdField.setPromptText("Unit ID");

        Button addUnitButton = new Button("Add Unit");
        addUnitButton.setOnAction(e -> addUnit(unitIdField.getText()));

        Button dropUnitButton = new Button("Drop Unit");
        dropUnitButton.setOnAction(e -> dropUnit(unitIdField.getText()));

        Label gradesLabel = new Label("Grades:");
        gradeListView = new ListView<>();
        refreshGrades();

        Label feesLabel = new Label("Fees:");
        feesListView = new ListView<>();
        refreshFees();

        Button payFeesButton = new Button("Pay Fees");
        payFeesButton.setOnAction(e -> payFees());

        // Layout Setup
        VBox unitBox = new VBox(5, unitLabel, unitListView, unitIdField, addUnitButton, dropUnitButton);
        VBox gradesBox = new VBox(5, gradesLabel, gradeListView);
        VBox feesBox = new VBox(5, feesLabel, feesListView, payFeesButton);

        grid.add(unitBox, 0, 0);
        grid.add(gradesBox, 1, 0);
        grid.add(feesBox, 2, 0);

        Scene scene = new Scene(grid, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void refreshUnits() {
        unitListView.getItems().clear();
        List<String> units = db.readUnitsForUsers(userId);
        unitListView.getItems().addAll(units);
    }

    private void refreshGrades() {
        gradeListView.getItems().clear();
        List<String> grades = db.readGradesForUsers(userId);
        gradeListView.getItems().addAll(grades);
    }

    private void refreshFees() {
        feesListView.getItems().clear();
        List<Boolean> fees = db.readFeesForUsers(userId);
        fees.forEach(paid -> feesListView.getItems().add(paid ? "Paid" : "Not Paid"));
    }

    private void addUnit(String unitId) {
        try {
            int unitIntId = Integer.parseInt(unitId);
            db.addUnitsForStudents(userId, unitIntId, true);
            refreshUnits();
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid unit ID");
        }
    }

    private void dropUnit(String unitId) {
        try {
            int unitIntId = Integer.parseInt(unitId);
            db.dropUnitsForStudents(userId, unitIntId);
            refreshUnits();
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid unit ID");
        }
    }

    private void payFees() {
        db.updateFeesForUsers(userId);
        refreshFees();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

   
}
