import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
  PImage imgBackground;
  PImage imgWalkRight;
  PImage imgWalkLeft;
  PImage imgStandStill;

  float[] circleY = new float[75];
  int intSnowSpeed = 1;
	
  public void settings() {
    size(750, 750);
  }

  public void setup() {
    imgBackground = loadImage("snow background.jpeg");
    imgBackground.resize(750, 750);

    imgWalkRight = loadImage("walk right.png");
    imgWalkRight.resize(91, 138);
    
    imgWalkLeft = loadImage("walk left.png");
    imgWalkLeft.resize(91, 138);
    
    imgStandStill = loadImage("standing still.png");
    imgStandStill.resize(58, 138);

    for (int i = 0; i < circleY.length; i++) {
      circleY[i] = random(height);
    }
  }

  public void draw() {
    image(imgBackground, 0, 0);
    
    image(imgWalkRight, 100, 600);
    image(imgWalkLeft, 300, 600);
    image(imgStandStill, 500, 600);

    for (int i = 0; i < circleY.length; i++) {
      float circleX = width * i / circleY.length;
      ellipse(circleX, circleY[i], 20, 20);
  
      circleY[i] += intSnowSpeed;
  
      if (circleY[i] > height) {
        circleY[i] = 0;
      }
    }

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

      try {
        Thread.sleep(100);
      } 
      
      catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  
  
}