// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Color[][] imageOut;
		// imageOut = flippedHorizontally(tinypic);
		// System.out.println();
		// print(imageOut);

		// Color[][] imageVert;
		// imageVert = flippedVertically(tinypic);
		// System.out.println();
		// print(imageVert);

		// Color[][] grayImage;
		// grayImage = grayScaled(tinypic);
		// System.out.println();
		// print(grayImage);

		// Color[][] scalImage;
		// int width = Integer.parseInt(args[0]);
		// int height = Integer.parseInt(args[1]);
		// scalImage = scaled(tinypic, width, height);
		// print(scalImage);
		// Color tat = new Color (10 ,10 ,10);
		// Color[][] checkBlend = new Color[tinypic.length][tinypic[0].length];
		// for (int i = 0; i < tinypic.length; i++) {
		// 	for (int j = 0; j < tinypic[0].length; j++) {

		// 		checkBlend[i][j] = blend(tat, tinypic[i][j], 0.25);

		// 	}
		// }
		// print(checkBlend);
		

		
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
	
		Color[][] image = new Color[numRows][numCols];
		int r, g ,b;

		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image[0].length; j++) {
				r = in.readInt();
				g = in.readInt();
				b = in.readInt();
				image[i][j] = new Color(r ,g ,b);
			}
		}

		return image;
	}

    // Prints the RGB values of a given color.
	public static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	
	public static void print(Color[][] image) {
            //System.out.println(image.length + " " +image[0].length);
            for (int i = 0; i < image.length; i++) {
                for (int j = 0; j < image[0].length; j++) {
                    print(image[i][j]);
				}
                System.out.println();
            }

		}
	
	
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] newImage = new Color[image.length][image[0].length];
		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image[0].length; j++) {
				newImage[i][j] = image[i][image[0].length - (j + 1)];
			}
		}
		return newImage;
	}
	
	
	public static Color[][] flippedVertically(Color[][] image){
		Color[][] newImage = new Color[image.length][image[0].length];
		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image[0].length; j++) {
				newImage[i][j] = image[image.length - (i + 1)][j];
			}
		}
		return newImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		int lum = (int)((0.299 * pixel.getRed()) + (0.587 * pixel.getGreen()) + (0.114 * pixel.getBlue()));
		Color lumPixel = new Color (lum,lum,lum);
		return lumPixel;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] newImage = new Color[image.length][image[0].length];
		for(int i = 0; i < image.length; i++) {
			for(int j = 0; j < image[0].length; j++) {
				newImage[i][j] = luminance(image[i][j]);
			}
		}	
		return newImage;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] newImage = new Color[height][width];
		double divH = (double)(image.length) / (double)(height);
		double divW = (double)(image[0].length) / (double)(width);
		int pixI, pixJ;
		// System.out.println("DivW: " + divW + " DivH: " + divH);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				pixI = (int)(i * divH);
				pixJ = (int)(j * divW);
				newImage[i][j] = image[pixI][pixJ];
			}
		} 
		return newImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int r = (int)((c1.getRed() * alpha) + (c2.getRed() * (1 - alpha)));
		int g = (int)((c1.getGreen() * alpha) + (c2.getGreen() * (1 - alpha)));
		int b = (int)((c1.getBlue() * alpha) + (c2.getBlue() * (1 - alpha)));

		Color newC = new Color (r,g,b);
		return newC;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		Color[][] newImage = new Color [image1.length][image1[0].length];
		for(int i = 0; i < image1.length; i++) {
			for(int j = 0; j < image1[0].length; j++) {
				newImage[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return newImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		Color[][] newTarget;
		int height = source.length;
		int width = source[0].length;
		newTarget = scaled(target, width, height);
		double alpha;
		for(int i = 0; i < n; i++) {
			alpha = (double)(n - i) / (double)(n);
			display(blend(source, newTarget, alpha));
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

