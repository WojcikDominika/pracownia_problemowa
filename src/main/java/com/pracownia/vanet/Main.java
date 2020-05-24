package com.pracownia.vanet;

import com.pracownia.vanet.view.Simulation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Main extends Application {

    /*------------------------ FIELDS REGION ------------------------*/
    private TextField speedField;
    private TextField vehIdField;
    private TextField connEventsField;
    private TextField connVehField;
    private TextField connPointsField;
    private Group root = new Group();
    private boolean isRangeRendered = false;
    private Simulation simulation;
    private long startTime;

    /*------------------------ METHODS REGION ------------------------*/
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });

        this.simulation = new Simulation(root);

        setInterface(simulation);

        Scene scene = new Scene(root, 1400, 850);

        stage.setTitle("Vanet");
        stage.setScene(scene);
        stage.show();
        simulation.getTr().start();

        long startTime = System.currentTimeMillis();
        double durationTime = ((System.currentTimeMillis() - startTime) / 1000.0);
        System.out.println(durationTime + "s");

    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        System.out.println("Simulation started");
    }

    private void stopTimer() {
        double durationTime = ((System.currentTimeMillis() - startTime) / 1000.0);
        System.out.println("Duration: " + durationTime + "s");
        System.out.println("Simulation stopped");
    }

    private void setInterface(Simulation simulation) {
        Button showRangeButton = new Button("Show Range");
        Button changeRangeButton = new Button("ChangeRange");
        Button spawnVehiclesButton = new Button("Spawn Vehicles");
        TextField vehiclesAmountField = new TextField();
        TextField rangeAmountField = new TextField();
        Label rangeAmountLabel = new Label("Range");
        Label vehiclesAmountLabel = new Label("Vehicle Amount");

        // Start stop simulation.
        Button startSimulation = new Button("Start simulation");
        startSimulation.setLayoutX(950.0);
        startSimulation.setLayoutY(310.);
        startSimulation.setOnAction(e -> {
            simulation.setSimulationRunning(true);
            startTimer();
        });

        Button stopSimulation = new Button("Stop simulation");
        stopSimulation.setLayoutX(950.0);
        stopSimulation.setLayoutY(340.);
        stopSimulation.setOnAction(e -> {
            simulation.setSimulationRunning(false);
            stopTimer();
        });

        Button addWormholeVehicle = new Button("Add wormhole attack");
        addWormholeVehicle.setLayoutX(1130.0);
        addWormholeVehicle.setLayoutY(200.00);
        addWormholeVehicle.setOnAction(e -> {
            simulation.addWormholeVehicles();
        });

        Button findHackers = new Button("Find Hackers");
        findHackers.setLayoutX(1130.0);
        findHackers.setLayoutY(4500.00);
        findHackers.setOnAction(e -> {
            simulation.showHackers();
        });

        Button findGreyHoles = new Button("Find grey holes");
        findGreyHoles.setLayoutX(1130.0);
        findGreyHoles.setLayoutY(350.00);
        findGreyHoles.setOnAction(e -> {
            simulation.showGreyHoles();
        });

        Button addGreyholeVehicle = new Button("Add greyhole attack");
        addGreyholeVehicle.setLayoutX(1130.0);
        addGreyholeVehicle.setLayoutY(250.00);
        addGreyholeVehicle.setOnAction(e -> {
            simulation.addGreyholeVehicle();

        });

        Button addBlackholeVehicle = new Button("Add blackhole attack");
        addBlackholeVehicle.setLayoutX(1130.0);
        addBlackholeVehicle.setLayoutY(300.0);
        addBlackholeVehicle.setOnAction(e -> {
            simulation.addBlackholeVehicle();
        });

        Button addSIN = new Button("Add SIN DEFENDER");
        addSIN.setLayoutX(1130.0);
        addSIN.setLayoutY(400.0);
        addSIN.setOnAction(e -> {
            simulation.addSIN();
        });
        // Vehicle informations.
        this.speedField = new TextField();
        speedField.setLayoutX(950.0);
        speedField.setLayoutY(460.0);

        Label speedLabel = new Label("Speed");
        speedLabel.setLayoutX(950.0);
        speedLabel.setLayoutY(490.0);

        this.vehIdField = new TextField();
        vehIdField.setLayoutX(950.0);
        vehIdField.setLayoutY(520.0);

        Label vehIdLabel = new Label("Veh id");
        vehIdLabel.setLayoutX(950.0);
        vehIdLabel.setLayoutY(550.0);

        this.connPointsField = new TextField();
        connPointsField.setLayoutX(950.0);
        connPointsField.setLayoutY(580.0);

        Label connPointsLabel = new Label("Conn points");
        connPointsLabel.setLayoutX(950.0);
        connPointsLabel.setLayoutY(610.0);

        this.connEventsField = new TextField();
        connEventsField.setLayoutX(950.0);
        connEventsField.setLayoutY(640.0);

        Label connEventsLabel = new Label("collectedEvents");
        connEventsLabel.setLayoutX(950.0);
        connEventsLabel.setLayoutY(670.0);

        this.connVehField = new TextField();
        connVehField.setLayoutX(950.0);
        connVehField.setLayoutY(700.0);

        Label connVehLabel = new Label("connectedVehicles");
        connVehLabel.setLayoutX(950.0);
        connVehLabel.setLayoutY(730.0);

        showRangeButton.setLayoutX(950.0);
        showRangeButton.setLayoutY(80.0);

        changeRangeButton.setLayoutX(950.0);
        changeRangeButton.setLayoutY(110.0);
        rangeAmountLabel.setLayoutX(950.0);
        rangeAmountLabel.setLayoutY(140.0);
        rangeAmountField.setLayoutX(950.0);
        rangeAmountField.setLayoutY(160.0);
        rangeAmountField.setText("40.0");

        spawnVehiclesButton.setLayoutX(950.0);
        spawnVehiclesButton.setLayoutY(190.0);
        vehiclesAmountLabel.setLayoutX(950.0);
        vehiclesAmountLabel.setLayoutY(220.0);
        vehiclesAmountField.setLayoutX(950.0);
        vehiclesAmountField.setLayoutY(240.0);
        vehiclesAmountField.setText("10");

        changeRangeButton.setOnAction(e -> simulation.changeVehiclesRanges(Double.parseDouble(rangeAmountField
                                                                                                      .getText())));

        showRangeButton.setOnAction(e -> {
            isRangeRendered = !isRangeRendered;
            if (isRangeRendered) {
                simulation.switchOnRangeCircles();
            } else {
                simulation.switchOffRangeCircles();
            }
        });

        spawnVehiclesButton.setOnAction(e -> {
            simulation.addVehicles(Integer.parseInt(vehiclesAmountField.getText()));
        });

        root.getChildren()
            .addAll(showRangeButton,
                    spawnVehiclesButton,
                    vehiclesAmountField,
                    stopSimulation,
                    findHackers,
                    findGreyHoles,
                    speedField,
                    speedLabel,
                    vehIdField,
                    vehIdLabel,
                    connPointsField,
                    connPointsLabel,
                    connEventsField,
                    connEventsLabel,
                    connVehField,
                    connVehLabel,
                    startSimulation,
                    addWormholeVehicle,
                    addGreyholeVehicle,
                    addBlackholeVehicle,
                    vehiclesAmountLabel,
                    rangeAmountLabel,
                    rangeAmountField,
                    addSIN,
                    changeRangeButton);


    }
}
    