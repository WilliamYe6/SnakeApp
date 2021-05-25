# SnakeApp

Authors: William Ye  
Language: Java  
Version: 1.8  

# Introduction

Snake is a video game created in the 1970's that originated in arcades which instantly became a classic. 
The player controls a creature resembling a snake, which traverses around on a bordered plane, picking up apples, trying to avoid hitting its own tail or the edges of the playing area. As you collect more apples, the longer the snake gets. 

# Implementation

This simple Java snake app was created using the library Swing (GUI widget toolkit for Java) and AWT (Abstract Window Toolkit which is the older version of Swing).
Furthermore, this snake App is constructed in 3 java files.  

1) SnakeApp.java        Calls the main function that runs the Snake App  
2) GameFrame.java       Sets layout and functionality properties of the GUI when Snake App is opened  
3) GamePanel.java       Applies game mechanics and rules allowing the user to control the moving snake with arrow keys  

# How to Run

* Requirements: JDK and JRE installed

1) Open command prompt and navigate to the directory where these files are found  
2) Enter "javac SnakeApp.java", "javac GameFrame.java", "javac GamePanel.java" on the command line to compile these files
3) Enter "java SnakeApp" to run Snake

# How to Play

1) Use arrow keys are used to move the snake in different directions  
2) Avoid colliding the snake head with the borders or the snakes own body  
3) Eat as many apples possible in a single life (Be careful the snake grows longer with more apples eaten)  
4) Use spacebar to pause the game as needed  
5) Restarting the game requires to rerun the SnakeApp.java  

# Expected output of Snake App


![image](https://user-images.githubusercontent.com/74033578/119436449-2db2e500-bcea-11eb-91fa-484e19490ab9.png)  
*Instance of the game running*  


![image](https://user-images.githubusercontent.com/74033578/119436603-74084400-bcea-11eb-81e4-2dd7f29cfd89.png)  
*Instance of the game paused*  


![image](https://user-images.githubusercontent.com/74033578/119436480-3b686a80-bcea-11eb-9dc0-a8dc9e800bbb.png)  
*Instance of the game ended with a Highscore feature*


