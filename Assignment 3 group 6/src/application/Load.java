package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

/**
 * This class is responsible for loading entities such as artworks and profiles.
 * 
 * @author Christopher Lee
 * @version 1.0
 */

public class Load {
	// TODO load artworks, load profiles,

	/**
	 * Method to read the file of artworks and returns a list of artworks. The
	 * method should exit the program if an error has occurred.
	 * 
	 * @return The list of artworks in the file.
	 */
	public static ArrayList<Artwork> loadArtworks() {
		Scanner in = null;

		File file = new File("artworks.txt");
		try {

			in = new Scanner(file);
		} catch (Exception e) {
			System.out.println("Unable to open artwork.txt");
			System.exit(1);
		}

		return readArtworks(in);
	}

	/**
	 * Method to interpret information text file create a list of artworks.
	 * 
	 * @param in
	 *            The scanner to read the lines of text.
	 * @return A list of artworks on text file.
	 */
	private static ArrayList<Artwork> readArtworks(Scanner in) {
		ArrayList<Artwork> artworks = new ArrayList<>();
		String artworkType = "";
		String line = "";
		in.useDelimiter(",");

		while (in.hasNextLine()) {
			try {
				artworkType = in.next();
			} catch (Exception e) {
				artworkType = "empty";
			}

			switch (artworkType) {
			case "Painting":
				line = in.nextLine();
				artworks.add(createPainting(line));
				break;
			case "Sculpture":
				line = in.nextLine();
				Sculpture s = createSculpture(line);
				artworks.add(s);
				readAdditionalPhotos(s);
				break;
			default:
				break;
			}
		}
		return artworks;
	}

	/**
	 * Method interprets a line of data and creates a painting from that data.
	 * 
	 * @param line
	 *            A string of data relating to a painting.
	 * @return A painting specified in the line.
	 * @throws ParseException
	 */
	private static Painting createPainting(String line) {
		Scanner in = new Scanner(line);
		in.useDelimiter(",");

		String uploader = in.next();
		int bids = in.nextInt();
		String photo = in.next();
		String title = in.next();
		String creator = in.next();
		String year = in.next();
		double price = in.nextDouble();
		double height = in.nextDouble();
		double width = in.nextDouble();
		String date = in.next();
		String desc = in.next();

		in.close();

		if (desc.equals("empty")) {
			return new Painting(uploader, title, photo, creator, year, price, bids, date, height, width);
		} else {
			return new Painting(uploader, title, photo, creator, year, price, bids, date, height, width, desc);
		}
	}

	/**
	 * Method interprets a line of data and adds any additional photos to an
	 * ArrayList.
	 * 
	 * @param sculpture
	 *            An uploaded Sculpture Artwork.
	 * @return No return value.
	 * 
	 */
	public static void readAdditionalPhotos(Sculpture sculpture) {
		String title = sculpture.getTitle();
		Image additionalImage = null;
		Scanner in = null;

		File file = new File("src/Artwork Photos/Additional Photos/" + title + "Counter.txt");
		try {

			in = new Scanner(file);
		} catch (Exception e) {
			System.out.println("Unable to open " + title + "Counter.txt");
			System.exit(1);
		}
		int photoCounter = in.nextInt();
		System.out.println(photoCounter);
		in.close();
		int counter = 1;
		if (photoCounter != 0) {
			while (counter <= photoCounter) {
				String path = "src/Artwork Photos/Additional Photos/" + title + "-" + counter + ".jpg";
				File photoFile = new File(path);
				try {

					in = new Scanner(photoFile);
					BufferedImage bufferedAdditionalImage = ImageIO.read(photoFile);
					additionalImage = SwingFXUtils.toFXImage(bufferedAdditionalImage, null);
				} catch (Exception e) {
					System.out.println("Unable to open " + sculpture.getTitle() + "-" + counter + ".jpg");
					System.exit(1);
				}
				counter++;
				sculpture.addMorePhotos(additionalImage);
			}
			in.close();
		}

	}

	private static Sculpture createSculpture(String line) {
		Scanner in = new Scanner(line);
		in.useDelimiter(",");

		String uploader = in.next();
		int bids = in.nextInt();
		String photo = in.next();
		String title = in.next();
		String creator = in.next();
		String year = in.next();
		double price = in.nextDouble();
		double height = in.nextDouble();
		double width = in.nextDouble();
		double depth = in.nextDouble();
		String material = in.next();
		String date = in.next();
		String desc = in.next();

		in.close();

		if (desc.equals("empty")) {
			return new Sculpture(uploader, title, photo, creator, year, price, bids, date, height, width, depth,
					material);
		} else {
			return new Sculpture(uploader, title, photo, creator, year, price, bids, date, height, width, depth, desc);
		}
	}

	/**
	 * Opens file explorer so a user can choose an image to upload.
	 * 
	 * @return the file selected by the user.
	 * 
	 */
	public static File fileSelecter() {

		// loads images from windows explorer so you can add additional photos
		// for sculptures
		// Window Opener
		FileChooser additionalImageFinder = new FileChooser();

		// Filters for extensions (PNGs and JPGs)
		FileChooser.ExtensionFilter filterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
		FileChooser.ExtensionFilter filterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		additionalImageFinder.getExtensionFilters().addAll(filterJPG, filterPNG);

		// reads selected files
		File file = additionalImageFinder.showOpenDialog(null);
		return file;
	}

}
