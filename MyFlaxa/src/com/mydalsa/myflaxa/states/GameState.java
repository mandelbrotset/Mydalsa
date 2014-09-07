package com.mydalsa.myflaxa.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import general.IDGenerator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.mydalsa.myflaxa.entities.Bird;
import com.mydalsa.myflaxa.entities.Player;
import com.mydalsa.myflaxa.entities.Sprite;
import com.mydalsa.myflaxa.multiplayer.MultiplayerSprite;
import com.mydalsa.myflaxa.multiplayer.client.Client;
import com.mydalsa.myflaxa.util.Constants;

public class GameState extends State {

	private SpriteBatch batch;

	private boolean debug = false;
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera cam;
	private OrthographicCamera backgroundCam;

	private Player player;
	private OrthogonalTiledMapRenderer tmr;
	
	private TiledMap map;
	private float middle;

	private float lowest;

	private Vector3 eye;
	private int zoom;
	
	private boolean isClient;
	private Client client;

	
	private ConcurrentHashMap<Long, Sprite> sprites;

	public GameState(MyFlaxaGame game, Client client) {
		super(game);
		
		if(client != null)
			isClient = true;
		this.client = client;
		
		zoom = 1;
		sprites = new ConcurrentHashMap<Long, Sprite>();
		batch = game.getSpriteBatch();
		cam = game.getCamera();
		backgroundCam = new OrthographicCamera();
		backgroundCam.setToOrtho(false, cam.viewportWidth*2, cam.viewportHeight*2);

		// set up box2d stuff
		world = new World(new Vector2(0, Constants.GRAVITY), true);
		World.setVelocityThreshold(0);
		b2dr = new Box2DDebugRenderer();
		

		// create tiles
		loadTiles();

		// create player
		createPlayer();
		
		addSomeRandomBirds(10);


	}
	
	private void addSomeRandomBirds(int i){
		Random r = new Random();
		for(int x = 0; x<i; x++)
			addSprite(new Bird(new Vector2(Constants.X_START + r.nextFloat() + r.nextInt(i), middle), world, false, IDGenerator.getNewID()));
	}


	private void loadTiles() {
		map = new TmxMapLoader().load("res/myflaxa.tmx");
		tmr = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);
		Iterator<MapLayer> i = map.getLayers().iterator();
		while (i.hasNext()) {
			createLayer((TiledMapTileLayer) i.next());
		}
	}

	private void createLayer(TiledMapTileLayer layer) {

		if (layer.getProperties().containsKey("use")) {
			if (layer.getProperties().get("use").equals("0")) {
				return;
			}
		}

		float tileHeight = layer.getTileHeight();
		float tileWidth = layer.getTileWidth();

/*		float realHeight = Float.parseFloat((String) layer.getProperties().get(
				"height"));
		float realWidth = Float.parseFloat((String) layer.getProperties().get(
				"width"));*/

		middle = tileHeight * layer.getHeight() / Constants.PPM / 2;
		lowest = Float.MAX_VALUE;

		if (layer.getProperties().containsKey("nopolygonhack")) {
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

					bdef.position.set((x + 0.5f) * tileWidth / Constants.PPM, (y + 0.5f)
							* tileHeight / Constants.PPM);
					PolygonShape ps = new PolygonShape();

					ps.setAsBox((tileWidth) / 2 / Constants.PPM, (tileHeight) / 2 / Constants.PPM);
					FixtureDef fdef = new FixtureDef();
					fdef.friction = 0;
					fdef.shape = ps;
					world.createBody(bdef).createFixture(fdef);
					ps.dispose();

				}
			return;
		} else { 
			ChainShape shape = new ChainShape();
			BodyDef bdef = new BodyDef();
			bdef.type = BodyType.StaticBody;
			bdef.position.set(0, 0);

			ArrayList<Vector2> vs = new ArrayList<Vector2>();
			for (int x = 0; x < layer.getWidth(); x++)
				for (int y = 0; y < layer.getHeight(); y++) {
					Cell cell = layer.getCell(x, y);
					// check if cell exists
					if (cell == null)
						continue;
					if (cell.getTile() == null)
						continue;

					Vector2 v1 = new Vector2(
							((x + 0.5f) * tileWidth - (tileWidth / 2)) / Constants.PPM,
							((y + 0.5f) * tileHeight - (tileHeight / 2)) / Constants.PPM);
					Vector2 v2 = new Vector2(
							((x + 0.5f) * tileWidth + (tileWidth / 2)) / Constants.PPM,
							((y + 0.5f) * tileHeight - (tileHeight / 2)) / Constants.PPM);
					Vector2 v3 = new Vector2(
							((x + 0.5f) * tileWidth - (tileWidth / 2)) / Constants.PPM,
							((y + 0.5f) * tileHeight + (tileHeight / 2)) / Constants.PPM);
					Vector2 v4 = new Vector2(
							((x + 0.5f) * tileWidth + (tileWidth / 2)) / Constants.PPM,
							((y + 0.5f) * tileHeight + (tileHeight / 2)) / Constants.PPM);

					vs.add(v1);
					vs.add(v2);
					vs.add(v4);
					vs.add(v3);
					vs.add(v1);

				}
			if (vs.isEmpty()) {
				return;
			}
			shape.createChain(getCornerArray(vs));
			FixtureDef fdef = new FixtureDef();
			fdef.friction = 0;
			fdef.shape = shape;
			world.createBody(bdef).createFixture(fdef);
			shape.dispose();

		}
	}

	private Vector2[] getCornerArray(ArrayList<Vector2> list) {
		Vector2[] vs = new Vector2[5];

		Vector2 leftUpperCorner = new Vector2();
		Vector2 rightUpperCorner = new Vector2();
		Vector2 leftLowerCorner = new Vector2();
		Vector2 rightLowerCorner = new Vector2();

		HashMap<Float, ArrayList<Vector2>> mappingYValues = new HashMap<Float, ArrayList<Vector2>>();

		float lowestX = Float.MAX_VALUE;
		float lowestY = Float.MAX_VALUE;
		float highestX = Float.MIN_VALUE;
		float highestY = Float.MIN_VALUE;

		for (Vector2 v : list) {
			float x = v.x;
			float y = v.y;
			if (x < lowestX)
				lowestX = x;
			if (y < lowestY)
				lowestY = y;

			if (x > highestX)
				highestX = x;
			if (y > highestY)
				highestY = y;

			if (!mappingYValues.containsKey(y)) {
				mappingYValues.put(y, new ArrayList<Vector2>());
			}
			mappingYValues.get(y).add(v);

		}

		for (Vector2 v : mappingYValues.get(lowestY)) {
			if (v.x == lowestX)
				leftLowerCorner = v;
			if (v.x == highestX)
				rightLowerCorner = v;
		}
		for (Vector2 v : mappingYValues.get(highestY)) {
			if (v.x == lowestX)
				leftUpperCorner = v;
			if (v.x == highestX)
				rightUpperCorner = v;
		}

		vs[0] = leftLowerCorner;
		vs[1] = rightLowerCorner;
		vs[2] = rightUpperCorner;
		vs[3] = leftUpperCorner;
		vs[4] = leftLowerCorner;

		return vs;
	}

	private void createPlayer() {
		
		player = new Player(new Bird(new Vector2(Constants.X_START, middle), world, true, IDGenerator.getNewID()), "Player", this);
		
	}

	@Override
	public void handleInput() {
		player.handleInput();
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
			zoom++;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS) || Gdx.input.isKeyJustPressed(Input.Keys.I)) {
			if (zoom > 0) {
				zoom--;
			}
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			System.exit(0);
		}
	}

	@Override
	public void update(float dt) {
		
		if(isClient)
			syncSpriteList();
		
		handleInput();

		for(Sprite sprite : sprites.values())
			sprite.update(dt);

		world.step(dt, 6, 2);
		
		if(isClient)
			updateSpriteList();
	}

	private void updateSpriteList() {
		for(Sprite s : sprites.values())
			client.getList().addSprite(s);
	}

	private void syncSpriteList() {
		for(MultiplayerSprite mulSprite : client.getList().getSprites()){
			Sprite s = sprites.get(mulSprite.getId());
			if(s == null){
				s = createSprite(mulSprite);
			}
			updateBody(s.getBody(), mulSprite);
		}
	}

	private Sprite createSprite(MultiplayerSprite mulSprite) {
		Bird b = new Bird(mulSprite.getPosition(), world, false, mulSprite.getId());
		addSprite(b);
		return b;
	}
	
	private void updateBody(Body body, MultiplayerSprite mSprite){
		body.setTransform(mSprite.getPosition(), mSprite.getAngle());
		body.setLinearVelocity(mSprite.getVelocity());
		body.setAngularVelocity(mSprite.getAngle());
	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		eye = new Vector3(player.getPosition().x * Constants.PPM, player.getPosition().y
				* Constants.PPM, 0f);

		cam.position.set(eye);
		cam.zoom = zoom;
		cam.update();
		
		//Render background
		backgroundCam.position.set(eye);
		cam.update();
		batch.setProjectionMatrix(backgroundCam.combined);
		Texture t = MyFlaxaGame.getRes().getTexture("sky");
		batch.begin();
		batch.draw(t, 0, 0, t.getWidth()*4, t.getHeight()*4);
		batch.end();
		
		batch.setProjectionMatrix(cam.combined);
		
		//Render sprites
		for(Sprite sprite : sprites.values()){
			sprite.render(batch);
		}
		
		Matrix4 m = cam.combined.scl(Constants.PPM);
		
		//Render map
		tmr.setView(m, 0, 0, MyFlaxaGame.V_WIDTH,
				MyFlaxaGame.V_HEIGHT);
		tmr.render();
				
		//Render box2d debug
		if (debug) {
			b2dr.render(world, m);
		}

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	public void addSprite(Sprite sprite){
		sprites.put(sprite.getId(), sprite);
	}

}
