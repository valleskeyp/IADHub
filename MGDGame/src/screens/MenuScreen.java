package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;

public class MenuScreen implements Screen, InputProcessor {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite, playButton, instructionButton, creditsButton;
	
	@Override
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
		
		// background    ----  Don't forget to add sprite to draw batch when done  ***AND ALSO DISPOSE***
		texture = new Texture(Gdx.files.internal("data/splash.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 800, 480);
		
		sprite = new Sprite(region);
		sprite.setSize(1f, 1f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		texture = new Texture(Gdx.files.internal("data/playButton.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(texture, 0, 0, 256, 64);
		
		playButton = new Sprite(region);
		playButton.setSize(.3328f, .0832f);
		playButton.setOrigin(playButton.getWidth()/2, playButton.getHeight()/2);
		playButton.setPosition(.2f, -.04f);
		
		texture = new Texture(Gdx.files.internal("data/instructionsButton.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(texture, 0, 0, 256, 64);
		
		instructionButton = new Sprite(region);
		instructionButton.setSize(.3328f, .0832f);
		instructionButton.setOrigin(instructionButton.getWidth()/2, instructionButton.getHeight()/2);
		instructionButton.setPosition(.15f, -.14f);
		
		texture = new Texture(Gdx.files.internal("data/creditsButton.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		region = new TextureRegion(texture, 0, 0, 256, 64);
		
		creditsButton = new Sprite(region);
		creditsButton.setSize(.3328f, .0832f);
		creditsButton.setOrigin(creditsButton.getWidth()/2, creditsButton.getHeight()/2);
		creditsButton.setPosition(.1f, -.24f);
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		sprite.draw(batch);
		playButton.draw(batch);
		instructionButton.draw(batch);
		creditsButton.draw(batch);
		batch.end();	
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 touchPos = new Vector2();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY());

        Ray cameraRay = camera.getPickRay(touchPos.x, touchPos.y);
        if (playButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y)) {
        	((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
		} else if (instructionButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y)) {
			((Game) Gdx.app.getApplicationListener()).setScreen(new InstructionScreen());
		} else if (creditsButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y)) {
			((Game) Gdx.app.getApplicationListener()).setScreen(new CreditsScreen());
		}
		return true;
	}
	
	
	
	
	
	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
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
}
