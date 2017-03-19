package be.ac.umons.sgl.lazer.g06.game;

//import java.io.File;
import java.io.IOException;
//import java.util.Enumeration;
import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;


public class Orientation {
	
	private Hashtable<String,Integer> orientationToIndice = new Hashtable<String ,Integer>();
	private String[] indiceToOrientation;


	/**
	 * this constructor return a hashtable and string[] element who contain directions from xml document
	 * @param xml it is a document on xml format
	 * @throws IOException
	 */
	public Orientation(FileHandle xml ) throws IOException{
		XmlReader reader= new XmlReader();
		Element root = reader.parse(xml);
		Array<Element> orientations = root.getChildrenByName("orientation");
		int indice=0;
		for (Element child : orientations ){
			if(!this.getOrientationToIndice().containsKey(child.getText().toString())){
				//indiceToOrientation[indice]=child.getText().toString();
				orientationToIndice.put(child.getText().toString(), indice);
				indice+=1;
			}
		}
		indiceToOrientation=new String[orientationToIndice.size()];
		Object[] key= orientationToIndice.keySet().toArray();
		for(int i=0; i< orientationToIndice.size();i++){
			indiceToOrientation[i]=key[i].toString();
			
		}
			
	}
	
	/**
	 * @return the attribut orientationToIndice from object
	 */
	public Hashtable<String,Integer> getOrientationToIndice(){
		return orientationToIndice;
	}
	
	/**
	 * @return the attribut indiceToOrientation from object
	 */
	public String[] getIndiceToOrientation(){
		return indiceToOrientation;
	}
	
	public int size(){
		return this.orientationToIndice.size();
	}
}
