package structures;

import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

/**
 * The AACMappings class has an AssociativeArray<String, AACCategory> that maps an image to
 * the category found by clicking on the image
 * 
 * @author Maria Rodriguez 
 * October 14, 2023 
 * CSC207 MP 5
 */

public class AACMappings {
  /*-------------------------------------
  * Fields |
  *-------------------------------------*/

  // a category that keeps track of every category (AKA the lines in the file without a '>')
  AACCategory topLevelCategory;

  // keeps track of the category the user is currently in
  AACCategory currentCategory;

  /* an array that maps the string file to the category (that contains images and
  * text related to the file)
  */
  AssociativeArray<String, AACCategory> mapping;

  /*-------------------------------------
  * Constructors |
  *-------------------------------------*/

  /*reads in a file and creates top level categories while mapping them to the list of
  * image/text paired categories that are found in the file
  */
  public AACMappings(String filename){
    this.topLevelCategory = new AACCategory("");
    //creates a top level category with an empty string name

    this.currentCategory = this.topLevelCategory;
    this.mapping = new AssociativeArray<>(); //initializes a new mapping associative array
    try {
      Scanner scanner = new Scanner(new File(filename));
        //read the file in, then use it to create categories. write
        // to file only works to save updated categories u added

      while(scanner.hasNextLine()){ //reads through the file
        String line = scanner.nextLine();
        String[] splitLine = line.split(" "); //separates image from text
        if(line.charAt(0) != '>'){  //finds top level categories
    
          this.mapping.set(splitLine[0], new AACCategory(splitLine[1]));
          //sets the mapping between the image and the category

          this.topLevelCategory.addItem(splitLine[0], splitLine[1]);
          //creates the home category and adds the image and text pair

          try {
            this.currentCategory = this.mapping.get(splitLine[0]);
            //sets the currentCategory to the category associated w the image read in from file

          } catch (Exception e) {
            System.err.println("Did not set the mapping properly");
          }//try

        } else {
          this.currentCategory.addItem(splitLine[0].substring(1, splitLine[0].length()), splitLine[1]); 
          // removes the '>' from the img path
        }//if
  
      }//while
      scanner.close();
      this.currentCategory = this.topLevelCategory;
    } catch (Exception e) {
      System.out.println("Cannot find file");
      return;
    }//catch
  }//AACMapings constructor


  /*-------------------------------------
  * Methods |
  *-------------------------------------*/

  /*
   * Adds the mapping to the current category (or the default category if that is the current
   * category)
   */
  public void add(String imageLoc, String text) {
    this.currentCategory.addItem(imageLoc, text);
  }// add

  /*
   * Gets the current category
   */
  public String getCurrentCategory() {
    return currentCategory.getCategory();
  }//getCurrentCategory

  /*
   * Provides an array of all the images in the current category
   */
  public String[] getImageLocs() {
    return this.currentCategory.getImages();
  }//getImageLocs

  /*
   * Given the image location selected, it determines the associated text with the image.
   */
  public String getText(String imageLoc) {
    try {
      if(this.topLevelCategory.hasImage(imageLoc)){ //checks if the img is a top level category img
        String tempCategory = this.topLevelCategory.getText(imageLoc); 
        //gets the string name of the category that the image belongs to
  
        this.currentCategory = this.mapping.get(imageLoc);
        //sets current category to the category associated w that image
  
        return tempCategory;
        //returns the text associated w the image 
      } else {
        if(this.currentCategory.hasImage(imageLoc)){
          return this.currentCategory.getText(imageLoc);
          // if the img is found in the category we are currently in, return the 
          // text associated w that img
        }//if
        
      }//if
    } catch (Exception e) {
      return "Cannot find this image";
    }//catch    

    return "{}";
  }//getText

  /*
   * Determines if the image represents a category or text to speak
   */
  public boolean isCategory(String imageLoc) {
    return topLevelCategory.hasImage(imageLoc);
  }//isCategory

  /*
   * Resets the current category of the AAC back to the default category
   */
  public void reset() {
    this.currentCategory = topLevelCategory;
  }//reset

  /*
   * Writes the ACC mappings stored to a file.
   */
  public void writeToFile(String filename) {
    File updatedFile = new File(filename);
    String[] topLevelImgs = this.topLevelCategory.getImages();

    try {
      PrintWriter pen = new PrintWriter(updatedFile);
      for(int i = 0; i < topLevelImgs.length; i++){
        pen.println(topLevelImgs[i] + " " + this.topLevelCategory.getText(topLevelImgs[i]));
          
        //imgs = mapping.getKeyAt(i).getImages();
        AACCategory tempCategory = mapping.get(topLevelImgs[i]);
        //gets the category we're currently in, prepared for the '>'

        String[] currentCategoryImgs = tempCategory.getImages();
        //gets all the imgs in the category


        for(int j = 0; j < currentCategoryImgs.length; j++){
          pen.println(">" + currentCategoryImgs[j] + " " + tempCategory.getText(currentCategoryImgs[j]));
          //here I attempt to save every image within the category in a for loop
        }//for
      }//for
      pen.close();

      /* 
      Scanner scanner = new Scanner(updatedFile);
      String[] listOfImgs = topLevelCategory.contents.keys();
          //this gives us a list of every image that represents a top
          //level category. We use this to iterate through our categories
    
      String recentAdd = listOfImgs[listOfImgs.length - 1];
          //this gets the key of the most recently added category
    
      String categoryName = topLevelCategory.getText(recentAdd);
          //this returns the Value(text) associated with the Key(img)
      
      
      while(scanner.hasNextLine()){
        
        String line = scanner.nextLine();
        String[] splitLine = line.split(" ");
        if(!(line.startsWith(">"))){
          

          if(splitLine[1].equals(currentCategory.categoryName)){
            //^checks if we're in the right category

            try {
              //PrintWriter pen = new PrintWriter(updatedFile);
              // ... iterate till the end of the file, then write?
              pen.close();
            } catch (Exception e) {
              // ...
            }
          }//if
          
        }//if it is a toplevelCategory
        
        
      }
      scanner.close();
      
    //at this point, we reached the end of the file
      try {
        pen.println(recentAdd + " " + categoryName);
        pen.close();
      } catch (Exception e) {}
     */

    } catch (Exception e) {
      System.err.println("Cannot find file");
    }//catch    
  }//write to file


}//AACMappings
