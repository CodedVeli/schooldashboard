import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AdminPage extends Application {

    private Db database = new Db(); // instance of Db class to access database methods

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin Dashboard");

        // GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // User Registration Section
        Label userLabel = new Label("Register User:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        CheckBox isAdminCheck = new CheckBox("Is Admin?");
        Button registerButton = new Button("Register User");
        registerButton.setOnAction(e -> {
            database.register(usernameField.getText(), passwordField.getText(), emailField.getText(), isAdminCheck.isSelected());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "User Registered Successfully!");
            alert.showAndWait();
        });

        // Add components to grid
        grid.add(userLabel, 0, 0);
        grid.add(usernameField, 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(passwordField, 2, 1);
        grid.add(isAdminCheck, 3, 1);
        grid.add(registerButton, 4, 1);

        // Unit Management Section
        Label unitLabel = new Label("Manage Units:");
        TextField unitCodeField = new TextField();
        unitCodeField.setPromptText("Unit Code");
        TextField unitNameField = new TextField();
        unitNameField.setPromptText("Unit Name");
        Button createUnitButton = new Button("Create Unit");
        createUnitButton.setOnAction(e -> {
            database.createUnits(unitCodeField.getText(), unitNameField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Unit Created Successfully!");
            alert.showAndWait();
        });

        grid.add(unitLabel, 0, 3);
        grid.add(unitCodeField, 0, 4);
        grid.add(unitNameField, 1, 4);
        grid.add(createUnitButton, 2, 4);

        // View All Students Button
        Button viewStudentsButton = new Button("View All Students");
        viewStudentsButton.setOnAction(e -> {
            // Logic to fetch and display all students
        });
        grid.add(viewStudentsButton, 0, 6);

        // View All Units Button
        Button viewUnitsButton = new Button("View All Units");
        viewUnitsButton.setOnAction(e -> {
            // Logic to fetch and display all units
        });
        grid.add(viewUnitsButton, 1, 6);

        // Assign Units to Students
        Label assignUnitLabel = new Label("Assign Unit to Student:");
        TextField userIdField = new TextField();
        userIdField.setPromptText("User ID");
        TextField unitIdField = new TextField();
        unitIdField.setPromptText("Unit ID");
        CheckBox activeStatusCheck = new CheckBox("Active");
        Button assignUnitButton = new Button("Assign Unit");
        assignUnitButton.setOnAction(e -> {
            database.addUnitsForStudents(Integer.parseInt(userIdField.getText()), Integer.parseInt(unitIdField.getText()), activeStatusCheck.isSelected());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Unit Assigned Successfully!");
            alert.showAndWait();
        });

        grid.add(assignUnitLabel, 0, 7);
        grid.add(userIdField, 0, 8);
        grid.add(unitIdField, 1, 8);
        grid.add(activeStatusCheck, 2, 8);
        grid.add(assignUnitButton, 3, 8);

        // Drop Unit for Student
        Button dropUnitButton = new Button("Drop Unit");
        dropUnitButton.setOnAction(e -> {
            database.dropUnitsForStudents(Integer.parseInt(userIdField.getText()), Integer.parseInt(unitIdField.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Unit Dropped Successfully!");
            alert.showAndWait();
        });
        grid.add(dropUnitButton, 4, 8);

        // Create and Update Fees Section
        Label feesLabel = new Label("Manage Fees:");
        TextField feesUserIdField = new TextField();
        feesUserIdField.setPromptText("User ID");
        CheckBox paidStatusCheck = new CheckBox("Paid?");
        Button createFeesButton = new Button("Create Fees");
        createFeesButton.setOnAction(e -> {
            database.createFees(Integer.parseInt(feesUserIdField.getText()), paidStatusCheck.isSelected());
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Fees Created Successfully!");
            alert.showAndWait();
        });
        Button updateFeesButton = new Button("Update Fees");
        updateFeesButton.setOnAction(e -> {
            database.updateFeesForUsers(Integer.parseInt(feesUserIdField.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Fees Updated Successfully!");
            alert.showAndWait();
        });

        grid.add(feesLabel, 0, 9);
        grid.add(feesUserIdField, 0, 10);
        grid.add(paidStatusCheck, 1, 10);
        grid.add(createFeesButton, 2, 10);
        grid.add(updateFeesButton, 3, 10);

        // Set up scene and stage
        Scene scene = new Scene(grid, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
