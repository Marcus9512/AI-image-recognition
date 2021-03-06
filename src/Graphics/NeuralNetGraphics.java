/**
 * This class manages the graphical user interface.
 * Run this main to use the GUI to load and test a NN
 *
 * @author Marcus Jonsson Ewerbring @ Jonas Johansson
 * @verion 1.0
 * @since 2019-01-03
 */
package Graphics;

import NN.MultipleNumbers;
import NN.Neural_Network;
import Tools.Holder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class NeuralNetGraphics extends JPanel {

    Dimension dimension;
    BufferedImage bufferedImage;
    MultipleNumbers multipleNumbers;

    FileNameExtensionFilter filter;
    static JTextArea textArea;
    static Neural_Network nn;



    //Init function for the GUI
    public NeuralNetGraphics(int x, int y){
        dimension = new Dimension(x,y);
        setPreferredSize(dimension);
        filter = new FileNameExtensionFilter("Image filter",ImageIO.getReaderFileSuffixes());
        multipleNumbers = new MultipleNumbers();


    }

    // This function is used to draw the loaded test image on screen
    private void draw(){
        Graphics og = getGraphics();
        if(bufferedImage != null){
            double x = (dimension.width/2.0) - (bufferedImage.getWidth()/2.0);
            double y = (dimension.height/2.0) - (bufferedImage.getHeight()/2.0);
            og.drawImage(bufferedImage,(int)x,(int)y,null);

        }
    }
    // This function draws a blue rectangle around the found numbers in images.
    private void drawResults(ArrayList<Holder> arrayList){
        Graphics og = getGraphics();
        double x = (dimension.width/2.0) - (bufferedImage.getWidth()/2.0);
        double y = (dimension.height/2.0) - (bufferedImage.getHeight()/2.0);
        for(Holder holder: arrayList){
            og.setColor(Color.BLUE);
            og.drawRect((int)x+holder.getX(),(int)y+holder.getY(),Neural_Network.picW,Neural_Network.picH);
        }
    }
    // Loads the image and checks the image size. if it is bigger then 28x28 multipleNumbers is called otherwise neural_network.
    // The result is then drawn to the screen,
    public void loadImage(File file){
        try {
            //read image and draw to screen
            System.out.println(file.getPath());
            bufferedImage = ImageIO.read(file);
            draw();
            //Is the image larger than 28x28
            if(bufferedImage.getHeight() > Neural_Network.picH || bufferedImage.getWidth() > Neural_Network.picW){
                //analyze the image with multipleimage and print the result
                ArrayList<Holder> results = multipleNumbers.analyzeImage(bufferedImage,nn);
                drawResults(results);
                StringBuilder printMe = new StringBuilder();
                for(Holder holder : results){
                    printMe.append(holder.getSol());
                }
                textArea.setText("Network answer: " + printMe);
            }else {
                //analyze a single image via the neural network and draw the result
                Holder res = nn.runNetwork(bufferedImage);
                if(res.getSol() != 10) {
                    textArea.setText("Network answer: " + res.getSol() + " cost: " + res.getHowClose());
                }else{
                    textArea.setText("Network answer: It´s nothing there " + res.getHowClose());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Opens a JFileChooser and asks the user for a neural network, the selected network
    //is loaded in to the class
    public String loadNN(JFrame jFrame, NeuralNetGraphics ng){
        File folder = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+"/SavedNetworks");
       // File folder = new File("SavedNetworks");

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(folder);
        jFileChooser.addChoosableFileFilter(ng.filter);

        int result = jFileChooser.showOpenDialog(jFrame);
        if(result == JFileChooser.APPROVE_OPTION){
            return jFileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
    //The main method initialize all necessary components for the GUI
    public static void main(String[] args) {

        JFrame jFrame = new JFrame("Neural-Network");
        JPanel mainPanel = new JPanel();
        NeuralNetGraphics ng = new NeuralNetGraphics(600,400);

        String input = ng.loadNN(jFrame,ng);

        if(ng == null)
            nn = new Neural_Network(true,"");
        else
            nn = new Neural_Network(false,input);


        JPanel buttonPanel = new JPanel();

        buttonPanel.setBackground(Color.WHITE);
        JButton button = new JButton("Search");
        button.setBounds(60, 400, 220, 30);
        buttonPanel.setLayout(null);
        buttonPanel.setLayout(null);
        buttonPanel.add(button);
        buttonPanel.setLocation(0,500);

        //If the searchbutton is pressed then open a JFileChooser and ask for a image
        //the image is then passed to loadImage
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClassLoader classLoader = getClass().getClassLoader();
                File folder = new File(classLoader.getResource("testing").getFile());
              //  File folder = new File("testing");

                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setCurrentDirectory(folder);
                jFileChooser.addChoosableFileFilter(ng.filter);

                int result = jFileChooser.showOpenDialog(jFrame);
                if(result == JFileChooser.APPROVE_OPTION){
                     ng.loadImage(jFileChooser.getSelectedFile());
                }
            }
        });

        JPanel textPanel = new JPanel();
        textArea = new JTextArea();
        textArea.setBounds(0,400,600,100);
        textPanel.add(textArea);


        mainPanel.add(ng);
        mainPanel.add(textPanel);
        mainPanel.add(button);
        jFrame.add(mainPanel);

        jFrame.setSize(600,600);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
