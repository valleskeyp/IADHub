package com.valleskeyp.mgdgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Card {
	public Sprite card;
	public Texture textureMark, textureLetter;
	public String letter;
	public Boolean isFlipped = false;
	private Animation animation;
	private float time = 999;
	public float xCoord;
	public float yCoord;

	
	public Card(float x, float y, String l) {
		textureMark = new Texture(Gdx.files.internal("data/card.png"));
		textureMark.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		xCoord = x;
		yCoord = y;
		
		textureLetter = new Texture(Gdx.files.internal("data/" + l + "Card.png"));
		textureLetter.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		letter = l;
		
		TextureRegion region = new TextureRegion(textureMark, 0, 0, 80, 115);
		card = new Sprite(region);
		card.setSize(.08f, .115f);
		card.setOrigin(card.getWidth()/2, card.getHeight()/2);
		card.setPosition(x, y);
		
		animation = new Animation(1/3f, 
				new TextureRegion(new Texture(Gdx.files.internal("data/cardFlip1.png")), 0, 0, 80, 115),
				new TextureRegion(new Texture(Gdx.files.internal("data/cardFlip2.png")), 0, 0, 80, 115),
				new TextureRegion(new Texture(Gdx.files.internal("data/cardFlip3.png")), 0, 0, 80, 115)
		);
	}
	
	public void update() {
		
	}
	
	public void draw(SpriteBatch batch, float dt) {
		if (animation.isAnimationFinished(time)) {
			card.draw(batch);
		} else {
			xCoord = card.getX();
			yCoord = card.getY();
			
			if (isFlipped) {
				animation.setPlayMode(Animation.NORMAL);
				batch.draw(animation.getKeyFrame(time += dt*3), xCoord, yCoord, .08f, .115f);
			} else if (!isFlipped) {
				animation.setPlayMode(Animation.REVERSED);
				batch.draw(animation.getKeyFrame(time += dt*3), xCoord, yCoord, .08f, .115f);
			}
		}
		
	}
	
	public Sprite spriteReturn() {
		return card;
	}
	
	public void flipCard() {
		if (!isFlipped) {
			time = 0;
			
			TextureRegion region = new TextureRegion(textureLetter, 0, 0, 80, 115);
			card.setRegion(region);
			isFlipped = true;
		} else {
			time = 0;
			
			TextureRegion region = new TextureRegion(textureMark, 0, 0, 80, 115);
			card.setRegion(region);
			isFlipped = false;
		}
	}
	
}
