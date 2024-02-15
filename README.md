# **🧩🧩Piece of Mind By Twana Development🧩🧩**

## Introduction:

<p>Piece of Mind is an application that utilizes reminiscence therapy to help people with dementia to slow down cognitive deterioration and loss of personhood. The app uses photos with special meanings for the PWD, which are uploaded by the caregiver, to generate a jigsaw puzzle for the PWD to solve.</p>
<p>The application allows the caregiver to either take a photo or use one from their gallery, which will be used to generate a jigsaw puzzle. Furthermore, the caregiver will be able to alter the difficulty of the puzzle so that it suits the patient's cognitive skills. There is also a tutorial section that will provide a step by step guide for the caregiver to set up the puzzle for the patient. Finally, the application contains a feeback section, where the caregiver will be able to rate the application. The feedback section also provides a bar graph that showcases the current star ratings for the application submitted by other users.</p>
<p>The main bugs of the application are as follows:</p>
<p>1) Some images from the device may turn out rotated when generated into the puzzle because of the way android studio reads the photos. This can be fixed either by holding the device sideways when using the camera feature, or by taking a screenshot of a photo before importing the screenshot into the application.</p>
<p>2.) The application might take relatively longer to load larger(higher quality) image files. This can be fixed by taking a screenshot of the photo and using that screenshot as the puzzle image</p>

<p>Here is also a [link](https://csil-git1.cs.surrey.sfu.ca/jwa398/piece-of-mind/-/blob/ruoxi_branch/2022-2-Group01-PieceOfMind.mp4) to a short video demonstrating our application</p>

## Installation:
#### Start by cloning the repository with the following command in the desired folder
#### Clone with SHH:
```git@csil-git1.cs.surrey.sfu.ca:jwa398/piece-of-mind.git```

#### Clone with HTTP:
```https://csil-git1.cs.surrey.sfu.ca/jwa398/piece-of-mind.git```



## File Describe:
**MainMenu.java** --> Displays the main menu (main activity) of the application and allows the user to navigate to the puzzle game, the application settings, application tutorial, or the feedback section. </p>

**Import.java** --> Allows the user to retrieve an image from his/her gallery and stores the image. The user will also have the option to open the camera and take a photo from the application. </p>

**UserInput.java** --> Displays a blank line for the user to type a sentence based on the image chosen. The sentence is stored and transferred to the DisplayPhotoInput activity. </p>

**DisplayPhotoInput.java** --> Combines the chosen image and the sentence inputted by the user and displays them for the user. </p>

**PuzzleGenerator.java** --> Generates the puzzle based on the image chosen by the user. </p>

**Settings1.java** --> Allows the user to alter the difficulty of the puzzle. </p>

**SplitImageAndroid.java** --> Loads and splits the chosen image to puzzle pieces by desired columns and rows. </p>

**SolveScreen.java** --> Displays the photo user has solved and a button to go back to the main menu. </p>

**Tutorial.java** --> Contains a step by step tutorial of the game for the caregiver. </p>

**dbHandler.java** --> Accesses the database to update ratings and review all past ratings. </p>

**reviewObject.java** --> Stores all user’s rating inputs such as rating scores and comments. </p>

**Information.java** --> Updates the user’s input to the database. </p>

**Ratings_chart.java** --> Shows a bar chart containing a record of all past rating scores. </p>


## Database Usage:
<p>Piece of Mind uses Firebase as its primary external storage to store two things: 1) User Feedback & 2) User Rating</p>
<p>Here is a snapshot of how the database is managed for our application --> [DataBase Snapshot](https://csil-git1.cs.surrey.sfu.ca/jwa398/piece-of-mind/-/blob/master/doc/OneDataBaseEntry.png)</p>
<p>The application does not have a built in system that showcases the usage time of our application. Not only tracking usage time can be tricky to implement properly, but we also saw no reason to implement such feature(or any other similar features) because Firebase already has built in functionalities that provide all the statistics needed to track the performance and the quality of the application over time.</p>
<p>Here are some examples of the statistics that the Firebase analytics feature provides in the case of our application:</p>
<p>[Average Engagement Time](https://csil-git1.cs.surrey.sfu.ca/jwa398/piece-of-mind/-/blob/master/doc/AverageTime.png)</p>
<p>[User Activity over Time](https://csil-git1.cs.surrey.sfu.ca/jwa398/piece-of-mind/-/blob/master/doc/UserActivity.png)</p>
<p>[Views by Page Title and Screen Class](https://csil-git1.cs.surrey.sfu.ca/jwa398/piece-of-mind/-/blob/master/doc/Activity.png)</p>

## Authors
SFU CMPT 276 Introduction to Software Engineering</p>
Group 01
##### Team member
| Name                     | Email           |
|--------------------------|-----------------|
| Sepehr Ahmadipourshirazi | sahmadip@sfu.ca |
| Callum Alexander         | cja66@sfu.ca    |
| Huy Ngoc Nguyen (Luke)   | hnn3@sfu.ca     |
| Ruoxi Tong (Cici)        | rta55@sfu.ca    |
|Jeffrey Wu|jwa398@sfu.ca|

## Code Style Guide:
↓For info regarding the coding visit the link below↓</p>
    [Code Style Guide](https://csil-git1.cs.surrey.sfu.ca/jwa398/piece-of-mind/-/blob/master/doc/CodeStyleGuide.md
)



