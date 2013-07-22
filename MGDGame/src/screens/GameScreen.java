package screens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.valleskeyp.mgdgame.Card;

public class GameScreen implements Screen, InputProcessor {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite, shadow, pause, play, backButton, gameOverText, gameWinText, hotStreak, strike1, strike2, correct1, correct2;
	private Sound correct, incorrect;
	private Music bgMusic;
	private String answer = "";
	private Card card1;
	private Card card2;
	private Boolean isPlaying = true;
	private Boolean showingCards = true;
	private int wrongGuesses = 3;
	private Boolean gameOver = false;
	private float timer = 0;
	private int guessedInARow = 0;
	
	private int gameLevel = 0;
	private int timeDifficulty = 2000;
	
	public Array<HashMap<String, Float>> coord = new Array<HashMap<String, Float>>();
	public Array<Card> cards;
	
	@Override
	public void show() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		Gdx.input.setInputProcessor(this);
		
		
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("data/BGMusic.mp3"));
		bgMusic.setVolume(0.15f);
		bgMusic.play();
		bgMusic.setLooping(true);
		
		correct = Gdx.audio.newSound(Gdx.files.internal("data/correct.mp3"));
		incorrect = Gdx.audio.newSound(Gdx.files.internal("data/incorrect.wav"));
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		
		
		// background    ----  Don't forget to add sprite to draw batch when done
		texture = new Texture(Gdx.files.internal("data/bgGDX.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 800, 480);
		
		sprite = new Sprite(region);
		sprite.setSize(1f, 1f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		// bg shadow
		texture = new Texture(Gdx.files.internal("data/shadow.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(texture, 0, 0, 800, 480);
		
		shadow = new Sprite(region);
			shadow.setSize(1f, 1f * shadow.getHeight() / shadow.getWidth());
		shadow.setOrigin(shadow.getWidth()/2, shadow.getHeight()/2);
		shadow.setPosition(-shadow.getWidth()/2, -shadow.getHeight()/2);
		
		// pause button
		texture = new Texture(Gdx.files.internal("data/pause.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(texture, 0, 0, 32, 32);
		
		pause = new Sprite(region);
		pause.setSize(.064f, .064f);
		pause.setOrigin(pause.getWidth()/2, pause.getHeight()/2);
		pause.setPosition(0.424f, 0.22f);
		
		// play button
		texture = new Texture(Gdx.files.internal("data/play.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		region = new TextureRegion(texture, 0, 0, 32, 32);

		play = new Sprite(region);
		play.setSize(.064f, .064f);
		play.setOrigin(play.getWidth()/2, play.getHeight()/2);
		play.setPosition(0.424f, 0.22f);
		
		// game over text
		texture = new Texture(Gdx.files.internal("data/gameOver.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		region = new TextureRegion(texture, 0, 0, 256, 128);

		gameOverText = new Sprite(region);
		gameOverText.setSize(.256f, .128f);
		gameOverText.setOrigin(gameOverText.getWidth()/2, gameOverText.getHeight()/2);
		gameOverText.setPosition(-.128f, -.064f);
		
		// game win text
		texture = new Texture(Gdx.files.internal("data/gameWin.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		region = new TextureRegion(texture, 0, 0, 256, 128);

		gameWinText = new Sprite(region);
		gameWinText.setSize(.256f, .128f);
		gameWinText.setOrigin(gameWinText.getWidth()/2, gameWinText.getHeight()/2);
		gameWinText.setPosition(-.128f, -.064f);
		
		// hot streak text
		texture = new Texture(Gdx.files.internal("data/hotStreak.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		region = new TextureRegion(texture, 0, 0, 256, 75);

		hotStreak = new Sprite(region);
		hotStreak.setSize(.256f, .078f);
		hotStreak.setOrigin(hotStreak.getWidth()/2, hotStreak.getHeight()/2);
		hotStreak.setPosition(-.02f, .2f);

		// wrong card
		texture = new Texture(Gdx.files.internal("data/wrong.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(texture, 0, 0, 80, 115);

		strike1 = new Sprite(region);
		strike1.setSize(.08f, .115f);
		strike1.setOrigin(strike1.getWidth()/2, strike1.getHeight()/2);
		strike1.setPosition(-.45f, .08f);
		
		region = new TextureRegion(texture, 0, 0, 80, 115);

		strike2 = new Sprite(region);
		strike2.setSize(.08f, .115f);
		strike2.setOrigin(strike2.getWidth()/2, strike2.getHeight()/2);
		strike2.setPosition(-.45f, -.07f);
		
		// correct card
		texture = new Texture(Gdx.files.internal("data/correctCard.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		region = new TextureRegion(texture, 0, 0, 90, 125);

		correct1 = new Sprite(region);
		correct1.setSize(.08f, .115f);
		correct1.setOrigin(correct1.getWidth()/2, correct1.getHeight()/2);
		correct1.setPosition(-.02f, .18f);

		region = new TextureRegion(texture, 0, 0, 90, 125);

		correct2 = new Sprite(region);
		correct2.setSize(.08f, .115f);
		correct2.setOrigin(correct2.getWidth()/2, correct2.getHeight()/2);
		correct2.setPosition(.08f, .18f);

		// back button
		texture = new Texture(Gdx.files.internal("data/backButton.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		region = new TextureRegion(texture, 0, 0, 64, 64);

		backButton = new Sprite(region);
		backButton.setSize(.096f, .096f);
		backButton.setOrigin(backButton.getWidth()/2, backButton.getHeight()/2);
		backButton.setPosition(-.5f, (h/w)/2 - .096f);
		
//// CARD CREATION /////////////////////
		
		restart(); // uses array of coordinates to make an array of Card objects -- fresh start
		displayCards();
	}

	private void restart() { 
		int i = 0;
		int length = 0;

		if (gameLevel == 0) {
			length = 12;
		} else if (gameLevel == 1) {
			length = 16;
		} else if (gameLevel == 2) {
			length = 20;
		} else {
			length = 24;
		}
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
	    for(int x = 0; x < length; x++) {
	    	numbers.add(x);
	    }
	    Collections.shuffle(numbers); // Randomizes the numbers array so each card gets a letter based on that number
	    
		setCoordinates();
		cards = new Array<Card>();
		for (HashMap<String, Float> hm : coord) {
			String str = "";
			if (numbers.get(i) < 2) {
				str = "a";
			} else if (numbers.get(i) < 4 && numbers.get(i) >= 2) {
				str = "b";
			} else if (numbers.get(i) < 6 && numbers.get(i) >= 4) {
				str = "c";
			} else if (numbers.get(i) < 8 && numbers.get(i) >= 6) {
				str = "d";
			} else if (numbers.get(i) < 10 && numbers.get(i) >= 8) {
				str = "e";
			} else if (numbers.get(i) < 12 && numbers.get(i) >= 10) {
				str = "f";
			} else if (numbers.get(i) < 14 && numbers.get(i) >=12) {
				str = "g";
			} else if (numbers.get(i) < 16 && numbers.get(i) >=14) {
				str = "h";
			} else if (numbers.get(i) < 18 && numbers.get(i) >=16) {
				str = "i";
			} else if (numbers.get(i) < 20 && numbers.get(i) >=18) {
				str = "j";
			} else if (numbers.get(i) < 22 && numbers.get(i) >=20) {
				str = "k";
			} else if (numbers.get(i) < 24 && numbers.get(i) >=22) {
				str = "l";
			}
			Card card = new Card(hm.get("x"), hm.get("y"), str);
			cards.add(card);
			i++;
		}
	}

	private void displayCards() {
		showingCards = true;
		for (Card card : cards) {
			card.flipCard();
		}
	}

	@Override
	public void dispose() {  //     DON'T FORGET TO DISPOSE OF EVERYTHING POSSIBLE!
		batch.dispose();
		texture.dispose();
		correct.dispose();
		incorrect.dispose();
		bgMusic.dispose();
		for (Card card : cards) {
			card.textureLetter.dispose();
			card.textureMark.dispose();
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		float dt = Gdx.graphics.getDeltaTime();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);
		if (cards.size > 0) {
			if (showingCards) {
				timer += dt;
				if (timer > 4) {
					for (Card card2 : cards) {
						card2.flipCard();
					}
					showingCards = false;
					timer = 0;
				}
			}
			if (!gameOver) { //																		 GAME PLAY SCREEN
				if (guessedInARow == 1) { //                DRAW CURRENT HOT STREAK
					correct1.draw(batch);
				} else if (guessedInARow == 2) {
					correct1.draw(batch);
					correct2.draw(batch);
				} else if (guessedInARow >= 3) {
					hotStreak.draw(batch);
				}
				backButton.draw(batch);
				if (isPlaying) {//										WHILE GAME PLAYING
					pause.draw(batch);

					if(answer.equals("correct")) { //         			   --ON RIGHT ANSWER
						cardMove(dt);
						if (Intersector.overlaps(card1.card.getBoundingRectangle(), card2.card.getBoundingRectangle())) {
							answer = "";
							for (Card card : cards) {
								if (card.letter.equals(card1.letter)) {
									cards.removeValue(card, true);
								}
							}
							for (Card card : cards) {
								if (card.letter.equals(card1.letter)) {
									cards.removeValue(card, true);
								}
							}
							card1 = null;
							card2 = null;
							guessedInARow += 1;
						}
					}

					switch (wrongGuesses) { // 						      	 ----DISPLAY WRONG GUESS ALLOWANCE
					case 2:
						//draw 1 wrong card
						strike1.draw(batch);
						break;
					case 3:
						//draw 2 wrong cards
						strike1.draw(batch);
						strike2.draw(batch);
						break;
					default:
						break;
					}

					for (Card card : cards) {
						card.draw(batch, dt);
					}
					batch.end();
				} else {  //										  WHILE GAME PAUSED
					switch (wrongGuesses) { // 							----DISPLAY WRONG GUESS ALLOWANCE
					case 2:
						//draw 1 wrong card
						strike1.draw(batch);
						break;
					case 3:
						//draw 2 wrong cards
						strike1.draw(batch);
						strike2.draw(batch);
						break;
					default:
						break;
					}

					for (Card card : cards) {
						card.draw(batch, dt);
					}
					shadow.draw(batch);
					play.draw(batch);
					batch.end();
				}
			} else {  //                   							  WHILE GAME OVER [LOSS]
				timer += dt;
				shadow.draw(batch);
				gameOverText.draw(batch);
				batch.end();
				if (timer >= 3) {
					wrongGuesses = 3;
					guessedInARow = 0;
					gameLevel = 0;
					restart();
					timer = 0;
					gameOver = false;
					displayCards();
				} 
			}
		} else {  //												WHILE GAME OVER [WIN]
			timer += dt;
			
			shadow.draw(batch);
			gameWinText.draw(batch);
			batch.end();
			if (timer >= 3) {
				wrongGuesses = 3;
				guessedInARow = 0;
				gameLevel += 1;
				restart();
				timer = 0;
				displayCards();
			} 
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		if( isPlaying )
        {
            isPlaying = false;
            bgMusic.pause();
        }
        else
        {
            isPlaying = true;
            bgMusic.play();
        }
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 touchPos = new Vector2();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY());

        Ray cameraRay = camera.getPickRay(touchPos.x, touchPos.y);
        if (isPlaying && !gameOver && cards.size > 0) {  // only act when game is playing
        	for (Card card : cards) {
				Sprite sp = card.spriteReturn();
				if (sp.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y)) {
					if (answer.equals("")) {
						if (!card.isFlipped == true) {
							String str = card.letter;
							if (str.equals("a")) {
								card.flipCard();
								if (card1 == null) {
									card1 = card;
								} else {
									card2 = card;
								}
							} else if (str.equals("b")) {
								card.flipCard();
								if (card1 == null) {
									card1 = card;
								} else {
									card2 = card;
								}
							} else if (str.equals("c")) {
								card.flipCard();
								if (card1 == null) {
									card1 = card;
								} else {
									card2 = card;
								}
							} else if (str.equals("d")) {
								card.flipCard();
								if (card1 == null) {
									card1 = card;
								} else {
									card2 = card;
								}
							} else if (str.equals("e")) {
								card.flipCard();
								if (card1 == null) {
									card1 = card;
								} else {
									card2 = card;
								}
							} else if (str.equals("f")) {
								card.flipCard();
								if (card1 == null) {
									card1 = card;
								} else {
									card2 = card;
								}
							} else if (str.equals("g")) {
								card.flipCard();
								if (card1 == null) {
									card1 = card;
								} else {
									card2 = card;
								}
							} else if (str.equals("h")) {
								card.flipCard();
								if (card1 == null) {
									card1 = card;
								} else {
									card2 = card;
								}
							} else if (str.equals("i")) {
								card.flipCard();
								if (card1 == null) {
									card1 = card;
								} else {
									card2 = card;
								}
							} else if (str.equals("j")) {
								card.flipCard();
								if (card1 == null) {
									card1 = card;
								} else {
									card2 = card;
								}
							} else if (str.equals("k")) {
								card.flipCard();
								if (card1 == null) {
									card1 = card;
								} else {
									card2 = card;
								}
							} else if (str.equals("l")) {
								card.flipCard();
								if (card1 == null) {
									card1 = card;
								} else {
									card2 = card;
								}
							}
						}
						
						if (card2 != null) { //   		TWO CARDS PICKED
							if (card1.letter.equals(card2.letter)) { // 		-- RIGHT ANSWER
								correct.play(1.0f);
								answer = "correct";
								// now interpolate card movement in batch and reset after
							} else { //							-- WRONG ANSWER
								incorrect.play(1.0f);
								answer = "wrong";
								wrongGuesses -= 1;
								guessedInARow = 0;
								if (wrongGuesses == 0) {
									answer = "";
									gameOver = true;
									gameLevel = 0;
									timeDifficulty = 2000;
									restart();
									card1 = null;
									card2 = null;
								} else {
									Timer timer = new Timer();
									timer.schedule(new TimerTask() { 
										// let the incorrect card display then hide and return to neutral state
										public void run() {
											answer = "";
											card1.flipCard();
											card2.flipCard();
											card1 = null;
											card2 = null;
										} 
									}, timeDifficulty);
								}
							}
						}
						
					}
				}
			}
        	if (backButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y)) {
        		bgMusic.stop();
            	((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
    		}
        }
        if (!gameOver && cards.size > 0) {
        	if (pause.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y) || play.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y)) {
    			pause();  // pause/resume game
    		}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public void cardMove(float _dt) {
		// change this so it sends the Card objects toward each other
		
		if (card1.card.getX() <= 0 && card1.card.getY() <= 0) {
			card1.card.setPosition(card1.card.getX() + (.3f * _dt), card1.card.getY() + (.18f * _dt));
		} else if (card1.card.getX() >= 0 && card1.card.getY() <= 0) {
			card1.card.setPosition(card1.card.getX() - (.3f * _dt), card1.card.getY() + (.18f * _dt));
		} else if (card1.card.getX() >= 0 && card1.card.getY() >= 0){
			card1.card.setPosition(card1.card.getX() - (.3f * _dt), card1.card.getY() - (.18f * _dt));
		} else if (card1.card.getX() <= 0 && card1.card.getY() >= 0){
			card1.card.setPosition(card1.card.getX() + (.3f * _dt), card1.card.getY() - (.18f * _dt));
		}
		if (card2.card.getX() <= 0 && card2.card.getY() <= 0) {
			card2.card.setPosition(card2.card.getX() + (.3f * _dt), card2.card.getY() + (.18f * _dt));
		} else if (card2.card.getX() >= 0 && card2.card.getY() <= 0) {
			card2.card.setPosition(card2.card.getX() - (.3f * _dt), card2.card.getY() + (.18f * _dt));
		} else if (card2.card.getX() >= 0 && card2.card.getY() >= 0){
			card2.card.setPosition(card2.card.getX() - (.3f * _dt), card2.card.getY() - (.18f * _dt));
		} else if (card2.card.getX() <= 0 && card2.card.getY() >= 0){
			card2.card.setPosition(card2.card.getX() + (.3f * _dt), card2.card.getY() - (.18f * _dt));
		}
	}
	
	private void setCoordinates() { // INITIAL LEVEL 0 SETUP
		coord.clear();
		HashMap<String, Float> hm = new HashMap<String, Float>(); // row 1
		hm.put("x", -.33f);
		hm.put("y", .06f);
		coord.add(hm);

		hm = new HashMap<String, Float>();
		hm.put("x", -.24f);
		hm.put("y", .06f);
		coord.add(hm);

		hm = new HashMap<String, Float>();
		hm.put("x", -.15f);
		hm.put("y", .06f);
		coord.add(hm);

		hm = new HashMap<String, Float>();
		hm.put("x", -.06f);
		hm.put("y", .06f);
		coord.add(hm);

		hm = new HashMap<String, Float>(); // row 2
		hm.put("x", -.33f);
		hm.put("y", -.05f);
		coord.add(hm);

		hm = new HashMap<String, Float>();
		hm.put("x", -.24f);
		hm.put("y", -.05f);
		coord.add(hm);

		hm = new HashMap<String, Float>();
		hm.put("x", -.15f);
		hm.put("y", -.05f);
		coord.add(hm);

		hm = new HashMap<String, Float>();
		hm.put("x", -.06f);
		hm.put("y", -.05f);
		coord.add(hm);

		hm = new HashMap<String, Float>(); // row 3
		hm.put("x", -.33f);
		hm.put("y", -.16f);
		coord.add(hm);

		hm = new HashMap<String, Float>();
		hm.put("x", -.24f);
		hm.put("y", -.16f);
		coord.add(hm);

		hm = new HashMap<String, Float>();
		hm.put("x", -.15f);
		hm.put("y", -.16f);
		coord.add(hm);

		hm = new HashMap<String, Float>();
		hm.put("x", -.06f);
		hm.put("y", -.16f);
		coord.add(hm);
		
		if (gameLevel > 0) { // LEVEL ONE DIFFICULTY -- G & H cards
			hm = new HashMap<String, Float>(); // row 4
			hm.put("x", -.33f);
			hm.put("y", -.27f);
			coord.add(hm);

			hm = new HashMap<String, Float>();
			hm.put("x", -.24f);
			hm.put("y", -.27f);
			coord.add(hm);

			hm = new HashMap<String, Float>();
			hm.put("x", -.15f);
			hm.put("y", -.27f);
			coord.add(hm);

			hm = new HashMap<String, Float>();
			hm.put("x", -.06f);
			hm.put("y", -.27f);
			coord.add(hm);
		}
		if (gameLevel > 1) { // LEVEL TWO DIFFICULTY -- + I & J cards
			hm = new HashMap<String, Float>(); // column 4
			hm.put("x", .03f);
			hm.put("y", .06f);
			coord.add(hm);

			hm = new HashMap<String, Float>();
			hm.put("x", .03f);
			hm.put("y", -.05f);
			coord.add(hm);

			hm = new HashMap<String, Float>();
			hm.put("x", .03f);
			hm.put("y", -.16f);
			coord.add(hm);

			hm = new HashMap<String, Float>();
			hm.put("x", .03f);
			hm.put("y", -.27f);
			coord.add(hm);
		}
		if (gameLevel > 2) { // LEVEL THREE DIFFICULTY -- + K & L cards
			hm = new HashMap<String, Float>(); // column 4
			hm.put("x", .12f);
			hm.put("y", .06f);
			coord.add(hm);

			hm = new HashMap<String, Float>();
			hm.put("x", .12f);
			hm.put("y", -.05f);
			coord.add(hm);

			hm = new HashMap<String, Float>();
			hm.put("x", .12f);
			hm.put("y", -.16f);
			coord.add(hm);

			hm = new HashMap<String, Float>();
			hm.put("x", .12f);
			hm.put("y", -.27f);
			coord.add(hm);
		}
		if (gameLevel > 3) { // ADDITIONAL DIFFICULTY -- increase speed
			if (timeDifficulty != 1000) {
				timeDifficulty -= 200;
			}
		}
	}

	@Override
	public void hide() {
		// Called when this screen is no longer the current Screen for a game.
	}
}