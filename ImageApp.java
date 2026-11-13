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

    Picture recoloredImg = recolorImage(128,0,128,pictureFile); 
    // Image #2 Using the original image and pixels, create a photographic negative of the image DAVID
    
    Picture negImage = negativeImage(pictureFile);
    

    // Image #3 Using the original image and pixels, create a grayscale version of the image DAVID
    
    Picture greyImage = greyScaleImage(pictureFile);
    


    // Image #4 Using the original image and pixels, rotate it 180 degrees DAVID
    Picture udImage = upsideDownImage(pictureFile);


    // Image #5 Using the original image and pixels, rotate image 90 SHIVAM
    Picture rotateImg = new Picture(pictureFile);
    Pixel[][] rotatePixels = rotateImg.getPixels2D();

    /* to be implemented */



    // Image #6 Using the original image and pixels, rotate image -90 SHIVAM
    

    
    Picture rotateImg2 = new Picture(pictureFile);
    Pixel[][] rotatePixels2 = rotateImg2.getPixels2D();

    /* to be implemented */


    // Final Image: Add a small image to a larger one 

    

    // Align = topLeft
    
    //Test removing Background
    Picture rBG = removeWhiteBackground("lib2/bird.png");
    // rBG.explore();

    Picture sOL = smallOverLarge(20,20,pictureFile, "lib2/bird.png");


    // recoloredImg.explore();
    // negImage.explore();
    // greyImage.explore();
    // udImage.explore();
    sOL.explore();

    // // for testing  2D algorithms
    // int[][] test1 = { { 1, 2, 3, 4 },
    //     { 5, 6, 7, 8 },
    //     { 9, 10, 11, 12 },
    //     { 13, 14, 15, 16 } };
    // int[][] test2 = new int[4][4];

  }

  //Image 1 DAVID

  public static Picture recolorImage(int r, int g, int b,String pictureFile) {
    Picture origImg = new Picture(pictureFile);
    Pixel[][] origPixels = origImg.getPixels2D();
    System.out.println(origPixels[0][0].getColor());
    // origImg.explore();

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

  //Image 2 DAVID

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

  // public static Picture greyScaleImage(String pictureFile) {

  // }

  // //Image 6 SHIVAM
  
  // public static Picture greyScaleImage(String pictureFile) {

  // }

  // // Small Over Large DAVID
  
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

}
