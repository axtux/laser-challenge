package be.ac.umons.sgl.lazer.g06.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Map {
	static final String MAP_PATH = "maps";
	private TiledMap map;
	TiledMapTileLayer ground;
	TiledMapTileLayer blocks;
	
	public Map(String name) {
		Gdx.app.debug("Map.Map", "creating map from file "+name+".tmx");
		// load map from local files
		TmxMapLoader loader = new TmxMapLoader(new LocalFileHandleResolver());
		
		map = loader.load(MAP_PATH+"/"+name+".tmx");
		ground = (TiledMapTileLayer)map.getLayers().get("ground");
	}
	
	public TiledMap getTiledMap() {
		return map;
	}
	
	public String getProp(String prop) {
		return getProp(prop, "");
	}
	
	public String getProp(String prop, String defaultValue) {
		return map.getProperties().get(prop, defaultValue, String.class);
	}
	
	public String getCellProp(int x, int y, String prop) {
		return getCellProp(x, y, prop, "");
	}
	
	public String getCellProp(int x, int y, String prop, String defaultValue) {
		Cell c = ground.getCell(x, y);
		return c.getTile().getProperties().get(prop, defaultValue, String.class);
	}
}
