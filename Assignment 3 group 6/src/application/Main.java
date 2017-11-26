package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import com.sun.prism.paint.Color;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * This class initialises the Art-A-Tawe system.
 * 
 * @author
 * @version 1.0
 */
public class Main extends Application {

	final int PANE_SIZE_WIDTH = 1000;
	final int PANE_SIZE_HEIGHT = 1000;
	final int DISPLAY_IMAGE_WIDTH = 250;
	final int DISPLAY_IMAGE_HEIGHT = 250;
	final int VBOX_SPACING = 5;
	final int PADDING = 5;

	private Scene scene;
	private Stage stage;
	private Scene homeScene;
	private Image image;
	private Image additionalImage;

	private TextField uploaderBox = new TextField();
	private TextField titleBox = new TextField();
	private TextField creatorNameBox = new TextField();
	private TextField yearBox = new TextField();
	private TextField reservePriceBox = new TextField();
	private TextField bidsAllowedBox = new TextField();
	private TextField heightBox = new TextField();
	private TextField widthBox = new TextField();
	private TextField depthBox = new TextField();
	private TextField materialBox = new TextField();
	private TextField descriptionBox = new TextField();
	private ImageView imageViewer = new ImageView();
	private Button homeNavButton = new Button("Home");
	private Label textFieldErrorLabel = new Label("");
	private Label imageErrorLabel = new Label("");
	private Label testLabel = new Label("test label");
	private Label testLabel2 = new Label("test label2");

	private Insets paddingInset = new Insets(PADDING, PADDING, PADDING, PADDING);

	public static String photoLocation;
	public static String additionalPhotosLocation;
	private String title;
	private int additionalPhotoCounter;

	private BufferedImage bufferedImage;
	private BufferedImage bufferedAdditionalImage;

	@Override
	public void start(Stage primaryStage) {
		try {
			this.stage = primaryStage;
			BorderPane root = new BorderPane();
			VBox left = new VBox();
			VBox right = new VBox();
			left.setSpacing(VBOX_SPACING);
			right.setSpacing(VBOX_SPACING);
			left.setPadding(paddingInset);
			right.setPadding(paddingInset);
			Label homePageLabel = new Label("Home Page (Place Holder)");
			root.setLeft(left);
			root.setRight(right);
			root.setCenter(homePageLabel);
			Button browseNavButton = new Button("Browse");
			Button sellNavButton = new Button("Sell");

			sellNavButton.setOnAction(event -> {
				stage.setScene(navigateSell());
			});

			browseNavButton.setOnAction(event -> {
				stage.setScene(navigateBrowse());
			});
			
			browseNavButton.setMaxWidth(Double.MAX_VALUE);
			sellNavButton.setMaxWidth(Double.MAX_VALUE);

			left.getChildren().addAll(browseNavButton);
			right.getChildren().addAll(sellNavButton);

			scene = new Scene(root, PANE_SIZE_WIDTH, PANE_SIZE_HEIGHT);
			homeScene = scene;

			stage.setTitle("Home");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Scene navigateSell() {

		BorderPane sellPage = new BorderPane();
		Scene scene = new Scene(sellPage, PANE_SIZE_WIDTH, PANE_SIZE_HEIGHT);
		Label sellPageLabel = new Label("Sell Page (Place Holder)");
		sellPage.setCenter(sellPageLabel);
		VBox left = new VBox();
		VBox right = new VBox();
		VBox bottom = new VBox();
		left.setSpacing(VBOX_SPACING);
		right.setSpacing(VBOX_SPACING);
		left.setPadding(paddingInset);
		right.setPadding(paddingInset);
		bottom.setPadding(paddingInset);

		Button paintingNavButton = new Button("Sell Painting");
		Button sculptureNavButton = new Button("Sell Sculpture");

		paintingNavButton.setMaxWidth(Double.MAX_VALUE);
		sculptureNavButton.setMaxWidth(Double.MAX_VALUE);

		left.getChildren().addAll(paintingNavButton);
		right.getChildren().addAll(sculptureNavButton);
		bottom.getChildren().addAll(homeNavButton);

		paintingNavButton.setOnAction(event -> {
			stage.setScene(navigatePainting());

		});

		sculptureNavButton.setOnAction(event -> {
			stage.setScene(navigateSculpture());
		});

		homeNavButton.setOnAction(event -> {
			goHome();
		});

		stage.setTitle("Sell");
		sellPage.setLeft(left);
		sellPage.setRight(right);
		sellPage.setBottom(bottom);
		return scene;
	}

	public Scene navigatePainting() {
		BorderPane paintingPage = new BorderPane();
		Scene scene = new Scene(paintingPage, PANE_SIZE_WIDTH, PANE_SIZE_HEIGHT);
		paintingPage.setCenter(imageViewer);
		VBox left = new VBox();
		VBox right = new VBox();
		VBox bottom = new VBox();
		left.setSpacing(VBOX_SPACING);
		right.setSpacing(VBOX_SPACING);
		left.setPadding(paddingInset);
		right.setPadding(paddingInset);
		bottom.setPadding(paddingInset);

		Label uploaderLabel = new Label("Uploader:");
		Label titleLabel = new Label("Title:");
		Label creatorNameLabel = new Label("Creator Name:");
		Label yearLabel = new Label("Year Created:");
		Label reservePriceLabel = new Label("Reserve Price:");
		Label bidsAllowedLabel = new Label("Number of Bids Allowed:");
		Label heightLabel = new Label("Height:");
		Label widthLabel = new Label("Width:");
		Label descriptionLabel = new Label("Description (Optional):");

		Button createPaintingButton = new Button("Create Painting");
		Button loadPaintingButton = new Button("Load Painting Image");
		createPaintingButton.setMaxWidth(Double.MAX_VALUE);

		Label paintingLabel = new Label("Painting");
		left.getChildren().addAll(createPaintingButton, paintingLabel, uploaderLabel, uploaderBox, titleLabel, titleBox,
				creatorNameLabel, creatorNameBox, yearLabel, yearBox, reservePriceLabel, reservePriceBox,
				bidsAllowedLabel, bidsAllowedBox, heightLabel, heightBox, widthLabel, widthBox, descriptionLabel,
				descriptionBox, textFieldErrorLabel);
		right.getChildren().addAll(loadPaintingButton, imageErrorLabel, testLabel, testLabel2);
		bottom.getChildren().addAll(homeNavButton);

		createPaintingButton.setOnAction(event -> {
			createPainting();

		});

		loadPaintingButton.setOnAction(event -> {
			imageLoader();

		});

		homeNavButton.setOnAction(event -> {
			goHome();
		});

		paintingPage.setLeft(left);
		paintingPage.setRight(right);
		paintingPage.setBottom(bottom);
		stage.setTitle("Painting");
		return scene;

	}

	public Scene navigateSculpture() {

		BorderPane sculpturePage = new BorderPane();
		Scene scene = new Scene(sculpturePage, PANE_SIZE_WIDTH, PANE_SIZE_HEIGHT);
		sculpturePage.setCenter(imageViewer);
		VBox left = new VBox();
		VBox right = new VBox();
		VBox bottom = new VBox();

		Label uploaderLabel = new Label("Uploader:");
		Label titleLabel = new Label("Title:");
		Label creatorNameLabel = new Label("Creator Name:");
		Label yearLabel = new Label("Year Created:");
		Label reservePriceLabel = new Label("Reserve Price:");
		Label bidsAllowedLabel = new Label("Number of Bids Allowed:");
		Label heightLabel = new Label("Height:");
		Label widthLabel = new Label("Width:");
		Label depthLabel = new Label("Depth:");
		Label materialLabel = new Label("Material:");
		Label descriptionLabel = new Label("Description (Optional):");

		left.setSpacing(VBOX_SPACING);
		right.setSpacing(VBOX_SPACING);
		left.setPadding(paddingInset);
		right.setPadding(paddingInset);
		bottom.setPadding(paddingInset);
		Button createSculptureButton = new Button("Create Sculpture");
		Button loadSculptureButton = new Button("Load Sculpture Image");
		Button addAdditionalPhotosButton = new Button("Add Additional Photos");
		Label sculptureLabel = new Label("Sculpture");
		createSculptureButton.setMaxWidth(Double.MAX_VALUE);

		left.getChildren().addAll(createSculptureButton, sculptureLabel, uploaderLabel, uploaderBox, titleLabel,
				titleBox, creatorNameLabel, creatorNameBox, yearLabel, yearBox, reservePriceLabel, reservePriceBox,
				bidsAllowedLabel, bidsAllowedBox, heightLabel, heightBox, widthLabel, widthBox, depthLabel, depthBox,
				materialLabel, materialBox, descriptionLabel, descriptionBox, textFieldErrorLabel);
		right.getChildren().addAll(loadSculptureButton, addAdditionalPhotosButton, imageErrorLabel, testLabel,
				testLabel2);
		bottom.getChildren().addAll(homeNavButton);

		createSculptureButton.setOnAction(event -> {
			createSculpture();

		});

		loadSculptureButton.setOnAction(event -> {
			imageLoader();

		});

		addAdditionalPhotosButton.setOnAction(event -> {
			stage.setScene(navigateAddtionalPhotos());

		});
		homeNavButton.setOnAction(event -> {
			goHome();
		});

		sculpturePage.setLeft(left);
		sculpturePage.setRight(right);
		sculpturePage.setBottom(bottom);
		stage.setTitle("Sculpture");
		return scene;
	}

	public Scene navigateAddtionalPhotos() {

		BorderPane additionalPhotosPage = new BorderPane();
		Scene scene = new Scene(additionalPhotosPage, PANE_SIZE_WIDTH, PANE_SIZE_HEIGHT);
		additionalPhotosPage.setCenter(imageViewer);
		VBox left = new VBox();
		VBox right = new VBox();
		VBox bottom = new VBox();
		Button addPhotosButton = new Button("Add Photos");

		left.setSpacing(VBOX_SPACING);
		right.setSpacing(VBOX_SPACING);
		left.setPadding(paddingInset);
		right.setPadding(paddingInset);
		bottom.setPadding(paddingInset);

		additionalPhotosPage.setCenter(imageViewer);

		bottom.setPadding(paddingInset);
		right.getChildren().addAll(addPhotosButton, imageErrorLabel);
		bottom.getChildren().addAll(homeNavButton);

		addPhotosButton.setOnAction(event -> {
			additionalPhotoLoader();
		});

		homeNavButton.setOnAction(event -> {
			goHome();
		});

		additionalPhotosPage.setLeft(left);
		additionalPhotosPage.setRight(right);
		additionalPhotosPage.setBottom(bottom);
		stage.setTitle("Addtional Photos");
		return scene;
	}

	public Scene navigateBrowse() {
		BorderPane browsePage = new BorderPane();
		Scene scene = new Scene(browsePage, PANE_SIZE_WIDTH, PANE_SIZE_HEIGHT);
		VBox bottom = new VBox();
		
		Label placeHolder = new Label("THIS WHOLE PAGE IS JUST A PLACE HOLDER");
		browsePage.setCenter(placeHolder);
		stage.setTitle("Browse");
		bottom.getChildren().addAll(homeNavButton);
		bottom.setPadding(paddingInset);
		browsePage.setBottom(bottom);
		homeNavButton.setOnAction(event -> {
			goHome();
		});
		
		
		
		return scene;
	}
	
	
	public void createPainting() {

		try {
			String uploader = uploaderBox.getText();
			title = titleBox.getText();
			String creatorName = creatorNameBox.getText();
			String year = yearBox.getText();
			double reservePrice = Double.parseDouble(reservePriceBox.getText());
			int bidsAllowed = Integer.parseInt(bidsAllowedBox.getText());
			Date date = new Date();
			double height = Double.parseDouble(heightBox.getText());
			double width = Double.parseDouble(widthBox.getText());

			String imagePath = System.getProperty("user.dir") + "/Artwork Photos" + "/" + title + ".jpg";
			photoLocation = imagePath;
			File file = new File(imagePath);
			if (!file.exists()) {
				Save.saveImage(file, bufferedImage);
				if (descriptionBox.getText().equals(null)) {
					Painting painting = new Painting(uploader, title, photoLocation, creatorName, year, reservePrice,
							bidsAllowed, date, height, width);

					testLabel.setText(painting.toString());
					testLabel2.setText(photoLocation);
					Artwork.artworkList.add(painting);
				} else {
					String description = descriptionBox.getText();
					Painting painting = new Painting(uploader, title, photoLocation, creatorName, year, reservePrice,
							bidsAllowed, date, height, width, description);
					testLabel.setText(painting.toString());
					testLabel2.setText(photoLocation);
					Artwork.artworkList.add(painting);
				}

			} else {
				imageErrorLabel.setText("Artwork already exists");
			}

		} catch (NumberFormatException e) {
			textFieldErrorLabel.setText("Error, Please do not leave a field empty");
		}

	}

	public void createSculpture() {

		try {
			String uploader = uploaderBox.getText();
			title = titleBox.getText();
			String creatorName = creatorNameBox.getText();
			String year = yearBox.getText();
			double reservePrice = Double.parseDouble(reservePriceBox.getText());
			int bidsAllowed = Integer.parseInt(bidsAllowedBox.getText());
			Date date = new Date();
			double height = Double.parseDouble(heightBox.getText());
			double width = Double.parseDouble(widthBox.getText());
			double depth = Double.parseDouble(depthBox.getText());
			String material = materialBox.getText();

			String imagePath = System.getProperty("user.dir") + "/Artwork Photos" + "/" + title + ".jpg";
			photoLocation = imagePath;
			File file = new File(imagePath);
			if (!file.exists()) {
				Save.saveImage(file, bufferedImage);
				if (descriptionBox.getText().equals(null)) {
					Sculpture sculpture = new Sculpture(uploader, title, photoLocation, creatorName, year, reservePrice,
							bidsAllowed, date, height, width, depth, material);
					testLabel.setText(sculpture.toString());
					testLabel2.setText(photoLocation);
					Artwork.artworkList.add(sculpture);
				} else {
					String description = descriptionBox.getText();
					Sculpture sculpture = new Sculpture(uploader, title, photoLocation, creatorName, year, reservePrice,
							bidsAllowed, date, height, width, depth, material, description);
					testLabel.setText(sculpture.toString());
					testLabel2.setText(photoLocation);
					Artwork.artworkList.add(sculpture);
				}
			} else {
				imageErrorLabel.setText("Artwork already exists");
			}
		} catch (NumberFormatException e) {
			textFieldErrorLabel.setText("Error, Please do not leave a field empty");
		}

	}

	public void imageLoader() {
		// Window Opener
		FileChooser imageFinder = new FileChooser();

		// Filters for extensions (PNGs and JPGs)
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		imageFinder.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

		// reads selected files
		File file = imageFinder.showOpenDialog(null);

		try {
			bufferedImage = ImageIO.read(file);
			image = SwingFXUtils.toFXImage(bufferedImage, null);
			imageViewer.setImage(image);
			imageViewer.setFitWidth(DISPLAY_IMAGE_WIDTH);
			imageViewer.setFitHeight(DISPLAY_IMAGE_HEIGHT);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to load " + file, e);
		} catch (IllegalArgumentException e) {
			imageErrorLabel.setText("Error, No image selected");
		}

	}

	public void additionalPhotoLoader() {
		// Window Opener
		FileChooser additionalImageFinder = new FileChooser();

		// Filters for extensions (PNGs and JPGs)
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		additionalImageFinder.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

		// reads selected files
		File file = additionalImageFinder.showOpenDialog(null);

		try {
			bufferedAdditionalImage = ImageIO.read(file);
			additionalImage = SwingFXUtils.toFXImage(bufferedAdditionalImage, null);
			imageViewer.setImage(additionalImage);
			imageViewer.setFitWidth(DISPLAY_IMAGE_WIDTH);
			imageViewer.setFitHeight(DISPLAY_IMAGE_HEIGHT);
			additionalPhotoCounter++;
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to load " + file, e);
		} catch (IllegalArgumentException e) {
			imageErrorLabel.setText("Error, No image selected");
		}

		String imagePath = System.getProperty("user.dir") + "/Artwork Photos" + "/" + "Additional Photos" + "/" + title
				+ additionalPhotoCounter + ".jpg";
		additionalPhotosLocation = imagePath;
		File photoFile = new File(imagePath);
		if (!photoFile.exists()) {
			Save.saveAdditionalImage(photoFile, bufferedAdditionalImage);

		} else {
			imageErrorLabel.setText("Additional Image already exists");
			additionalPhotoCounter--;
		}

	}

	// resets all variables and labels that have changed
	public void goHome() {
		stage.setScene(homeScene);
		stage.setTitle("Home");
		textFieldErrorLabel.setText("");
		imageErrorLabel.setText("");
		imageViewer.setImage(null);
		additionalPhotoCounter = 0;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
