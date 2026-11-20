import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import javax.swing.border.*;
/**
 * Displays a picture and lets you explore the picture by displaying the row, column, red,
 * green, and blue values of the pixel at the cursor when you click a mouse button or
 * press and hold a mouse button while moving the cursor.  It also lets you zoom in or
 * out.  You can also type in a row and column value to see the color at that location.
 * 
 * Originally created for the Jython Environment for Students (JES). 
 * Modified to work with DrJava by Barbara Ericson
 * Also modified to show row and columns by Barbara Ericson
 * 
 * @author Keith McDermottt, gte047w@cc.gatech.edu
 * @author Barb Ericson ericson@cc.gatech.edu
 */
public class PictureExplorer implements MouseMotionListener, ActionListener, MouseListener
{
 
  // current indicies
  /** row index */
  private int rowIndex = 0; 
  /** column index */
  private int colIndex = 0;
  
  // main GUI
  /** window to hold GUI */
  private JFrame pictureFrame;
  /** window that allows the user to scroll to see a large picture */
  private JScrollPane scrollPane;
  
  // GUI components
  /** column label */
  private JLabel colLabel;
  /** column previous button */
  private JButton colPrevButton;
  /** row previous button */
  private JButton rowPrevButton;
  /** column next button */
  private JButton colNextButton;
  /** row next button */
  private JButton rowNextButton;
  /** row label */
  private JLabel rowLabel;
  /** text field to show column index */
  private JTextField colValue;
  /** text field to show row index */
  private JTextField rowValue;
  /** red value label */
  private JLabel rValue;
  /** green value label */
  private JLabel gValue;
  /** blue value label */
  private JLabel bValue;
  /** color swatch label */
  private JLabel colorLabel;
  /** panel to show the color swatch */
  private JPanel colorPanel;
  
  // menu components
  /** menu bar */
  private JMenuBar menuBar;
  /** zoom menu */
  private JMenu zoomMenu;
  /** 25% zoom level */
  private JMenuItem twentyFive;
  /** 50% zoom level */
  private JMenuItem fifty;
  /** 75% zoom level */
  private JMenuItem seventyFive;
  /** 100% zoom level */
  private JMenuItem hundred;
  /** 150% zoom level */
  private JMenuItem hundredFifty;
  /** 200% zoom level */
  private JMenuItem twoHundred;
  /** 500% zoom level */
  private JMenuItem fiveHundred;
  
  /** The picture being explored */
  private DigitalPicture picture;
  
  /** The image display */
  private ImageDisplay imageDisplay;
  
  /** the zoom factor (amount to zoom) */
  private double zoomFactor;
  
  /** the number system to use, 0 means starting at 0, 1 means starting at 1 */
  private int numberBase=0;

  /**
   * New GUI Elements : Rotations
   */
  
  /** current rotation in degrees  */
  private int baseRotation=0;
  private JLabel currentRotation;
  /** rotate +90 button */
  private JButton increaseRotation;
  /** rotate -90 button */
  private JButton decreaseRotation;
  
  /**
   * New GUI Elements : RGB shift
   */
  
  /** amount added to every R, G and B value (-255 to 255) */
  private int rgbShift=0;
  /** how much one click changes the shift */
  private static final int RGB_STEP = 15;
  private JLabel currentRgbShift;
  private JButton increaseRgbShift;
  private JButton decreaseRgbShift;
  
  /** untouched copy of the original image; all edits are rebuilt from this */
  private BufferedImage originalImage;
  /** the image currently shown (rotated + color shifted) */
  private BufferedImage displayedImage;
  
  /**
   * Public constructor 
   * @param picture the picture to explore
   */
  public PictureExplorer(DigitalPicture picture)
  {
    // set the fields
    this.picture=picture;
    zoomFactor=1;
    
    // keep a private copy of the original image 
    BufferedImage src = picture.getBufferedImage();
    originalImage = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = originalImage.createGraphics();
    g.drawImage(src, 0, 0, null);
    g.dispose();
    displayedImage = originalImage;
    
    // create the window and set things up
    createWindow();
  }
  
  /**
   * Changes the number system to start at one
   */
  public void changeToBaseOne()
  {
    numberBase=1;
  }
  
  /**
   * Set the title of the frame
   *@param title the title to use in the JFrame
   */
  public void setTitle(String title)
  {
    pictureFrame.setTitle(title);
  }
  
  /**
   * Method to create and initialize the picture frame
   */
  private void createAndInitPictureFrame()
  {
    pictureFrame = new JFrame(); // create the JFrame
    pictureFrame.setResizable(true);  // allow the user to resize it
    pictureFrame.getContentPane().setLayout(new BorderLayout()); // use border layout
    pictureFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // when close stop
    pictureFrame.setTitle(picture.getTitle());
    PictureExplorerFocusTraversalPolicy newPolicy = new PictureExplorerFocusTraversalPolicy();
    pictureFrame.setFocusTraversalPolicy(newPolicy);
    
  }
  
  /**
   * Method to create the menu bar, menus, and menu items
   */
  private void setUpMenuBar()
  {
    //create menu
    menuBar = new JMenuBar();
    zoomMenu = new JMenu("Zoom");
    twentyFive = new JMenuItem("25%");
    fifty = new JMenuItem("50%");
    seventyFive = new JMenuItem("75%");
    hundred = new JMenuItem("100%");
    hundred.setEnabled(false);
    hundredFifty = new JMenuItem("150%");
    twoHundred = new JMenuItem("200%");
    fiveHundred = new JMenuItem("500%");
    
    // add the action listeners
    twentyFive.addActionListener(this);
    fifty.addActionListener(this);
    seventyFive.addActionListener(this);
    hundred.addActionListener(this);
    hundredFifty.addActionListener(this);
    twoHundred.addActionListener(this);
    fiveHundred.addActionListener(this);
    
    // add the menu items to the menus
    zoomMenu.add(twentyFive);
    zoomMenu.add(fifty);
    zoomMenu.add(seventyFive);
    zoomMenu.add(hundred);
    zoomMenu.add(hundredFifty);
    zoomMenu.add(twoHundred);
    zoomMenu.add(fiveHundred);
    menuBar.add(zoomMenu);
    
    // set the menu bar to this menu
    pictureFrame.setJMenuBar(menuBar);
  }
  
  /**
   * Create and initialize the scrolling image
   */
  private void createAndInitScrollingImage()
  {
    scrollPane = new JScrollPane();
    
    imageDisplay = new ImageDisplay(displayedImage);
    imageDisplay.addMouseMotionListener(this);
    imageDisplay.addMouseListener(this);
    imageDisplay.setToolTipText("Click a mouse button on a pixel to see the pixel information");
    scrollPane.setViewportView(imageDisplay);
    pictureFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
  }
  
  /**
   * Creates the JFrame and sets everything up
   */
  private void createWindow()
  {
    // create the picture frame and initialize it
    createAndInitPictureFrame();
    
    // set up the menu bar
    setUpMenuBar();
    
    //create the information panel
    createInfoPanel();
    
    //creates the scrollpane for the picture
    createAndInitScrollingImage();
    
    // show the picture in the frame at the size it needs to be
    pictureFrame.pack();
    pictureFrame.setVisible(true);
  }


  //------------------------------------------------------------------------------------------------  

  /**
   * Set up angle changers (each click rotates the image 90 degrees)
   * Increase = +90 (counterclockwise, same as rotate90Image in ImageApp)
   * Decrease = -90 (clockwise, same as rotateNeg90Image in ImageApp)
   */

  private void setUpRotations()
  {
    Icon prevIcon = new ImageIcon(DigitalPicture.class.getResource("lib/leftArrow.gif"),
                                  "decrease rotation");
    Icon nextIcon = new ImageIcon(DigitalPicture.class.getResource("lib/rightArrow.gif"),
                                  "increase rotation");

    decreaseRotation = new JButton(prevIcon);
    increaseRotation = new JButton(nextIcon);
    currentRotation = new JLabel("Rotation: " + baseRotation);

    decreaseRotation.setToolTipText("Rotate -90 degrees");
    increaseRotation.setToolTipText("Rotate +90 degrees");

    decreaseRotation.setPreferredSize(
        new Dimension(prevIcon.getIconWidth() + 2, prevIcon.getIconHeight() + 2));
    increaseRotation.setPreferredSize(
        new Dimension(nextIcon.getIconWidth() + 2, nextIcon.getIconHeight() + 2));
    
    increaseRotation.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          baseRotation = (baseRotation + 90) % 360;
          currentRotation.setText("Rotation: " + baseRotation);
          updateImage();
        }
      });

    decreaseRotation.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          baseRotation = (baseRotation - 90 + 360) % 360;
          currentRotation.setText("Rotation: " + baseRotation);
          updateImage();
        }
      });
  }
  
  /**
   * Set up the RGB shift toggler. Moving the toggler adds (or subtracts)
   * RGB_STEP to every red, green and blue value of the image.
   */
  private void setUpRgbShift()
  {
    Icon prevIcon = new ImageIcon(DigitalPicture.class.getResource("lib/leftArrow.gif"),
                                  "decrease RGB shift");
    Icon nextIcon = new ImageIcon(DigitalPicture.class.getResource("lib/rightArrow.gif"),
                                  "increase RGB shift");

    decreaseRgbShift = new JButton(prevIcon);
    increaseRgbShift = new JButton(nextIcon);
    currentRgbShift = new JLabel("RGB Shift: " + rgbShift);

    decreaseRgbShift.setToolTipText("Darken (subtract " + RGB_STEP + " from every R, G, B)");
    increaseRgbShift.setToolTipText("Brighten (add " + RGB_STEP + " to every R, G, B)");

    decreaseRgbShift.setPreferredSize(
        new Dimension(prevIcon.getIconWidth() + 2, prevIcon.getIconHeight() + 2));
    increaseRgbShift.setPreferredSize(
        new Dimension(nextIcon.getIconWidth() + 2, nextIcon.getIconHeight() + 2));

    increaseRgbShift.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          rgbShift = Math.min(255, rgbShift + RGB_STEP);
          currentRgbShift.setText("RGB Shift: " + rgbShift);
          updateImage();
        }
      });

    decreaseRgbShift.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          rgbShift = Math.max(-255, rgbShift - RGB_STEP);
          currentRgbShift.setText("RGB Shift: " + rgbShift);
          updateImage();
        }
      });
  }
  
  /**
   * Keeps a color value inside 0-255
   */
  private int clamp(int value)
  {
    return Math.max(0, Math.min(255, value));
  }
  
  /**
   * Rebuilds the displayed image from the original by applying the RGB shift
   * and then the rotation, then refreshes the screen.
   */
  private void updateImage()
  {
    int w = originalImage.getWidth();
    int h = originalImage.getHeight();
    int quarterTurns = ((baseRotation / 90) % 4 + 4) % 4;
    boolean swap = (quarterTurns % 2 == 1);
    
    BufferedImage result = new BufferedImage(swap ? h : w, swap ? w : h,
                                             BufferedImage.TYPE_INT_ARGB);
    
    for (int y = 0; y < h; y++) {
  for (int x = 0; x < w; x++) {
    Color c = new Color(originalImage.getRGB(x, y), true);  // true = keep alpha

    int a = c.getAlpha();
    int r = clamp(c.getRed()   + rgbShift);
    int g = clamp(c.getGreen() + rgbShift);
    int b = clamp(c.getBlue()  + rgbShift);

    Color shifted = new Color(r, g, b, a);

    int nx, ny;
    if (quarterTurns == 1) {          // 90 (counterclockwise)
      nx = y;
      ny = w - 1 - x;
    } else if (quarterTurns == 2) {   // 180
      nx = w - 1 - x;
      ny = h - 1 - y;
    } else if (quarterTurns == 3) {   // 270 (clockwise 90)
      nx = h - 1 - y;
      ny = x;
    } else {                          // 0
      nx = x;
      ny = y;
    }
    result.setRGB(nx, ny, shifted.getRGB());
  }
}
    
    displayedImage = result;
    
    // redraw at the current zoom level and refresh the pixel info panel
    zoom(zoomFactor);
    displayPixelInformation(colIndex, rowIndex);
    pictureFrame.repaint();
  }
//------------------------------------------------------------------------------------------------  
  
/**
   * Method to set up the next and previous buttons for the
   * pixel location information
   */
  private void setUpNextAndPreviousButtons()
  {
    // create the image icons for the buttons
    Icon prevIcon = new ImageIcon(DigitalPicture.class.getResource("lib/leftArrow.gif"), 
                                  "previous index");
    Icon nextIcon = new ImageIcon(DigitalPicture.class.getResource("lib/rightArrow.gif"), 
                                  "next index");
    // create the arrow buttons
    colPrevButton = new JButton(prevIcon);
    colNextButton = new JButton(nextIcon);
    rowPrevButton = new JButton(prevIcon);
    rowNextButton = new JButton(nextIcon);
    
    // set the tool tip text
    colNextButton.setToolTipText("Click to go to the next column value");
    colPrevButton.setToolTipText("Click to go to the previous column value");
    rowNextButton.setToolTipText("Click to go to the next row value");
    rowPrevButton.setToolTipText("Click to go to the previous row value");
    
    // set the sizes of the buttons
    int prevWidth = prevIcon.getIconWidth() + 2;
    int nextWidth = nextIcon.getIconWidth() + 2;
    int prevHeight = prevIcon.getIconHeight() + 2;
    int nextHeight = nextIcon.getIconHeight() + 2;
    Dimension prevDimension = new Dimension(prevWidth,prevHeight);
    Dimension nextDimension = new Dimension(nextWidth, nextHeight);
    colPrevButton.setPreferredSize(prevDimension);
    rowPrevButton.setPreferredSize(prevDimension);
    colNextButton.setPreferredSize(nextDimension);
    rowNextButton.setPreferredSize(nextDimension);
    
    // handle previous column button press
    colPrevButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        colIndex--;
        if (colIndex < 0)
          colIndex = 0;
        displayPixelInformation(colIndex,rowIndex);
      }
    });
    
    // handle previous row button press
    rowPrevButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        rowIndex--;
        if (rowIndex < 0)
          rowIndex = 0;
        displayPixelInformation(colIndex,rowIndex);
      }
    });
    
    // handle next column button press
    colNextButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        colIndex++;
        if (colIndex >= displayedImage.getWidth())
          colIndex = displayedImage.getWidth() - 1;
        displayPixelInformation(colIndex,rowIndex);
      }
    });
    
    // handle next row button press
    rowNextButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        rowIndex++;
        if (rowIndex >= displayedImage.getHeight())
          rowIndex = displayedImage.getHeight() - 1;
        displayPixelInformation(colIndex,rowIndex);
      }
    });
  }
  
  /**
   * Create the pixel location panel
   * @param labelFont the font for the labels
   * @return the location panel
   */
  public JPanel createLocationPanel(Font labelFont) {
    
    // create a location panel
    JPanel locationPanel = new JPanel();
    locationPanel.setLayout(new FlowLayout());
    Box hBox = Box.createHorizontalBox();
    
    // create the labels
    rowLabel = new JLabel("Row:");
    colLabel = new JLabel("Column:");
    
    // create the text fields
    colValue = new JTextField(Integer.toString(colIndex + numberBase),6);
    colValue.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        displayPixelInformation(colValue.getText(),rowValue.getText());
      }
    });
    rowValue = new JTextField(Integer.toString(rowIndex + numberBase),6);
    rowValue.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        displayPixelInformation(colValue.getText(),rowValue.getText());
      }
    });
    
    // set up the next and previous buttons
    setUpNextAndPreviousButtons();
    
    // set up the font for the labels
    colLabel.setFont(labelFont);
    rowLabel.setFont(labelFont);
    colValue.setFont(labelFont);
    rowValue.setFont(labelFont);
    
    // add the items to the vertical box and the box to the panel
    hBox.add(Box.createHorizontalGlue());
    hBox.add(rowLabel);
    hBox.add(rowPrevButton);
    hBox.add(rowValue);
    hBox.add(rowNextButton);
    hBox.add(Box.createHorizontalStrut(10));
    hBox.add(colLabel);
    hBox.add(colPrevButton);
    hBox.add(colValue);
    hBox.add(colNextButton);
    locationPanel.add(hBox);
    hBox.add(Box.createHorizontalGlue());
    
    return locationPanel;
  }
  
  /**
   * Create the color information panel
   * @param labelFont the font to use for labels
   * @return the color information panel
   */
  private JPanel createColorInfoPanel(Font labelFont)
  {
    // create a color info panel
    JPanel colorInfoPanel = new JPanel();
    colorInfoPanel.setLayout(new FlowLayout());
    
    // get the color of the pixel at the x and y (from the displayed image)
    Color pixelColor = new Color(displayedImage.getRGB(colIndex, rowIndex));
    
    // create the labels
    rValue = new JLabel("R: " + pixelColor.getRed());
    gValue = new JLabel("G: " + pixelColor.getGreen());
    bValue = new JLabel("B: " + pixelColor.getBlue());
    
    // create the sample color panel and label
    colorLabel = new JLabel("Color at location: ");
    colorPanel = new JPanel();
    colorPanel.setBorder(new LineBorder(Color.black,1));
    
    // set the color sample to the pixel color
    
    // set the font
    rValue.setFont(labelFont);
    gValue.setFont(labelFont);
    bValue.setFont(labelFont);
    colorLabel.setFont(labelFont);
    colorPanel.setPreferredSize(new Dimension(25,25));
    
    // add items to the color information panel
    colorInfoPanel.add(rValue);
    colorInfoPanel.add(gValue);
    colorInfoPanel.add(bValue);
    colorInfoPanel.add(colorLabel);
    colorInfoPanel.add(colorPanel);
    
    return colorInfoPanel; 
  }
  
  /**
   * Creates the North JPanel with all the pixel location
   * and color information
   */
  private void createInfoPanel(){

    // create the info panel and set the layout
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BorderLayout());
    
    // create the font
    Font largerFont = new Font(infoPanel.getFont().getName(),
                               infoPanel.getFont().getStyle(),14);
    
    // create the pixel location panel
    JPanel locationPanel = createLocationPanel(largerFont);
    
    // create the color information panel
    JPanel colorInfoPanel = createColorInfoPanel(largerFont);
    
    // add the panels to the info panel
    infoPanel.add(BorderLayout.NORTH,locationPanel);
    infoPanel.add(BorderLayout.SOUTH,colorInfoPanel); 
    
    // rotation controls + RGB shift controls
    setUpRotations();
    setUpRgbShift();
    currentRotation.setFont(largerFont);
    currentRgbShift.setFont(largerFont);
    JPanel controlsPanel = new JPanel(new FlowLayout());
    controlsPanel.add(decreaseRotation);
    controlsPanel.add(currentRotation);
    controlsPanel.add(increaseRotation);
    controlsPanel.add(Box.createHorizontalStrut(20));
    controlsPanel.add(decreaseRgbShift);
    controlsPanel.add(currentRgbShift);
    controlsPanel.add(increaseRgbShift);
    infoPanel.add(BorderLayout.CENTER, controlsPanel);

    // add the info panel
    pictureFrame.getContentPane().add(BorderLayout.NORTH,infoPanel);
  } 
  
  /**
   * Method to check that the current position is in the viewing area and if
   * not scroll to center the current position if possible
   */
  public void checkScroll()
  {
    // get the x and y position in pixels
    int xPos = (int) (colIndex * zoomFactor); 
    int yPos = (int) (rowIndex * zoomFactor); 
    
    // only do this if the image is larger than normal
    if (zoomFactor > 1) {
      
      // get the rectangle that defines the current view
      JViewport viewport = scrollPane.getViewport();
      Rectangle rect = viewport.getViewRect();
      int rectWidth = (int) rect.getWidth();
      int rectHeight = (int) rect.getHeight();
      
      
      // calculate how to position the current position in the middle of the viewing
      // area
      int viewX = xPos - (int) (rectWidth / 2);
      int viewY = yPos - (int) (rectHeight / 2);
      
      // move the viewport upper left point
      viewport.scrollRectToVisible(new Rectangle(viewX,viewY,rectWidth,rectHeight));
    }
  }
  
  /**
   * Zooms in the on picture by scaling the image.  
   * It is extremely memory intensive.
   * @param factor the amount to zoom by
   */
  public void zoom(double factor)
  {
    // save the current zoom factor
    zoomFactor = factor;
    
    // calculate the new width and height and get an image that size
    int width = (int) (displayedImage.getWidth()*zoomFactor);
    int height = (int) (displayedImage.getHeight()*zoomFactor);
    
    // set the scroll image icon to the new image
    imageDisplay.setImage(displayedImage.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    imageDisplay.setCurrentX((int) (colIndex * zoomFactor));
    imageDisplay.setCurrentY((int) (rowIndex * zoomFactor));
    imageDisplay.revalidate();
    checkScroll();  // check if need to reposition scroll
  }
  
  /**
   * Repaints the image on the scrollpane.  
   */
  public void repaint()
  {
    pictureFrame.repaint();
  }
  
  //****************************************//
  //               Event Listeners          //
  //****************************************//
  
  /**
   * Called when the mouse is dragged (button held down and moved)
   * @param e the mouse event
   */
  public void mouseDragged(MouseEvent e)
  {
    displayPixelInformation(e);
  }
  
  /**
   * Method to check if the given x and y are in the picture
   * @param column the horizontal value
   * @param row the vertical value
   * @return true if the row and column are in the picture 
   * and false otherwise
   */
  private boolean isLocationInPicture(int column, int row)
  {
    boolean result = false; // the default is false
    if (column >= 0 && column < displayedImage.getWidth() &&
        row >= 0 && row < displayedImage.getHeight())
      result = true;
    
    return result;
  }
  
  /**
   * Method to display the pixel information from the passed x and y but
   * also converts x and y from strings
   * @param xString the x value as a string from the user
   * @param yString the y value as a string from the user
   */
  public void displayPixelInformation(String xString, String yString)
  {
    int x = -1;
    int y = -1;
    try {
      x = Integer.parseInt(xString);
      x = x - numberBase;
      y = Integer.parseInt(yString);
      y = y - numberBase;
    } catch (Exception ex) {
    }
    
    if (x >= 0 && y >= 0) {
      displayPixelInformation(x,y);
    }
  }
  
  /**
   * Method to display pixel information for the passed x and y
   * @param pictureX the x value in the picture
   * @param pictureY the y value in the picture
   */
  private void displayPixelInformation(int pictureX, int pictureY)
  {
    // check that this x and y are in range
    if (isLocationInPicture(pictureX, pictureY))
    {
      // save the current x and y index
      colIndex = pictureX;
      rowIndex = pictureY;
      
      // get the color of the pixel at the x and y in the displayed image
      Color pixelColor = new Color(displayedImage.getRGB(colIndex, rowIndex));
      
      // set the values based on the pixel
      colValue.setText(Integer.toString(colIndex  + numberBase));
      rowValue.setText(Integer.toString(rowIndex + numberBase));
      rValue.setText("R: " + pixelColor.getRed());
      gValue.setText("G: " + pixelColor.getGreen());
      bValue.setText("B: " + pixelColor.getBlue());
      colorPanel.setBackground(pixelColor);
      
    } 
    else
    {
      clearInformation();
    }
    
    // notify the image display of the current x and y
    imageDisplay.setCurrentX((int) (colIndex * zoomFactor));
    imageDisplay.setCurrentY((int) (rowIndex * zoomFactor));
  }
  
  /**
   * Method to display pixel information based on a mouse event
   * @param e a mouse event
   */
  private void displayPixelInformation(MouseEvent e)
  {
    
    // get the cursor x and y
    int cursorX = e.getX();
    int cursorY = e.getY();
    
    // get the x and y in the original (not scaled image)
    int pictureX = (int) (cursorX / zoomFactor + numberBase);
    int pictureY = (int) (cursorY / zoomFactor + numberBase);
    
    // display the information for this x and y
    displayPixelInformation(pictureX,pictureY);
    
  }
  
  /**
   * Method to clear the labels and current color and reset the 
   * current index to -1
   */
  private void clearInformation()
  {
    colValue.setText("N/A");
    rowValue.setText("N/A");
    rValue.setText("R: N/A");
    gValue.setText("G: N/A");
    bValue.setText("B: N/A");
    colorPanel.setBackground(Color.black);
    colIndex = -1;
    rowIndex = -1;
  }
  
  /**
   * Method called when the mouse is moved with no buttons down
   * @param e the mouse event
   */
  public void mouseMoved(MouseEvent e)
  {}
  
  /**
   * Method called when the mouse is clicked
   * @param e the mouse event
   */
  public void mouseClicked(MouseEvent e)
  {
    displayPixelInformation(e);
  }
  
  /**
   * Method called when the mouse button is pushed down
   * @param e the mouse event
   */ 
  public void mousePressed(MouseEvent e)
  {
    displayPixelInformation(e);
  }
  
  /**
   * Method called when the mouse button is released
   * @param e the mouse event
   */
  public void mouseReleased(MouseEvent e)
  {
  }
  
  /**
   * Method called when the component is entered (mouse moves over it)
   * @param e the mouse event
   */
  public void mouseEntered(MouseEvent e)
  {
  }
  
  /**
   * Method called when the mouse moves over the component
   * @param e the mouse event
   */
  public void mouseExited(MouseEvent e)
  {
  }
  
  /**
   * Method to enable all menu commands
   */
  private void enableZoomItems()
  {
    twentyFive.setEnabled(true);
    fifty.setEnabled(true);
    seventyFive.setEnabled(true);
    hundred.setEnabled(true);
    hundredFifty.setEnabled(true);
    twoHundred.setEnabled(true);
    fiveHundred.setEnabled(true);
  }
  
  /**
   * Controls the zoom menu bar
   *
   * @param a the ActionEvent 
   */
  public void actionPerformed(ActionEvent a)
  {
    
    if(a.getActionCommand().equals("Update"))
    {
      this.repaint();
    }
    
    if(a.getActionCommand().equals("25%"))
    {
      this.zoom(.25);
      enableZoomItems();
      twentyFive.setEnabled(false);
    }
    
    if(a.getActionCommand().equals("50%"))
    {
      this.zoom(.50);
      enableZoomItems();
      fifty.setEnabled(false);
    }
    
    if(a.getActionCommand().equals("75%"))
    {
      this.zoom(.75);
      enableZoomItems();
      seventyFive.setEnabled(false);
    }
    
    if(a.getActionCommand().equals("100%"))
    {
      this.zoom(1.0);
      enableZoomItems();
      hundred.setEnabled(false);
    }
    
    if(a.getActionCommand().equals("150%"))
    {
      this.zoom(1.5);
      enableZoomItems();
      hundredFifty.setEnabled(false);
    }
    
    if(a.getActionCommand().equals("200%"))
    {
      this.zoom(2.0);
      enableZoomItems();
      twoHundred.setEnabled(false);
    }
    
    if(a.getActionCommand().equals("500%"))
    {
      this.zoom(5.0);
      enableZoomItems();
      fiveHundred.setEnabled(false);
    }
  }
  
  
  
  /**
   * Class for establishing the focus for the textfields
   */
  private class PictureExplorerFocusTraversalPolicy
    extends FocusTraversalPolicy {
    
    /**
     * Method to get the next component for focus
     */
    public Component getComponentAfter(Container focusCycleRoot,
                                       Component aComponent) {
      if (aComponent.equals(colValue))
        return rowValue;
      else 
        return colValue;
    }
    
    /**
     * Method to get the previous component for focus
     */
    public Component getComponentBefore(Container focusCycleRoot,
                                        Component aComponent) {
      if (aComponent.equals(colValue))
        return rowValue;
      else 
        return colValue;
    }
    
    public Component getDefaultComponent(Container focusCycleRoot) {
      return colValue;
    }
    
    public Component getLastComponent(Container focusCycleRoot) {
      return rowValue;
    }
    
    public Component getFirstComponent(Container focusCycleRoot) {
      return colValue;
    }
  }
  
  /**
   * Test Main.  It will explore the beach 
   */
  public static void main( String args[])
  {
    Picture pix = new Picture("beach.jpg");
    pix.explore();
  }
  
}