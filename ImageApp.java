/*
  ImageApp: 
 */
import java.awt.Color;

public class ImageApp
{
  public static void main(String[] args)
  {

    // use any file from the lib folder
    String pictureFile = "lib/beach.jpg";

    // Get an image, get 2d array of pixels, show a color of a pixel, and display the image
    Picture origImg = new Picture(pictureFile);
    Pixel[][] origPixels = origImg.getPixels2D();
    System.out.println(origPixels[0][0].getColor());
    // origImg.explore();

    // Image #1 Using the original image and pixels, recolor an image by changing the RGB color of each Pixel DAVID
    Picture recoloredImg = new Picture(pictureFile);
    Pixel[][] recoloredPixels = recoloredImg.getPixels2D();
    int i = 0;
    int j = 0;
    for (Pixel[] pc: recoloredPixels) {
      for (Pixel p: pc){
        p.setColor(new Color(128, 0, 128));
        recoloredPixels[i][j] = p;
        j++;
      }
      j = 0;
      i++;
    }
    
    /* to be implemented */
 
    // Image #2 Using the original image and pixels, create a photographic negative of the image DAVID
    Picture negImg = new Picture(pictureFile);
    Pixel[][] negPixels = negImg.getPixels2D();
    
    for (int i1 = 0; i1 < negPixels.length; i1++) {
      for (int j1 = 0; j1 < negPixels[i1].length;j1++){ 
        int red = 255 - negPixels[i1][j1].getRed();
        int green = 255 - negPixels[i1][j1].getGreen();
        int blue = 255 - negPixels[i1][j1].getBlue();

        negPixels[i1][j1].setColor(new Color(red, green, blue));
      }

    }
    
    /* to be implemented */

    // Image #3 Using the original image and pixels, create a grayscale version of the image DAVID
    Picture grayscaleImg = new Picture(pictureFile);
    Pixel[][] grayscalePixels = grayscaleImg.getPixels2D();

    for (int i1 = 0; i1 < grayscalePixels.length; i1++) {
      for (int j1 = 0; j1 < grayscalePixels[i1].length;j1++){ 
        int red = grayscalePixels[i1][j1].getRed();
        int green = grayscalePixels[i1][j1].getGreen(); 
        int blue = grayscalePixels[i1][j1].getBlue();
        int average = (red + green + blue)/3;
        grayscalePixels[i1][j1].setColor(new Color(average, average, average));
      }

    }

    

    /* to be implemented */

    // Image #4 Using the original image and pixels, rotate it 180 degrees DAVID
    Picture upsidedownImage = new Picture(pictureFile);
    Pixel[][] originalPixels = upsidedownImage.getPixels2D();
    Picture tempImage = new Picture(pictureFile);
    Pixel[][] temp = tempImage.getPixels2D();
    
    for (int i1 = 0; i1 < originalPixels.length; i1++) {

      for (int j1 = 0; j1< originalPixels[i1].length; j1++) {
        int red = temp[originalPixels.length-i1-1][j1].getRed();
        int green = temp[originalPixels.length-i1-1][j1].getGreen();
        int blue = temp[originalPixels.length-i1-1][j1].getBlue();
        originalPixels[i1][j1].setColor(new Color(red,green,blue));
      }
    }

    upsidedownImage.explore();
    

    /* to be implemented */

    // Image #5 Using the original image and pixels, rotate image 90 SHIVAM
    Picture rotateImg = new Picture(pictureFile);
    Pixel[][] rotatePixels = rotateImg.getPixels2D();

    /* to be implemented */






    // Image #6 Using the original image and pixels, rotate image -90 SHIVAM
    Picture rotateImg2 = new Picture(pictureFile);
    Pixel[][] rotatePixels2 = rotateImg2.getPixels2D();

    /* to be implemented */


    // Final Image: Add a small image to a larger one 

    
    /* to be implemented */

    Picture largeImage = new Picture(pictureFile);
    Pixel[][] lIPixels = largeImage.getPixels2D();
    String overPictureFile = "lib2/bird.png";
    Picture SmallImage = new Picture(overPictureFile);
    Pixel[][] sIPixels = SmallImage.getPixels2D();
    int targetRow = 50;
    int targetCol = 20;

    int smallImageHeight = sIPixels.length;
    int smallImageWidth = sIPixels[0].length;
    // Align = topLeft


    for (int i1 = 0; i1 < smallImageHeight; i1++) {
      for (int j1 = 0; j1< smallImageWidth; j1++) {
        int red = sIPixels[i1][j1].getRed();
        int green = sIPixels[i1][j1].getGreen();
        int blue = sIPixels[i1][j1].getBlue();

        lIPixels[i1+targetRow][j1+targetCol].setColor(new Color(red,green,blue));
      }
    }

    largeImage.explore();
    


    // for testing  2D algorithms
    int[][] test1 = { { 1, 2, 3, 4 },
        { 5, 6, 7, 8 },
        { 9, 10, 11, 12 },
        { 13, 14, 15, 16 } };
    int[][] test2 = new int[4][4];

    // negImg.explore();
    // grayscaleImg.explore();
    // recoloredImg.explore();
  }
}
