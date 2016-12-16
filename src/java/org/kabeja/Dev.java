package org.kabeja;

import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLWPolyline;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFLineType;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import static java.nio.file.Files.newInputStream;

/**
 * Created by benja on 16/12/2016.
 */
public class Dev {

	public static void main(String[] args) {

		Path devFile = Paths.get("samples/accurate-dev.dxf");
		//System.out.println(Files.exists(devFile));

		try (BufferedInputStream bis = new BufferedInputStream(newInputStream(devFile))){

			Parser parser = ParserBuilder.createDefaultParser();
			parser.parse(bis, DXFParser.DEFAULT_ENCODING);
			DXFDocument doc = parser.getDocument();

			Iterator it = doc.getDXFLayerIterator();
			while (it.hasNext()) {
				DXFLayer layer = (DXFLayer) it.next();

				List<DXFEntity> entities = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_LWPOLYLINE);

				for (DXFEntity entity : entities) {
					System.out.println(entity);
					System.out.println(entity.getID());
				}

				/*Iterator typeIt = layer.getDXFEntityTypeIterator();
				while (typeIt.hasNext()) {
					Object obj = typeIt.next();
					System.out.println(obj);
				}*/
			}

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

}
