# FlappyBirdGame
Make Flappy Bird game from scratch in simple steps.
A lot of things like bird position, pipe position, pipe-velocity, gap between pipes are calculated using basic mathematics.
I've used GDX api for the app developemnt which makes things pretty easy for the game development.
Steps:-
1. Visit https://libgdx.badlogicgames.com/ and download the .jar file.
2. Select the project name and android studio for the setup.
3. Select submit and the rest things will be taken care by GDX jar file.
4. Now open the project with the android studio.
5. Add the bird, pipe images(In this repo it's taken care.)
6. Now open FlappyBird.java file from the core --> java --> com.(username).flappybird --> FlappyBird.
7. Now you will be mostly focused on create() & render() method.
8. render() method is called again & again within milliseconds and you can define texture(images) which are loader in order.
Note:- While compiling first i.e. run in physical or avd device please make sure you are connected to internet as it downloads some dependencies based on gradle.

# Known Bugs:-
There are certain bugs like gap between screen(top, bottom) and pipe. The reason the image that I've used for the pipe does not have proper height.
