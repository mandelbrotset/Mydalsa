package com.mydalsa.myflaxa.states;

import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mydalsa.myflaxa.MyFlaxaGame;
import com.mydalsa.myflaxa.entities.Sprite;

public class GameState extends State {
	public static final float PPM = 600f;
	public static final float GRAVITY = -9.82f;

	public static final float BIRD_WEIGHT = 0.05f;
	public static final float BIRD_HEIGHT = 0.05f;
	public static final float BIRD_WIDTH = 0.05f;

	public static final float JUMP_VELOCITY = 1.7f;
	public static final float STATIC_VELOCITY = 0.5f;
	public static final float BIRD_GRAVITY_SCALE = 0.5f;

	private boolean goingDown;
	private boolean goingUp;
	private boolean goingLeft;
	private boolean goingRight;

	private SpriteBatch batch;

	private boolean debug = true;
	private World world;
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera cam;
	private Sprite player;
	private Matrix4 debugMatrix;
	private Body body;
	private OrthogonalTiledMapRenderer tmr;
	private TiledMap map;
	private float middle;

	private float lowest;

	private Vector3 eye;
	private int zoom;
	private boolean firstKey;
	
	private boolean isDead;

	private float birdVelocity;

	public GameState(MyFlaxaGame game) {
		super(game);
		isDead = false;
		birdVelocity = 0;
		zoom = 1;

		goingDown = false;
		goingUp = false;
		goingRight = true;
		firstKey = true;
		batch = game.getSpriteBatch();
		cam = game.getCamera();

		// set up box2d stuff
		world = new World(new Vector2(0, GRAVITY), true);

		b2dr = new Box2DDebugRenderer();

		// create tiles
		loadTiles();

		// create player
		createPlayer();

		createGround();

		world.setContactListener(new ContactListener() {

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {

			}

			@Override
			public void endContact(Contact contact) {
				// System.out.println("End contact" +
				// body.getLinearVelocity().x);
			}

			@Override
			public void beginContact(Contact contact) {
				isDead = true;
				/*
				// System.out.println("Begin contact" +
				// body.getLinearVelocity().x);
				if (contact.getFixtureA().isSensor()) {
					birdVelocity = -birdVelocity;
					System.out.println("SENSOR");
				}

				if (contact.getFixtureB().isSensor()) {
					birdVelocity = -birdVelocity;
					System.out.println("SENSOR");
				} */
			}
		});

		// set up debug matrix
		debugMatrix = new Matrix4(cam.combined);
		debugMatrix.scl(PPM);

	}

	private void createGround() {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();

		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(new Vector2(0, 0));
		ChainShape shape = new ChainShape();
		Vector2[] vs = { new Vector2(-100.0f, lowest), new Vector2(100, lowest) };
		shape.createChain(vs);
		fixtureDef.shape = shape;
		fixtureDef.friction = 0.0f;
		world.createBody(bodyDef).createFixture(fixtureDef);
		shape.dispose();

	}

	private void loadTiles() {
		map = new TmxMapLoader().load("res/myflaxa.tmx");
		tmr = new OrthogonalTiledMapRenderer(map, 1 / PPM);
		Iterator<MapLayer> i = map.getLayers().iterator();
		while (i.hasNext()) {
			createLayer((TiledMapTileLayer) i.next());
		}
	}

	private void createLayer(TiledMapTileLayer layer) {
		float tileHeight = layer.getTileHeight();
		float tileWidth = layer.getTileWidth();
		middle = tileHeight * layer.getHeight() / PPM / 2;
		lowest = Float.MAX_VALUE;
		for (int x = 0; x < layer.getWidth(); x++)
			for (int y = 0; y < layer.getHeight(); y++) {
				Cell cell = layer.getCell(x, y);

				// check if cell exists
				if (cell == null)
					continue;
				if (cell.getTile() == null)
					continue;

				if (y < lowest)
					lowest = y;

				BodyDef bdef = new BodyDef();
				// create a body + fixture from cell
				bdef.type = BodyType.StaticBody;

				bdef.position.set((x + 0.5f) * tileWidth / PPM, (y + 0.5f)
						* tileHeight / PPM);
				PolygonShape ps = new PolygonShape();
				ps.setAsBox((tileWidth) / 2 / PPM, (tileHeight) / 2 / PPM);
				FixtureDef fdef = new FixtureDef();
				fdef.friction = 0;
				fdef.shape = ps;
				world.createBody(bdef).createFixture(fdef);
				ps.dispose();

			}
	}

	private void createPlayer() {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtDef = new FixtureDef();

		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(0, middle));
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(BIRD_WIDTH / 2, BIRD_HEIGHT / 2);
		fixtDef.shape = shape;
		fixtDef.restitution = 0.8f;
		fixtDef.density = BIRD_WEIGHT / (BIRD_HEIGHT * BIRD_WEIGHT);
		bodyDef.active = true;

		body = world.createBody(bodyDef);

		body.setGravityScale(0f);
		body.createFixture(fixtDef);

		fixtDef.isSensor = true;
		shape.setAsBox(BIRD_WIDTH / 5, BIRD_HEIGHT / 12, new Vector2(
				BIRD_WIDTH / 2, 0), 0);
		fixtDef.shape = shape;
		body.createFixture(fixtDef).setUserData("beakRight");
		
		shape.setAsBox(BIRD_WIDTH / 5, BIRD_HEIGHT / 12, new Vector2(-BIRD_WIDTH / 2,0),0);
		fixtDef.shape = shape;
		body.createFixture(fixtDef).setUserData("beakLeft");

		
		body.setLinearVelocity(birdVelocity, 0);
		
		player = new Sprite(body);
		shape.dispose();
	}

	@Override
	public void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
			zoom++;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS)) {
			if (zoom > 0) {
				zoom--;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			eye.add(-10f, 0f, 0.0f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			eye.add(10f, 0f, 0.0f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			eye.add(0f, 10f, 0.0f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			eye.add(0f, -10f, 0.0f);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
			birdVelocity = -birdVelocity;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if (firstKey) {
				firstKey = false;
				body.setGravityScale(BIRD_GRAVITY_SCALE);
				birdVelocity = STATIC_VELOCITY;
			}
			body.setLinearVelocity(body.getLinearVelocity().x, JUMP_VELOCITY);
			// body.applyLinearImpulse(0, JUMP_VELOCITY, body.getPosition().x,
			// body.getPosition().y, true);
			// body.applyForceToCenter(0.0f, JUMP_FORCE, true);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
			body.applyForceToCenter(2.0f, 0.0f, true);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			body.applyForceToCenter(-2.0f, 0.0f, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			System.exit(0);
		}
	}

	@Override
	public void update(float dt) {
		
		if(isDead){
			
			body.setTransform(new Vector2(0, middle), 0);
			birdVelocity = STATIC_VELOCITY;
			body.setLinearVelocity(birdVelocity, 0.0f);
			body.setAngularVelocity(0);
			isDead = false;
			return;
		}
		
		handleInput();

		if (goingRight && body.getLinearVelocity().x < -0.01) {
			goingLeft = true;
			goingRight = false;
		}
		if (goingLeft && body.getLinearVelocity().x > 0.01) {
			goingLeft = false;
			goingRight = true;
		}

		if (body.getLinearVelocity().y < -0.01 && !goingDown) {
			goingDown = true;
			goingUp = false;
			if(goingRight)
				body.setAngularVelocity(-7f);
			if(goingLeft)
				body.setAngularVelocity(7f);

		} else if (body.getLinearVelocity().y > 0.01 && !goingUp) {
			goingUp = true;
			goingDown = false;
			if(goingRight)
				body.setAngularVelocity(7f);
			if(goingLeft)
				body.setAngularVelocity(-7f);
		}
		if (goingRight && goingUp && body.getAngle() > Math.PI / 4) {
			body.setAngularVelocity(0);
			body.setTransform(body.getPosition().x, body.getPosition().y,
					(float) (Math.PI / 4f));

		}
		if (goingRight && goingDown && body.getAngle() < -Math.PI / 4) {
			body.setAngularVelocity(0);
			body.setTransform(body.getPosition().x, body.getPosition().y,
					(float) (-Math.PI / 4f));
		}
		
		if (goingLeft && goingUp && body.getAngle() < -Math.PI / 4) {
			body.setAngularVelocity(0);
			body.setTransform(body.getPosition().x, body.getPosition().y,
					(float) (-Math.PI / 4f));

		}
		if (goingLeft && goingDown && body.getAngle() > Math.PI / 4) {
			body.setAngularVelocity(0);
			body.setTransform(body.getPosition().x, body.getPosition().y,
					(float) (Math.PI / 4f));
		}


		if (birdVelocity != 0.0f) {
			body.setLinearVelocity(birdVelocity, body.getLinearVelocity().y);
		}
		world.step(dt, 6, 2);
	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		eye = new Vector3(body.getPosition().x * PPM, body.getPosition().y
				* PPM, 0f);

		cam.position.set(eye);
		cam.zoom = zoom;
		cam.update();

		// draw box2dworld
		if (true) {
			b2dr.render(world, cam.combined.scl(PPM));
		}
		tmr.setView(cam.combined, 0, 0, MyFlaxaGame.V_WIDTH,
				MyFlaxaGame.V_HEIGHT);
		tmr.render();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
