package com.mydalsa.myflaxa.states;

import java.util.Iterator;

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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mydalsa.myflaxa.MyFlaxaGame;
import com.mydalsa.myflaxa.entities.Sprite;

public class GameState extends State {
	public static final float PPM = 1000f;
	public static final float GRAVITY = -6.82f;
	
	public static final float BIRD_WEIGHT = 10;

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
	
	private Vector3 eye = new Vector3(1000000f/PPM, 1000000f/PPM, 0f);
	private int zoom = 10;

	private boolean flax_done = true;
	private boolean flax = false;

	public GameState(MyFlaxaGame game) {
		super(game);
		batch = game.getSpriteBatch();
		cam = game.getCamera();

		// set up box2d stuff
		world = new World(new Vector2(0, GRAVITY), true);

		b2dr = new Box2DDebugRenderer();

		// create player
		createPlayer();

		// create tiles
		loadTiles();

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
		Vector2[] vs = { new Vector2(-1000.0f / PPM, 0.0f),
				new Vector2(1000 / PPM, 200 / PPM) };
		shape.createChain(vs);
		fixtureDef.shape = shape;
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

		for (int x = 0; x < layer.getWidth(); x++)
			for (int y = 0; y < layer.getHeight(); y++) {
				Cell cell = layer.getCell(x, y);

				// check if cell exists
				if (cell == null)
					continue;
				if (cell.getTile() == null)
					continue;

				BodyDef bdef = new BodyDef();
				// create a body + fixture from cell
				bdef.type = BodyType.StaticBody;

				bdef.position.set((x + 0.5f) * tileWidth / PPM, (y + 0.5f)
						* tileHeight / PPM);
				PolygonShape ps = new PolygonShape();
				ps.setAsBox((tileWidth / PPM) / 2, (tileHeight / PPM) / 2);
				FixtureDef fdef = new FixtureDef();

				fdef.shape = ps;
				world.createBody(bdef).createFixture(fdef);
				ps.dispose();

			}
	}

	private void createPlayer() {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtDef = new FixtureDef();

		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(1000 / PPM, 200 / PPM));
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(100 / PPM, 100 / PPM);
		fixtDef.shape = shape;
		fixtDef.restitution = 0.5f;
		fixtDef.density = 1.0f;
		bodyDef.active = true;

		body = world.createBody(bodyDef);

		body.createFixture(fixtDef);
		
		player = new Sprite(body);
		shape.dispose();
	}

	@Override
	public void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
			zoom++;
			System.out.println(eye.x + ", " + eye.y);
		} 
		if(Gdx.input.isKeyJustPressed(Input.Keys.PLUS)){
			if(zoom > 0){
				zoom--;
			}
		} 
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			eye.add(-10f, 0f, 0.0f);
		} 
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			eye.add(10f, 0f, 0.0f);
		} 
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			eye.add(0f, 10f, 0.0f);
		} 
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			eye.add(0f, -10f, 0.0f);
		} 
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			flax = true;
			if (!flax_done) {
				body.applyForceToCenter(0.0f, 7.0f, true);
				flax_done = true;
			}
		} else {
			flax = false;
			flax_done = false;
		}
	}

	@Override
	public void update(float dt) {
		// System.out.println(body.getPosition().x + ", " +
		// body.getPosition().y);
		handleInput();
		world.step(dt, 6, 2);
	}
	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.position.set(eye);
		System.out.println(eye.x + ", " + eye.y);
		cam.zoom = zoom;
		cam.update();

		// draw box2dworld
		if (true) {
			b2dr.render(world, cam.combined.scl(PPM));
		}
		tmr.setView(cam);
		tmr.render();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
