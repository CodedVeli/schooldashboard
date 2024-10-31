import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage extends Application {
    private Db database = new Db();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();
            LoginResult loginResult = database.login(email, password);

            if (loginResult != null) {
                if (loginResult.isAdmin()) {
                    new AdminPage().start(primaryStage);
                } else {
                    StudentPage studentPage = new StudentPage();
                    studentPage.setUserId(loginResult.getUserId());
                    studentPage.start(primaryStage);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials. Try again.");
                alert.showAndWait();
            }
        });

        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}