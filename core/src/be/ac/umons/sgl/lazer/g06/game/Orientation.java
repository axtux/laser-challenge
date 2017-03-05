package be.ac.umons.sgl.lazer.g06.game;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;


public class Orientation {
	
	Hashtable orientationForIndice = new Hashtable();
	String[] indiceForOrientation;


	/** @author Maazouz Mehdi
	 * this constructor return a hashtable and string[] element who contain directions from xml document
	 * @param xml it is a document on xml format
	 * @throws IOException
	 */
	public Orientation(FileHandle xml ) throws IOException{
		XmlReader reader= new XmlReader();
		Element root = reader.parse(xml);
		Array<Element> orientations = root.getChildrenByName("orientation");
		indiceForOrientation=new String[8];
		boolean advanced;
		if (orientations.size==4)
			advanced=false;
		else
			advanced=true;
		int indice=0;
		for (Element child : orientations ){
			indiceForOrientation[indice]=child.getText().toString();
			orientationForIndice.put(child.getText().toString(), indice);
			if (!advanced){
				indice+=1;
				indiceForOrientation[indice]="Indisponible";
			}
			indice+=1;
		}
			
	}
	
}
