# find_route
A search algorithm that can find a route between any two cities.

Programming Language : Java

Code Structure:
1. The code is split in to two classes named FindRoute and Stations.
2. Both the classes are in the format of .java file extensions.
3. The class Stations is a constructor class which returns Source Station, Destination
Station and Path Cost between stations. This class does not require run time arguments.
4. The class FindRoute is a class with main method and consists of methods to find out
optimal path. This class requires run time arguments.

Run Code:
Please follow below steps to run the code:
1. The code is kept in folder in the fashion: assignment1_chs8824/find_route_pck
2. Folder find_route_pck contains FindRoute.java, Stations.java and input.txt.
2. Open your shell terminal and change current directory to code directory
by typing following command :
cd assignment1_chs8824/find_route_pck
3. Now compile the java file before excecuting it by following command :
javac Stations.java
javac FindRoute.java
4.Now run code by giving command: 
java FindRoute input.txt Bremen Frankfurt
5.You can change the last three input arguments but make sure there are no extra spaces.
6.If the input text file to be used is placed at othr folder then make sure you give 
correct path for it.
E.g. java FindRoute input1.txt Bremen Frankfurt



