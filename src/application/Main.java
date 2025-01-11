package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Main extends Application {
	private StackPane primaryViewPane = new StackPane();
	private Button btnDiscover = new Button("discover now");
	private Button btnBack = new Button("<<< go back");
	private File file;
	private static Graph graph;
	private static TableEntry[] table;
	private ComboBox<Vertex> cmboSource;
	private ComboBox<Vertex> cmboTarget;
	private int timeClicked = 0;
	private MinHeap<TableEntry> heap;
	private DoublyEndedLinkedList<Integer> altered = new DoublyEndedLinkedList<>();; // to store the indices of altered
																						// vertices in the table
	private Bounds bounds;
	private int numberOfLines;

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryView(primaryStage);
			Scene scene = new Scene(primaryViewPane, 1535, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
			primaryStage.setFullScreenExitHint("");

			btnDiscover.setOnMouseMoved(e -> {
				btnDiscover.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
			});

			btnDiscover.setOnMouseExited(e -> {
				btnDiscover.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 25));
			});

			btnBack.setOnMouseMoved(e -> {
				btnBack.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			});

			btnBack.setOnMouseExited(e -> {
				btnBack.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
			});

			btnDiscover.setOnAction(e -> {
				scene.setRoot(map());
			});

			btnBack.setOnAction(e -> {
				scene.setRoot(primaryViewPane);
			});

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void primaryView(Stage stage) {
		// get image
		Image img = new Image("earth.jpg");
		ImageView imgView = new ImageView(img);

		// adjust image properties
		imgView.fitHeightProperty().bind(primaryViewPane.heightProperty());
		imgView.fitWidthProperty().bind(primaryViewPane.widthProperty().subtract(500));

		Label lblTitle = new Label("Shortest Path Discovery Using Dijkstra's Algorithm");
		lblTitle.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 40));
		lblTitle.setPadding(new Insets(30));
		lblTitle.setTextAlignment(TextAlignment.CENTER);

		lblTitle.setStyle("-fx-text-fill: #FF7777;");

		HBox boxTilte = new HBox();
		boxTilte.getChildren().add(lblTitle);
		boxTilte.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setTop(boxTilte);

		btnDiscover.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 25));
		btnDiscover.setPrefSize(340, 50);
		btnDiscover.setStyle("-fx-background-color: transparent; -fx-text-fill: #FF7777;");

		Button btnLoad = new Button("load data from a file");
		btnLoad.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));

		btnLoad.setStyle("-fx-background-radius: 10;" + /* Adjust the radius to control the roundness of the corners */
				"-fx-min-width: 210px;" + "-fx-min-height: 40px;" + "-fx-max-width: 210px;" + "-fx-max-height: 40px;"
				+ "-fx-background-color: #AAEEDD;" + "-fx-text-fill: white;");

		btnLoad.setOnMouseMoved(e -> {
			btnLoad.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		});

		btnLoad.setOnMouseExited(e -> {
			btnLoad.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		});

		Label lblSelected = new Label("");
		lblSelected.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));

		HBox boxLoad = new HBox(5);
		boxLoad.setAlignment(Pos.CENTER);
		boxLoad.getChildren().addAll(btnLoad, lblSelected);

		VBox boxButtons = new VBox(5);
		boxButtons.getChildren().addAll(boxLoad);
		boxButtons.setPadding(new Insets(0, 0, 40, 0));
		boxButtons.setAlignment(Pos.BASELINE_CENTER);

		FileChooser chooser = new FileChooser();
		btnLoad.setOnAction(e -> {
			file = chooser.showOpenDialog(stage);
			if (file != null) {
				try {
					readFile();
					lblSelected.setText(file.getAbsolutePath());
					btnDiscover.setDisable(false);
					boxButtons.getChildren().add(btnDiscover);
					initializeTable();
					cmboSource = new ComboBox<>(FXCollections.observableArrayList(graph.getVertices()));
					cmboTarget = new ComboBox<>(FXCollections.observableArrayList(graph.getVertices()));
				} catch (Exception n) {
					lblSelected.setText("Loading Process Has Failed!");
					lblSelected.setTextFill(Color.RED);
					btnDiscover.setDisable(true);
					n.printStackTrace();
				}
			}
		});

		pane.setBottom(boxButtons);

		primaryViewPane.getChildren().addAll(imgView, pane);
		primaryViewPane.setAlignment(Pos.CENTER);
		primaryViewPane.setStyle("-fx-background-color: white;");
	}

	public BorderPane map() {

		StackPane imgPane = new StackPane();
		BorderPane rootPane = new BorderPane();

		// get image
		Image img = new Image("earth2.jpg");

		ImageView imgView = new ImageView(img);

		// adjust image properties
		imgView.fitHeightProperty().bind(imgPane.heightProperty());
		imgView.fitWidthProperty().bind(imgPane.widthProperty());
		imgPane.getChildren().add(imgView);
		imgPane.setAlignment(Pos.CENTER);

		// adjust image pane properties

		imgPane.setMinSize(950, 600);
		imgPane.setPrefSize(950, 600);
		imgPane.setMaxSize(950, 600);

		Platform.runLater(() -> {
			put_points_on_map(imgPane);
		});

		VBox box = new VBox(30);
		box.setAlignment(Pos.CENTER_LEFT);
		box.setPadding(new Insets(0, 20, 0, 0));

		GridPane gridPane = new GridPane();
		gridPane.setHgap(30);
		gridPane.setVgap(15);

		Label lblSource = new Label("Source: ");
		Label lblTarget = new Label("Target: ");

		lblSource.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		lblTarget.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

		gridPane.add(lblSource, 0, 0);
		gridPane.add(lblTarget, 0, 1);

		gridPane.add(cmboSource, 1, 0);
		gridPane.add(cmboTarget, 1, 1);

		cmboSource.setStyle(" -fx-background-color: #fff;\r\n" + "    -fx-border-color: #ccc;\r\n"
				+ "    -fx-border-width: 1px;\r\n"
				+ "    -fx-border-radius: 10px; /* Rounded corners for the popup */\r\n"
				+ "    -fx-background-radius: 10px; /* Rounded corners for the popup */" + "-fx-pref-width: 300px;\r\n"
				+ "    -fx-pref-height: 40px;");

		cmboTarget.setStyle(" -fx-background-color: #fff;\r\n" + "    -fx-border-color: #ccc;\r\n"
				+ "    -fx-border-width: 1px;\r\n"
				+ "    -fx-border-radius: 10px; /* Rounded corners for the popup */\r\n"
				+ "    -fx-background-radius: 10px; /* Rounded corners for the popup */" + "-fx-pref-width: 300px;\r\n"
				+ "    -fx-pref-height: 40px;");

		Label lblPath = new Label("Path: ");
		Label lblDistance = new Label("Distance: ");
		lblPath.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		lblDistance.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

		// path textarea
		TextArea txtAreaPath = new TextArea();
		txtAreaPath.setEditable(false);

		GridPane distancePane = new GridPane();
		distancePane.setHgap(10);
		distancePane.setVgap(20);

		TextField txtDistance = new TextField();
		txtDistance.setEditable(false);

		distancePane.add(lblDistance, 0, 0);
		distancePane.add(txtDistance, 1, 0);

		Button btnRun = new Button("Run");
		Button btnClear = new Button("Clear");

		btnRun.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		btnClear.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));

		btnRun.setStyle("-fx-background-radius: 10;" + /* Adjust the radius to control the roundness of the corners */
				"-fx-min-width: 75px;" + "-fx-min-height: 40px;" + "-fx-max-width: 75px;" + "-fx-max-height: 40px;"
				+ "-fx-background-color: #88BBAA;" + "-fx-text-fill: white;");

		btnClear.setStyle("-fx-background-radius: 10;" + /* Adjust the radius to control the roundness of the corners */
				"-fx-min-width: 75px;" + "-fx-min-height: 40px;" + "-fx-max-width: 75px;" + "-fx-max-height: 40px;"
				+ "-fx-background-color: #88BBAA;" + "-fx-text-fill: white;");

		btnRun.setOnMouseMoved(e -> {
			btnRun.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		});

		btnRun.setOnMouseExited(e -> {
			btnRun.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		});

		btnClear.setOnMouseMoved(e -> {
			btnClear.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		});

		btnClear.setOnMouseExited(e -> {
			btnClear.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		});

		btnClear.setOnAction(e -> {
			cmboSource.setDisable(false);
			cmboTarget.setDisable(false);
			cmboSource.setValue(null);
			cmboTarget.setValue(null);
			txtAreaPath.clear();
			txtDistance.clear();
			btnRun.setDisable(false);
			timeClicked = 0;

			Line[] lines = new Line[numberOfLines];
			int i = 0;
			for (var node : imgPane.getChildren()) {
				if (node instanceof Line) {
					lines[i] = (Line) node;
					i++;
				}
			}

			imgPane.getChildren().removeAll(FXCollections.observableArrayList(lines));

			numberOfLines = 0;

		});

		btnRun.setOnAction(e -> {
			Line[] lines = new Line[numberOfLines];
			int i = 0;
			for (var node : imgPane.getChildren()) {
				if (node instanceof Line) {
					lines[i] = (Line) node;
					i++;
				}
			}

			imgPane.getChildren().removeAll(FXCollections.observableArrayList(lines));

			numberOfLines = 0;
			
			if (cmboSource.getValue() != null && cmboTarget.getValue() != null) {
				btnRun.setDisable(true);

				System.out.println(cmboSource.getValue().getNumber());
				reinitializeTable(cmboSource.getValue());

				altered = new DoublyEndedLinkedList<>();
				boolean valid = Dijkstra(cmboSource.getValue(), cmboTarget.getValue());
				if (valid) {
					getPath(cmboTarget.getValue(), txtAreaPath, imgPane);
					txtDistance.setText(table[cmboTarget.getValue().getNumber()].getDistance() + " km");
				} else
					txtAreaPath
							.setText("There is no path from " + cmboSource.getValue() + " to " + cmboTarget.getValue());
			}
		});

		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(btnRun, btnClear);
		hbox.setAlignment(Pos.CENTER);

		box.getChildren().addAll(gridPane, hbox, lblPath, txtAreaPath, distancePane);

		btnBack.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		btnBack.setPrefSize(150, 30);
		btnBack.setStyle("-fx-background-color: transparent;");
		btnBack.setTextFill(Color.BLACK);

		VBox boxButton = new VBox();
		boxButton.getChildren().addAll(btnBack);
		boxButton.setPadding(new Insets(0, 0, 10, 0));
		boxButton.setAlignment(Pos.BASELINE_RIGHT);

		rootPane.setCenter(imgPane);
		rootPane.setRight(box);
		rootPane.setBottom(boxButton);

		rootPane.setStyle("-fx-background-color: white;");

		return rootPane;
	}

	public void readFile() {
		try {
			int numberOfVertices = 0;
			int numberOfEdges = 0;
			Scanner sc = new Scanner(file);
			if (sc.hasNext()) {
				String[] arr = sc.nextLine().split(",");
				numberOfVertices = Integer.parseInt(arr[0].strip());
				numberOfEdges = Integer.parseInt(arr[1].strip());
			}

			graph = new Graph(numberOfVertices);

			int counter = 0;
			while (sc.hasNext() && counter < numberOfVertices) {
				String[] capitalInfo = sc.nextLine().split(",");
				Capital capital = new Capital(capitalInfo[0], Double.parseDouble(capitalInfo[1].strip()),
						Double.parseDouble(capitalInfo[2].strip()));
				graph.setVertex(counter, capital);
				counter++;
			}

			counter = 0;
			while (sc.hasNext() && counter < numberOfEdges) {
				String[] connection = sc.nextLine().split(",");
				Vertex startVertex = graph.findVertex(connection[0].strip());
				Vertex endVertex = graph.findVertex(connection[1].strip());
				if (startVertex != null && endVertex != null) {
					graph.addEdge(startVertex, endVertex);
					startVertex.getEdgeWith(endVertex);
				}
				counter++;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void put_points_on_map(StackPane imgPane) {
		Vertex[] capitalsV = graph.getVertices();
		bounds = imgPane.getBoundsInParent();

		for (int i = 0; i < capitalsV.length; i++) {
			Vertex capitalV = capitalsV[i];
			if (capitalV != null) {
				Capital capital = capitalV.getCapital();

				Image img = new Image("pin2.png");
				ImageView imgView = new ImageView(img);

				// adjust image properties
				imgView.fitHeightProperty().bind(imgPane.heightProperty().divide(35));
				imgView.fitWidthProperty().bind(imgPane.widthProperty().divide(35));

				double x = (((capital.getLongitude() + 180) / 360 * bounds.getWidth()));
				double y = ((capital.getLatitude() - 90) / -180 * bounds.getHeight());
				
				Circle circle = new Circle(x, y, 3);
				circle.setManaged(false);

				imgView.setTranslateX(x - (imgView.getFitWidth() / 2));
				imgView.setTranslateY(y - (imgView.getFitHeight()));
				imgView.setManaged(false);

				Label lblCapital = new Label();
				lblCapital.setText(capital.getCapitalName());
				lblCapital.setVisible(true);

				lblCapital.setTranslateX(x - bounds.getWidth() / 2);
				lblCapital.setTranslateY(y - bounds.getHeight() / 2);
				lblCapital.setTextFill(Color.FIREBRICK);
				lblCapital.setFont(Font.font("Times New Roman", FontWeight.BOLD, 10));

				lblCapital.setOnMouseClicked(e -> {
					timeClicked++;
					if (timeClicked == 1)
						cmboSource.setValue(capitalV);
					else if (timeClicked == 2) {
						cmboTarget.setValue(capitalV);
						timeClicked = 0;
					}
				});

				imgPane.getChildren().add(lblCapital);
			}
		}
	}

	public boolean Dijkstra(Vertex source, Vertex target) {
		heap = new MinHeap<TableEntry>(graph.getNumberOfVertices());

		heap.add(table[source.getNumber()]);
		reinitializeTable(source);
		altered.addAtEnd(source.getNumber());
		while (!heap.isEmpty()) {

			TableEntry entry = heap.removeMin();
			table[entry.getVertex().getNumber()].setKnown(true);

			Vertex currVer = table[entry.getVertex().getNumber()].getVertex();
			if (currVer.compareTo(target) == 0)
				return true;

			Node<Edge> currNode = currVer.getEdges().getHead();
			while (currNode != null) {
				Edge edge = currNode.getData();
				if (edge != null && edge.getEnd() != null && !table[edge.getEnd().getNumber()].isKnown()) {

					int adjacentNumber = edge.getEnd().getNumber();
					double newValue = table[currVer.getNumber()].getDistance() + edge.getWeight();
					double oldValue = table[adjacentNumber].getDistance();

					if (table[adjacentNumber].getPath() == null) { // adjacent is not in the heap
						heap.add(table[adjacentNumber]);
						table[adjacentNumber].setDistance(newValue);
						table[adjacentNumber].setPath(currVer);
						altered.addAtEnd(adjacentNumber);
					} else { // adjacent is already in the heap
						if (newValue < oldValue) {
							table[adjacentNumber].setDistance(newValue);
							table[adjacentNumber].setPath(currVer);
						}
					}

				}
				currNode = currNode.getNext();
			}
		}
		return false;
	}

	public void getPath(Vertex vertex, TextArea txtPath, StackPane imgPane) {
		if (vertex == null)
			return;

		if (table[vertex.getNumber()].getPath() != null) {

			getPath(table[vertex.getNumber()].getPath(), txtPath, imgPane);
			txtPath.setText(txtPath.getText() + " --> ");

			Capital capital1 = vertex.getCapital();
			Capital capital2 = table[vertex.getNumber()].getPath().getCapital();

			double x1 = (((capital1.getLongitude() + 180) / 360 * bounds.getWidth()));
			double y1 = ((capital1.getLatitude() - 90) / -180 * bounds.getHeight());

			double x2 = (((capital2.getLongitude() + 180) / 360 * bounds.getWidth()));
			double y2 = ((capital2.getLatitude() - 90) / -180 * bounds.getHeight());

			Line line = new Line();
			line.setStartX(x2);
			line.setEndX(x1);
			line.setStartY(y2);
			line.setEndY(y1);
			line.setManaged(false);
			line.setStroke(Color.rgb(0, 0, 0));
			line.setStrokeWidth(2);
			imgPane.getChildren().add(line);
			numberOfLines++;
		}

		txtPath.setText(txtPath.getText() + vertex.toString());
	}

	private void initializeTable() {
		table = new TableEntry[graph.getNumberOfVertices()];
		Vertex[] capitalsV = graph.getVertices();
		for (int i = 0; i < graph.getNumberOfVertices(); i++) {
			TableEntry tableEntry = new TableEntry(capitalsV[i], Double.MAX_VALUE, false, null);
			table[i] = tableEntry;
		}
	}

	public void reinitializeTable(Vertex source) {
		Node<Integer> currNode = altered.getHead();
		while (currNode != null) {
			int index = currNode.getData();
			table[index].setDistance(Double.MAX_VALUE);
			table[index].setKnown(false);
			table[index].setPath(null);
			currNode = currNode.getNext();
		}

		table[source.getNumber()].setDistance(0.0);
	}
}
