package com.nikhil.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	//ShapeRenderer shapeRenderer;
	Random randomGenerator;

	Texture background;

	Texture[] birds;
	Circle birdCircle;
	Rectangle[] topTubeRectangles;
	Rectangle[] bottomTubeRectangles;

	int flapState, gameState;
	float birdY, velocity, gravity, antiGravity;

	Texture topTube, bottomTube;

	float gapBetweenTubes, maxTubeOffset, tubeVelocity;

	int numOfTubes = 4;
	float[] tubeX = new float[numOfTubes];
	float[] tubeOffset = new float[numOfTubes];
	float distanceBetweenTubes;

	int scoringTube, score;
	BitmapFont font;

	Texture gameOver;

	@Override
	public void create () {
		//HyperParameters
		gravity = 2.0f;
		antiGravity = 30.0f;
		tubeVelocity = 4;
		gapBetweenTubes = 400;

		font = new BitmapFont();
		font.setColor(Color.BROWN);
		font.getData().setScale(10);

		batch = new SpriteBatch();

		//shapeRenderer = new ShapeRenderer();

		topTubeRectangles = new Rectangle[numOfTubes];
		bottomTubeRectangles = new Rectangle[numOfTubes];

		background = new Texture("bg.png");

		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");


		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		maxTubeOffset = Gdx.graphics.getHeight()/2 - gapBetweenTubes/2 - 100;
		randomGenerator = new Random();

		distanceBetweenTubes = Gdx.graphics.getWidth()*(3.0f/4.0f);

		gameOver = new Texture("badlogic.jpg");
	}

	public void initCode(){
		//Start Parameters
		scoringTube = 0;
		score = 0;
		velocity = 0;
		gameState = 0;
		flapState = 0;

		birdCircle = new Circle();
		birdY = (Gdx.graphics.getHeight()/2)-(birds[flapState].getHeight()/2);

		for(int i=0;i<numOfTubes;i++){
			tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f)*(Gdx.graphics.getHeight()-gapBetweenTubes-200);
			tubeX[i] = Gdx.graphics.getWidth()/2 - topTube.getWidth()/2 + Gdx.graphics.getWidth() + i*distanceBetweenTubes;
			topTubeRectangles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();
		}

	}
	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();

		if(gameState == 0){
			initCode();
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		}
		else if (gameState == 1){
			if(tubeX[scoringTube] < Gdx.graphics.getWidth()/2){
				score += 1;
				scoringTube = (scoringTube+1)%numOfTubes;
				Gdx.app.log("Score", Integer.toString(score));
			}
			batch.begin();
			for(int i=0;i<numOfTubes;i++) {
				if(tubeX[i] < -topTube.getWidth()){
					tubeX[i] += numOfTubes*distanceBetweenTubes;
					tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f)*(Gdx.graphics.getHeight()-gapBetweenTubes-200);
				}
				tubeX[i] -= tubeVelocity;
				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gapBetweenTubes / 2 + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gapBetweenTubes / 2 - bottomTube.getHeight() + tubeOffset[i]);

				topTubeRectangles[i].set(tubeX[i], Gdx.graphics.getHeight() / 2 + gapBetweenTubes / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
				bottomTubeRectangles[i].set(tubeX[i], Gdx.graphics.getHeight() / 2 - gapBetweenTubes / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
			}
			batch.end();
			if(birdY > 0 || velocity<0) {
				velocity += gravity;
				birdY -= velocity;
			}
			if (Gdx.input.justTouched()) {
				Gdx.app.log("Touched", "Yep");
				velocity -= antiGravity;
			}

			//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			//shapeRenderer.setColor(Color.RED);
			birdCircle.set(Gdx.graphics.getWidth()/2, birdY+birds[flapState].getHeight()/2, birds[flapState].getWidth()/2);
			//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
			for(int i=0;i<numOfTubes;i++){
				//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gapBetweenTubes / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
				//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gapBetweenTubes / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());

				if(Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])){
					Gdx.app.log("Collison", "Yes!");
					gameState = 2;
				}
			}
			//shapeRenderer.end();
		}
		else if(gameState == 2){
			batch.begin();
			batch.draw(gameOver, Gdx.graphics.getWidth()/2-gameOver.getWidth()/2, Gdx.graphics.getHeight()/2 - gameOver.getHeight()/2);
			batch.end();
			if(Gdx.input.justTouched())
				initCode();
		}

		//Bird Rendering
		batch.begin();
		batch.draw(birds[flapState], (Gdx.graphics.getWidth() / 2) - (birds[flapState].getWidth() / 2), birdY);
		font.draw(batch, String.valueOf(score), 100, 200);
		batch.end();

		flapState = (flapState+1)%2;
	}
}
