package sample;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    private GridPane grid = new GridPane();

    private TextField t1 = new TextField();
    private TextField t2 = new TextField();
    private TextField t3 = new TextField();
    private TextField t4 = new TextField();
    private TextField t5 = new TextField();
    private TextField t6 = new TextField();

    private Button submit = new Button("Submit");

    @Override
    public void start(Stage primaryStage) throws Exception{

        grid.setPadding( new Insets(50));
        grid.setVgap( 15);
        grid.setHgap( 10);

        grid.add(new Label("Number of days on the trip:"), 0, 0);
        grid.add(t1, 2, 0);

        grid.add(new Label("Transportation (choose one):"), 0, 2);
        grid.add(new Label("Airfare cost:"), 2, 2);
        grid.add(t2, 2, 3);
        grid.add(new Label("Miles driven:"), 2, 4);
        grid.add(t3, 2, 5);

        grid.add(new Label("Conference registration cost:"), 0, 7);
        grid.add(t4, 2, 7);

        grid.add(new Label("Lodging cost (per night):"), 0, 9);
        grid.add(t5, 2, 9);

        grid.add(new Label("Total food cost:"), 0, 11);
        grid.add(t6, 2, 11);

        grid.add(submit, 1, 14);

        t2.textProperty().addListener(event -> {
           if (t2 != null) t3.setDisable(true);
        });

        t3.textProperty().addListener(event -> {
            if (t3 != null) t2.setDisable(true);
        });

        t1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    t1.setText(oldValue);
                }
            }
        });

        t2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    t2.setText(oldValue);
                }
            }
        });

        t3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    t3.setText(oldValue);
                }
            }
        });

        t4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    t4.setText(oldValue);
                }
            }
        });

        t5.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    t5.setText(oldValue);
                }
            }
        });

        t6.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    t6.setText(oldValue);
                }
            }
        });

        BooleanBinding booleanBind = t1.textProperty().isEmpty()
                .or(t4.textProperty().isEmpty())
                .or(t5.textProperty().isEmpty())
                .or(t6.textProperty().isEmpty());
        submit.disableProperty().bind(booleanBind);

        submit.setOnAction(event -> {
            int days = Integer.parseInt(t1.getText());

            double airfareCost = 0;
            double miles = 0;
            if (t2.textProperty() != null) {
                airfareCost = Double.parseDouble(t2.getText());         // Reimbursed
            }
            else {
                miles = Double.parseDouble(t3.getText());               // $0.42 per mile
            }

            double regCost = Double.parseDouble(t4.getText());
            double lodgingCost = Double.parseDouble(t5.getText());      // reimbursed if < $195
            double foodCost = Double.parseDouble(t6.getText());         // reimbursed if < $47

            double totalFoodCost = foodCost;
            if (foodCost / days < 47) totalFoodCost = 0;

            double totalLodgingCost = days * lodgingCost;
            if (lodgingCost < 195) totalLodgingCost = 0;

            double totalTravelCost = 0;
            if (t3.getText() != null) totalTravelCost = miles * 0.42;

            double totalExpenses = foodCost + lodgingCost + regCost + totalTravelCost + airfareCost;
            double totalOwed = totalFoodCost + totalLodgingCost + regCost + totalTravelCost;

            grid.getChildren().clear();

            grid.add(new Label("Total expenses incurred: $" + totalExpenses), 2, 0);
            grid.add(new Label("Total owed: $" + totalOwed), 2, 2);
        });

        primaryStage.setTitle("Expense Calculator");
        primaryStage.setScene(new Scene(grid));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
