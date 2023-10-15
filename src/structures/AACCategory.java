package structures;

/**
 * The AACCategory class has an AssociativeArray<String, String> that maps image locations
 * to words that will be spoken by the TTS
 * 
 * @author Maria Rodriguez
 * October 14, 2023
 * CSC207 MP 5
 */

public class AACCategory{
/*-------------------------------------
* Fields |
*-------------------------------------*/  

// An Associative Array that maps images to their respective text
AssociativeArray<String,String> contents = new AssociativeArray<String, String>();

// Saves the name of the Category
String categoryName;

/*-------------------------------------
* Constructors |
*-------------------------------------*/  

//Creates an AACCategory named after the specified string
//Represents the mappings for a single page of items that should be displayed
public AACCategory(String name){
  this.categoryName = name;
}

/*-------------------------------------
* Methods |
*-------------------------------------*/  

/*
 * Adds the mapping of the imageLoc to 
 * the text to the category.
 */
public void addItem(String imageLoc, String text){
  this.contents.set(imageLoc, text);
}

/*
 * Returns the name of the category
 */
public String getCategory(){
  return this.categoryName;
}

/*
 * Returns an array of all the images in the category
 */
public String[] getImages(){
  return this.contents.keys();
}

/*
 * Returns the text associated with the 
 * given image loc in this category
 */
public String getText(String imageLoc){
  try {
    return this.contents.get(imageLoc);
  } catch (Exception e) {
    return "Error: Could not find text";
  }
}

/*
 * Determines if the provided images is stored in the category
 */
public boolean hasImage(String imageLoc){
  return this.contents.hasKey(imageLoc);
}
}
