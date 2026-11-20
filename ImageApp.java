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
    //CodeCademy Assignment
    Picture origImg = new Picture(pictureFile);
    Pixel[][] origPixels = origImg.getPixels2D();
    // Image #1 Using the original image and pixels, recolor an image by changing the RGB color of each Pixel DAVID
    Picture recoloredImg = recolorImage(128,0,128,pictureFile); 
    // Image #2 Using the original image and pixels, create a photographic negative of the image DAVID
    Picture negImage = negativeImage(pictureFile);
    // Image #3 Using the original image and pixels, create a grayscale version of the image DAVID
    Picture greyImage = greyScaleImage(pictureFile);
    // Image #4 Using the original image and pixels, rotate it 180 degrees DAVID
    Picture udImage = upsideDownImage(pictureFile);
   // Image #5 Using the original image and pixels, rotate image 90 SHIVAM
    Picture rotated90Img = rotate90Image(pictureFile);
    // Image #6 Using the original image and pixels, rotate image -90 SHIVAM
    Picture rotatedNeg90Img = rotateNeg90Image(pictureFile);
    // Final Image: Add a small image to a larger one 
    Picture sOL = smallOverLarge(20,20,pictureFile, "lib2/dog.png");

    //Matrix rotations
    
  
   

    // PictureExplorer explorer = new PictureExplorer(rotateNDeg);

    // UNCOMENT TO EXPLORE ALL IMAGES FROM PLTW AND MATRIX MULTI
    // rotateNDeg.explore();
    // origImg.explore();
    // recoloredImg.explore();
    // negImage.explore();
    // greyImage.explore();
    // udImage.explore();
    // sOL.explore();
    // rotated90Img.explore();
    // rotatedNeg90Img.explore();

    //*************** Blackbox testing ***************\\
    // recolored image
    // exploreRgbRange(pictureFile, -255, 300, 50);

    // String[] imageLinks = {
    // "lib/arch.jpg","lib/beach.jpg","lib/bicycle.jpg","lib/bridge.jpg","lib/city-street.jpg","lib/dock-at-lake.jpg","lib/door.jpg",
    // "lib/field.jpg","lib/forest-path.jpg","lib/forest.jpg","lib/gorge.jpg","lib/koala.jpg","lib/leftArrow.gif","lib/moon-surface.jpg","lib/motorcycle.jpg",
    // "lib/picnic.jpg","lib/rainbow.jpg","lib/rightArrow.gif","lib/ruins.jpg","lib/sports-car.jpg","lib/taj-mahal.jpg","lib/temple.jpg"};

    // for (String s: imageLinks) {
    //   // Image #2 Using the original image and pixels, create a photographic negative of the image DAVID
    //   Picture I2 = negativeImage(s);
    //   // Image #3 Using the original image and pixels, create a grayscale version of the image DAVID
    //   Picture I3 = greyScaleImage(s);
    //   // Image #4 Using the original image and pixels, rotate it 180 degrees DAVID
    //   Picture I4 = upsideDownImage(s);
    // // Image #5 Using the original image and pixels, rotate image 90 SHIVAM
    //   Picture I5 = rotate90Image(s);
    //   // Image #6 Using the original image and pixels, rotate image -90 SHIVAM
    //   Picture I6 = rotateNeg90Image(s);

    //   // I2.explore();
    //   // I3.explore();
    //   // I4.explore();
    //   // I5.explore();
    //   I6.explore();
    }
      // for (int i = 0; i< 400; i+= 25) {
    //    Picture rotateNDeg = rotateImageNDegrees(pictureFile, i);
    //    rotateNDeg.explore();
    // }

    //*************** Blackbox testing end ***************\\

  // }

  //Image 1 DAVID
/**
 * This Changes every pixel in an image to a certain color
 */
  public static Picture recolorImage(int r, int g, int b,String pictureFile) {
    if (r != 0) {
      r = Math.abs(255%r);
    }
    if (g != 0) {
       g = Math.abs(255%g);
    }
    if (b != 0) {
      b = Math.abs(255%b);
    }
    Picture recoloredImg = new Picture(pictureFile);
    Pixel[][] recoloredPixels = recoloredImg.getPixels2D();
    int i = 0;
    int j = 0;
    for (Pixel[] pc: recoloredPixels) {
      for (Pixel p: pc){
        p.setColor(new Color(r, g, b));
        recoloredPixels[i][j] = p;
        j++;
      }
      j = 0;
      i++;
    }

    return recoloredImg;
  }
  // black box testing
  public static void exploreRgbRange(String pictureFile, int minVal, int maxVal, int step) {
    for (int v = minVal; v <= maxVal; v += step) {
        recolorImage(v, v, v, pictureFile).explore();
    }
}
// exploreRgbRange(pictureFile, -100, 102410, 5125);

  //Image 2 DAVID

/**
 * Inverts all pixel color values in an image by doing 255-rgb value
 * @param pictureFile the picture file
 */
  public static Picture negativeImage(String pictureFile) {
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
    return negImg;
  }

  //Image 3 DAVID

  /**
 * Averages all pixel color values rgb, and sets each to the value, for each pixel
 * @param pictureFile the picture file
 */

  public static Picture greyScaleImage(String pictureFile) {
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
    return grayscaleImg;
  }

  // //Image 4 DAVID


  /**
 * Creates a flipped image by setting each pixel row to be
 * @param pictureFile the picture file
 */
  public static Picture upsideDownImage(String pictureFile) {
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
    return upsidedownImage;
  }
  
  // //Image 5 SHIVAM

  /**
 * Rotates the image 90 degrees; mapping each column of pixels to the rows place
 * @param pictureFile the picture file
 */
  public static Picture rotate90Image(String pictureFile) {
    Picture rotateImg = new Picture(pictureFile);
    Pixel[][] rotatePixels = rotateImg.getPixels2D();

    int rows = rotatePixels.length;
    int cols = rotatePixels[0].length;

    Picture rotated90Img = new Picture(cols, rows);
    Pixel[][] rotated90Pixels = rotated90Img.getPixels2D();

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        rotated90Pixels[cols - 1 - c][r].setColor(rotatePixels[r][c].getColor());
      }
    }

    return rotated90Img;
  }

  // Image #6 SHIVAM

  /**
 * Rotates the image -90 degrees; mapping each column of pixels to the rows place
 * @param pictureFile the picture file
 */

  public static Picture rotateNeg90Image(String pictureFile) {
    Picture rotateImg2 = new Picture(pictureFile);
    Pixel[][] rotatePixels2 = rotateImg2.getPixels2D();

    int rows2 = rotatePixels2.length;
    int cols2 = rotatePixels2[0].length;

    Picture rotatedNeg90Img = new Picture(cols2, rows2);
    Pixel[][] rotatedNeg90Pixels = rotatedNeg90Img.getPixels2D();

    for (int r = 0; r < rows2; r++) {
      for (int c = 0; c < cols2; c++) {
        rotatedNeg90Pixels[c][rows2 - 1 - r].setColor(rotatePixels2[r][c].getColor());
      }
    }

    return rotatedNeg90Img;
  }

  // // Small Over Large DAVID
  
  /**
 * Removes background
 * @param pictureFile the picture file
 */

  public static Picture removeWhiteBackground(String pictureFile) {
    Picture rBG = new Picture(pictureFile);
    Pixel[][] rBGPixels = rBG.getPixels2D();

    for (int i1 = 0; i1 < rBGPixels.length; i1++) {
      for (int j1 = 0; j1< rBGPixels[i1].length; j1++) {
        Color cC = rBGPixels[i1][j1].getColor();
        if (cC.getRed() == 255 && cC.getGreen() == 255 && cC.getBlue() == 255){
          rBGPixels[i1][j1].setAlpha(0);;
        }

      }}
    
    return rBG;
  }

  
  /**
   * Puts a small image over a large one, by mapping its pixels over the 
   * large image
   * @param smallPictureFile front pic file
   * @param offsetR row you want to start generating the image at
   * @param offsetC column you want to start generating the image at
   * @param largePictureFile background pic file
 */

  public static Picture smallOverLarge(int offsetR, int offsetC,String largePictureFile, String smallPictureFile) {
    
    Picture largeImage = new Picture(largePictureFile);
    Pixel[][] lIPixels = largeImage.getPixels2D();
    Picture SmallImage = removeWhiteBackground(smallPictureFile);
    Pixel[][] sIPixels = SmallImage.getPixels2D();
    int targetRow = offsetR;
    int targetCol = offsetC;

    int smallImageHeight = sIPixels.length;
    int smallImageWidth = sIPixels[0].length;

    for (int i1 = 0; i1 < smallImageHeight; i1++) {
      for (int j1 = 0; j1< smallImageWidth; j1++) {
        
        Pixel smallPixel = sIPixels[i1][j1];
        
        // Only copy the pixel if it is NOT white (skipping the background)
        if (!(smallPixel.getRed() == 255 && 
              smallPixel.getGreen() == 255 && 
              smallPixel.getBlue() == 255)) {
              
          lIPixels[i1 + targetRow][j1 + targetCol].setColor(smallPixel.getColor());
        }
      }
    }
  return largeImage;
  }


    /**
    * Rotates using matrix multilication
    * @param pictureFile the picture file
    * @param degrees amount you want to rotate by
 */


  public static Picture rotateImageNDegrees(String pictureFile, double degrees) {
    degrees = Math.abs(degrees%360);
    Picture input = new Picture(pictureFile);
    Pixel[][] inputArray = input.getPixels2D();
    int origHeight = inputArray.length;
    int origWidth = inputArray[0].length;

    // rotate all 4 corners
    Matrix2by2 rotateMatrix = new Matrix2by2(-degrees);
    double halfW = origWidth / 2.0;
    double halfH = origHeight / 2.0;

    Vector1by2[] corners = { //Corner coordiantes; center of shape is (0,0)
        new Vector1by2(-halfW, -halfH),
        new Vector1by2(halfW, -halfH),
        new Vector1by2(-halfW, halfH),
        new Vector1by2(halfW, halfH)
    };

    double maxX = 1000000, minX = -1000000;
    double minY = 100000, maxY = -1000000;

    for (Vector1by2 corner : corners) {
        Vector1by2 rotated = Matrix2by2.multiply(corner, rotateMatrix);
        maxX = Math.min(maxX, rotated.getA());
        minX = Math.max(minX, rotated.getA());
        minY = Math.min(minY, rotated.getB());
        maxY = Math.max(maxY, rotated.getB());
    }
    System.out.println(minX);
    System.out.println(maxY);
    int newWidth = (int) Math.ceil(minX - maxX);
    int newHeight = (int) Math.ceil(maxY - minY);

    //create picture
    Picture output = new Picture(newHeight, newWidth);
    Pixel[][] outputArray = output.getPixels2D();

    //Map Pixels
    int newCenterX = newWidth / 2;
    int newCenterY = newHeight / 2;

    for (int y = 0; y < newHeight; y++) {
        for (int x = 0; x < newWidth; x++) {
            // Shift relative to the new image's center
            int shiftedX = x - newCenterX;
            int shiftedY = y - newCenterY;

            // Rotate backwards to find where this pixel came from
            Vector1by2 currentVector = new Vector1by2(shiftedX, shiftedY);
            Vector1by2 newVector = Matrix2by2.multiply(currentVector, rotateMatrix);

            // Shift back using the ORIGINAL image's center
            int origX = (int) Math.round(newVector.getA()) + (int)halfW;
            int origY = (int) Math.round(newVector.getB()) + (int)halfH;

            //  Prevent out of bounds error for original array
            if (origY >= 0 && origY < origHeight && origX >= 0 && origX < origWidth) {
                outputArray[y][x].setColor(inputArray[origY][origX].getColor());
            }
        }
    }

    return output;
}


}
