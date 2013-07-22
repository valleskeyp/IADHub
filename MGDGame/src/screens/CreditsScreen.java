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

public class CreditsScreen implements Screen, InputProcessor {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite, backButton;
	
	@Override
	public void show() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
		
		// background    ----  Don't forget to add sprite to draw batch when done  ***AND ALSO DISPOSE***
		texture = new Texture(Gdx.files.internal("data/creditsScreen.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		TextureRegion region = new TextureRegion(texture, 0, 0, 800, 480);

		sprite = new Sprite(region);
		sprite.setSize(1f, 1f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);

		texture = new Texture(Gdx.files.internal("data/backButton.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		region = new TextureRegion(texture, 0, 0, 64, 64);

		backButton = new Sprite(region);
		backButton.setSize(.096f, .096f);
		backButton.setOrigin(backButton.getWidth()/2, backButton.getHeight()/2);
		backButton.setPosition(-.5f, (h/w)/2 - .096f);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		sprite.draw(batch);
		backButton.draw(batch);
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
        if (backButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y)) {
        	((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
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
