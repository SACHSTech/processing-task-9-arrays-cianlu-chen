import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
  // Variables
  PImage imgBackground;
  PImage imgWalkRight;
  PImage imgWalkLeft;
  PImage imgStandStill;
  PImage imgHeart;
  PImage imgColdHeart;
  PImage imgGameOver;

  boolean blnLeft = false;
  boolean blnRight = false;

  float[] snowY = new float[150];
  float[] snowX = new float[150];

  boolean[] blnSnowHide = new boolean[150];

  int intSnowSpeed = 1;

  int intManX = 375;
  int intManSpeed = 5;

  int intLives = 3;
  	
  public void settings() {
    size(750, 750);
  }

  public void setup() {
    imgBackground = loadImage("snow background.jpeg");
    imgBackground.resize(750, 750);

    imgGameOver = loadImage("game over.jpg");
    imgGameOver.resize(750, 750);

    imgWalkRight = loadImage("walk right.png");
    imgWalkRight.resize(91, 138);
    
    imgWalkLeft = loadImage("walk left.png");
    imgWalkLeft.resize(91, 138);
    
    imgStandStill = loadImage("standing still.png");
    imgStandStill.resize(58, 138);

    imgHeart = loadImage("heart.png");
    imgHeart.resize(50, 50);

    imgColdHeart = loadImage("cold heart.png");
    imgColdHeart.resize(50, 50);

    // Sets a random number for snowX and snowY
    for (int i = 0; i < snowY.length; i++) {
      snowY[i] = random(-350, 401);
    }

    for (int i = 0; i < snowX.length; i++) {
      snowX[i] = random(width);
    }

    // Sets all of blnSnowHide to false
    for (int i = 0; i < blnSnowHide.length; i++) {
      blnSnowHide[i] = false;
    }
  }

  public void draw() {
    // Background
    image(imgBackground, 0, 0);
    
    // Man movement
    if(blnLeft && intManX > -25){
      image(imgWalkLeft, intManX - 30, 600);
      intManX -= intManSpeed;
    }

    else if(blnRight && intManX < 700){
      image(imgWalkRight, intManX, 600);
      intManX += intManSpeed;
    }

    else{
      image(imgStandStill, intManX, 600);
    }

    // Barriers
    if(intManX > 700){
      intManSpeed = 0;
    }

    else if(intManX < -25){
      intManSpeed = 0;
    }

    // Drawing the snow
    for (int i = 0; i < snowY.length; i++) {
      ellipse(snowX[i], snowY[i], 40, 40);
  
      snowY[i] += intSnowSpeed;
  
      if (snowY[i] > height) {
        snowY[i] = 0;
      }

      // Collision with player head
      if(snowX[i] > intManX && snowX[i] < intManX + 60){
        if(snowY[i] > 600 && snowY[i] < 650){
          intLives--;
          snowY[i] = -0;
        }
      }

      // Hiding the snowflakes
      if(blnSnowHide[i]){
        snowX[i] = -100;
        blnSnowHide[i] = false;
      }
    }

    // Snow speed
    if(keyPressed){
      if(keyCode == UP){
        intSnowSpeed += 1;
        if(intSnowSpeed == 11){
          intSnowSpeed = 10;
        }
      }

      if(keyCode == DOWN){
        intSnowSpeed -= 1;
        if(intSnowSpeed == 0){
          intSnowSpeed = 1;
        }
      }
    }

    // Lives
    image(imgHeart, 10, 10);
    image(imgHeart, 70, 10);
    image(imgHeart, 130, 10);

    if(intLives < 3){
      image(imgColdHeart, 130, 10);
    }

    if(intLives < 2){
      image(imgColdHeart, 70, 10);
    }

    // Game over
    if(intLives < 1){
      image(imgColdHeart, 10, 10);
      intSnowSpeed = 0;
      intManSpeed = 0;
      delay(1000);
      background(0);
      image(imgGameOver, -15, 0);
    }
  }

  // True or false for man movement
  public void keyPressed(){
    if(key == 'a'){
      blnLeft = true;
    }

    if(key == 'd'){
      blnRight = true;
    }
  }

  public void keyReleased(){
    if(key == 'a'){
      blnLeft = false;
    }

    if(key == 'd'){
      blnRight = false;
    }
  }

  // If user clicks a snowflake, it 'disappears'
  public void mouseClicked(){
    float mouseSnowX[] = new float[150];
    float mouseSnowY[] = new float[150];

    for(int i = 0; i < mouseSnowX.length; i++){
      mouseSnowX[i] = snowX[i];
    }

    for(int i = 0; i < mouseSnowY.length; i++){
      mouseSnowY[i] = snowY[i];
    }

    for(int i = 0; i < mouseSnowX.length; i++){
      if(dist(mouseX, mouseY, mouseSnowX[i], mouseSnowY[i]) < 30){
        blnSnowHide[i] = true;
      }
    }
  }
}