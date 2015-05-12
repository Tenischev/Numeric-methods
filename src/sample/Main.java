package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    private double fieldWidth = 70;
    private String fileName = "points";
    private Double scale = 10.0;

    List<Double> xPoints = new ArrayList<>();
    List<Double> yPoints = new ArrayList<>();
    List<Double> zPoints = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        ScrollPane scrollPane = new ScrollPane(root);

        VBox centerDelimiter = new VBox();
        centerDelimiter.setAlignment(Pos.CENTER);
        root.getChildren().add(centerDelimiter);

        HBox verticalMenu = new HBox();
        verticalMenu.setAlignment(Pos.TOP_CENTER);
        centerDelimiter.getChildren().add(verticalMenu);

        GridPane verticalGraphDelimiter = new GridPane();
        verticalGraphDelimiter.setAlignment(Pos.CENTER);
        verticalGraphDelimiter.setHgap(20);
        verticalGraphDelimiter.setVgap(20);
        verticalGraphDelimiter.setPadding(new Insets(25, 25, 25, 25));
        centerDelimiter.getChildren().add(verticalGraphDelimiter);

        VBox graph2D = new VBox();
        graph2D.setAlignment(Pos.CENTER);
        verticalGraphDelimiter.getChildren().add(graph2D);

        VBox graph3D = new VBox();
        graph3D.setAlignment(Pos.CENTER);

        verticalGraphDelimiter.getChildren().add(graph3D);
        final ComboBox method = new ComboBox();
        final List<String> methods = new ArrayList<>(Arrays.asList("явный Эйлер", "неявный Эйлер", "явный Рунге-Кутта", "явный Адамса", "неявный Адамса"));
        method.getItems().addAll(methods.toArray());
        method.setValue(methods.get(0));
        verticalMenu.getChildren().add(method);

        Text sigmaText = new Text("δ:");
        verticalMenu.getChildren().add(sigmaText);

        final TextField sigmaField = new TextField("10");
        sigmaField.setMaxWidth(fieldWidth);
        verticalMenu.getChildren().add(sigmaField);

        Text bText = new Text("b:");
        verticalMenu.getChildren().add(bText);

        final TextField bField = new TextField("2.666");
        bField.setMaxWidth(fieldWidth);
        verticalMenu.getChildren().add(bField);

        Text rText = new Text("r:");
        verticalMenu.getChildren().add(rText);

        final TextField rField = new TextField();
        rField.setMaxWidth(fieldWidth);
        verticalMenu.getChildren().add(rField);

        Text stepText = new Text("∆T:");
        verticalMenu.getChildren().add(stepText);

        final TextField stepField = new TextField();
        stepField.setMaxWidth(fieldWidth);
        verticalMenu.getChildren().add(stepField);

        Text xText = new Text("X0:");
        verticalMenu.getChildren().add(xText);

        final TextField xField = new TextField();
        xField.setMaxWidth(fieldWidth);
        verticalMenu.getChildren().add(xField);

        Text yText = new Text("Y0:");
        verticalMenu.getChildren().add(yText);

        final TextField yField = new TextField();
        yField.setMaxWidth(fieldWidth);
        verticalMenu.getChildren().add(yField);

        Text zText = new Text("Z0:");
        verticalMenu.getChildren().add(zText);

        final TextField zField = new TextField();
        zField.setMaxWidth(fieldWidth);
        verticalMenu.getChildren().add(zField);

        Text zoom = new Text("Zoom:");
        verticalMenu.getChildren().add(zoom);

        final TextField zoomField = new TextField(scale.toString());
        zField.setMaxWidth(fieldWidth);
        verticalMenu.getChildren().add(zoomField);

        Text steps = new Text("Steps:");
        verticalMenu.getChildren().add(steps);

        final TextField stepsField = new TextField(scale.toString());
        zField.setMaxWidth(fieldWidth);
        verticalMenu.getChildren().add(stepsField);

        Line lineX = new Line();
        lineX.setStartX(-1.0 * scale);
        lineX.setStartY(0);
        lineX.setEndX(10 * scale);
        lineX.setEndY(0);
        Line lineY = new Line();
        lineY.setStartX(0);
        lineY.setStartY(-10.0 * scale);
        lineY.setEndX(0);
        lineY.setEndY(5.0 * scale);

        final Polyline polylineX = new Polyline();
        polylineX.getPoints().addAll();
        //verticalGraphDelimiter.add(lineX, 0, 0);
        //verticalGraphDelimiter.add(lineY, 0, 0);
        verticalGraphDelimiter.add(polylineX, 0, 0);

        lineX = new Line();
        lineX.setStartX(-1.0 * scale);
        lineX.setStartY(0);
        lineX.setEndX(10 * scale);
        lineX.setEndY(0);
        lineY = new Line();
        lineY.setStartX(0);
        lineY.setStartY(-10.0 * scale);
        lineY.setEndX(0);
        lineY.setEndY(5.0 * scale);

        final Polyline polylineY = new Polyline();
        polylineY.getPoints().addAll();
        //verticalGraphDelimiter.add(lineX, 1, 0);
        //verticalGraphDelimiter.add(lineY, 1, 0);
        verticalGraphDelimiter.add(polylineY, 1, 0);

        lineX = new Line();
        lineX.setStartX(-1.0 * scale);
        lineX.setStartY(0);
        lineX.setEndX(10 * scale);
        lineX.setEndY(0);
        lineY = new Line();
        lineY.setStartX(0);
        lineY.setStartY(-10.0 * scale);
        lineY.setEndX(0);
        lineY.setEndY(5.0 * scale);

        final Polyline polylineZ = new Polyline();
        polylineZ.getPoints().addAll();
        //verticalGraphDelimiter.add(lineX, 2, 0);
        //verticalGraphDelimiter.add(lineY, 2, 0);
        verticalGraphDelimiter.add(polylineZ, 2, 0);

        final List<PathElement> coordinateXYZ = new ArrayList<>();
        coordinateXYZ.add(new MoveTo(0, 0));
        coordinateXYZ.add(new LineTo(0, -10.0 * scale));
        coordinateXYZ.add(new MoveTo(0, 0));
        coordinateXYZ.add(new LineTo(10.0 * scale, 0));
        coordinateXYZ.add(new MoveTo(0, 0));
        coordinateXYZ.add(new LineTo(-10.0 * 0.707 * scale, 10.0 * 0.707 * scale));

        final Path pathXYZ = new Path();
        verticalGraphDelimiter.add(pathXYZ, 3, 0);

        final List<Double> coordinate = new ArrayList<>();

        Button compute = new Button("Посчитать!");
        compute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Double r = 0.0;
                Double step = 0.0;
                xPoints.clear();
                yPoints.clear();
                zPoints.clear();
                r = Double.parseDouble(rField.getText());
                step = Double.parseDouble(stepField.getText());
                scale = Double.parseDouble(zoomField.getText());
                Runtime runtime = Runtime.getRuntime();
                double minX = -1;
                double maxX = 20;
                double minY = -10;
                double maxY = 10;
                try {
                    String command = String.format("./compute -S %s -B %s -R %s -s %s -n %d -x %s -y %s -z %s -p %s -m %d", sigmaField.getText(), bField.getText(), r.toString(), step.toString(), 5000, xField.getText(), yField.getText(), zField.getText(), fileName, methods.indexOf(method.getValue()));
                    System.out.println(command);
                    Process process = runtime.exec(command);
                    while (process.isAlive()) {
                    }
                    File file = new File(".", fileName + ".xyz");
                    int i = 0;
                    Double d;
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNext()) {
                        minX = Math.min(minX, i * step);
                        maxX = Math.max(maxX, i * step);
                        xPoints.add(i * step * scale);
                        d = Double.parseDouble(scanner.next());
                        minY = Math.min(minY, d);
                        maxY = Math.max(maxY, d);
                        xPoints.add(d * scale);
                        yPoints.add(i * step * scale);
                        d = Double.parseDouble(scanner.next());
                        minY = Math.min(minY, d);
                        maxY = Math.max(maxY, d);
                        yPoints.add(d * scale);
                        zPoints.add(i * step * scale);
                        d = Double.parseDouble(scanner.next());
                        minY = Math.min(minY, d);
                        maxY = Math.max(maxY, d);
                        zPoints.add(d * scale);
                        i++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                coordinate.clear();
                coordinate.add(0.0);
                coordinate.add(0.0);
                coordinate.add(minX * scale);
                coordinate.add(0.0);
                coordinate.add(0.0);
                coordinate.add(0.0);
                coordinate.add(maxX * scale);
                coordinate.add(0.0);
                coordinate.add(0.0);
                coordinate.add(0.0);
                coordinate.add(0.0);
                coordinate.add(maxY * scale);
                coordinate.add(0.0);
                coordinate.add(0.0);
                coordinate.add(0.0);
                coordinate.add(minY * scale);
                coordinate.add(0.0);
                coordinate.add(0.0);
                polylineX.getPoints().clear();
                polylineY.getPoints().clear();
                polylineZ.getPoints().clear();
                polylineX.getPoints().addAll(coordinate);
                polylineY.getPoints().addAll(coordinate);
                polylineZ.getPoints().addAll(coordinate);
                polylineX.getPoints().addAll(xPoints);
                polylineY.getPoints().addAll(yPoints);
                polylineZ.getPoints().addAll(zPoints);
                List<Double> xyzPoints = new ArrayList<>();
                pathXYZ.getElements().clear();
                pathXYZ.getElements().addAll(coordinateXYZ);
                for (int i = 1; i < xPoints.size(); i += 2) {
                    xyzPoints.add(xPoints.get(i) - 0.707 * zPoints.get(i));
                    xyzPoints.add(-1.0 * yPoints.get(i) + 0.707 * zPoints.get(i));
                }
                pathXYZ.getElements().addAll(new MoveTo(xyzPoints.get(0), xyzPoints.get(1)));
                for (int i = 2; i < xyzPoints.size(); i += 2) {
                    pathXYZ.getElements().addAll(new LineTo(xyzPoints.get(i), xyzPoints.get(i + 1)));
                }
            }
        });
        verticalMenu.getChildren().add(compute);

        Scene scene = new Scene(scrollPane, 800, 600, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
