/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;
import static java.lang.Integer.min;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Nicolle
 */
public class Salf22SuperVisual implements Visualizer {
    private final String name = "Salf22 Super Visual";
    
    private Integer numBands;
    private AnchorPane vizPane;
    
    private String vizPaneInitialStyle = "";
    
    private final Double bandHeightPercentage = 1.3;
    private final Double minEllipseRadius = 10.0;  // 10.0
    private final Double rotatePhaseMultiplier = 300.0;
    
    private Double width = 0.0;
    private Double height = 200.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    private Double halfBandWidth = 0.0;
    
    private final Double startHue = 100.0;
   
    
    private Rectangle[] rectangleOne;
    private Rectangle[] rectangleTwo;
   // private Polygon[] poly;
    private Rectangle[] rectangleThree;
    
  

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void start(Integer numBands, AnchorPane vizPane) {        
        end();
        
       // vizPaneInitialStyle = vizPane.getStyle();
        
        this.numBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        Rectangle clip = new Rectangle(width, height);
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        vizPane.setClip(clip);
        
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        rectangleOne = new Rectangle[numBands];
        rectangleTwo = new Rectangle[numBands];
        rectangleThree = new Rectangle[numBands];
        halfBandWidth = bandWidth / 2;
        
        
        for (int i = 0; i < numBands; i++) {
            Rectangle recOne = new Rectangle();
            Rectangle recTwo = new Rectangle(height, width, 0, 0);
            
            Rectangle recThree = new Rectangle();
            
            double centerX = halfBandWidth + bandWidth * i;
            //change line below back to height / 2 to get what I had
            double centerY = height / 2;
            
            double centerYTwo = height + 8;
            double centerYThree = height + 12;
            
            recOne.setX(centerX - halfBandWidth);
            recOne.setY(centerY - height /2);
            recOne.setWidth(bandWidth);
            recOne.setHeight(minEllipseRadius);
           
            recOne.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(recOne);
            rectangleOne[i] = recOne;
            
            
           
            recTwo.setX(centerX - halfBandWidth);
            recTwo.setY(centerYTwo - height / 1.5);
            recTwo.setWidth(bandWidth - 3);
            recTwo.setHeight(minEllipseRadius);
            recTwo.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            recTwo.setRotate(180);
            vizPane.getChildren().add(recTwo);
           
            rectangleTwo[i] = recTwo;
            
            recThree.setX(centerX - halfBandWidth);
            recThree.setY(centerYThree - height / 3);
            recThree.setWidth(bandWidth);
            recThree.setHeight(minEllipseRadius - 1);
            recThree.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            recThree.setRotate(180);
            vizPane.getChildren().add(recThree);
           
            rectangleThree[i] = recThree;
        }
        
    }
    
    @Override
    public void end() {
        if (rectangleOne != null && rectangleTwo != null && rectangleThree != null) {
            for (Rectangle rec : rectangleOne) {
                vizPane.getChildren().remove(rec);
            }
            rectangleOne = null;
            vizPane.setClip(null);
            vizPane.setStyle(vizPaneInitialStyle);
            for(Rectangle rec : rectangleTwo){
                vizPane.getChildren().remove(rec);
            }
            rectangleTwo = null;
            vizPane.setClip(null);
            vizPane.setStyle(vizPaneInitialStyle);
            
            for(Rectangle rec : rectangleThree){
                vizPane.getChildren().remove(rec);
            }
            rectangleThree = null;
            vizPane.setClip(null);
            vizPane.setStyle(vizPaneInitialStyle);
        }        
    }

    
    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if (rectangleOne == null) {
            return;
        }
        
        Integer num = min(rectangleOne.length, magnitudes.length);
      // Integer num2 = min(rectangleTwo.length, magnitudes.length);
        
        for (int i = 0; i < num; i++) {
            rectangleOne[i].setHeight(((60.0 + magnitudes[i])/60.0) * halfBandHeight + minEllipseRadius);
            rectangleOne[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            rectangleOne[i].setRotate(phases[i]);
            
            rectangleTwo[i].setHeight(((60.0 + magnitudes[i])/60.0) * halfBandHeight + minEllipseRadius);
            rectangleTwo[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            rectangleTwo[i].setRotate(phases[i]);
            
            rectangleThree[i].setHeight(((60.0 + magnitudes[i])/60.0) * halfBandHeight + minEllipseRadius);
            rectangleThree[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            rectangleThree[i].setRotate(phases[i]);
            
           
            
                rectangleOne[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
                //rectangleTwo[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            
           
                
                
//                rectangleTwo[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            
            //rectangles[i].setFill(Color.rgb(0, 0, 0, 100));
        }
        
//        for(int i = 0; i < num2; i++){
//         rectangleTwo[i].setHeight(((60.0 + magnitudes[i])/60.0) * halfBandHeight + minEllipseRadius);
//            rectangleTwo[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
//            rectangleTwo[i].setRotate(phases[i]);
//        }
        Double hue = ((60.0 + magnitudes[0])/60.0) * 360;
        vizPane.setStyle("-fx-background-color: hsb(" + hue + ", 0%, 0%)" );
    }  
}

/**
 *
 * @author Spencer Laird
 */

