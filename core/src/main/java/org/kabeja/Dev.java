package org.kabeja;

import org.kabeja.common.DraftEntity;
import org.kabeja.common.Layer;
import org.kabeja.common.Type;
import org.kabeja.dxf.parser.DXFParser;
import org.kabeja.dxf.parser.DXFParserBuilder;
import org.kabeja.dxf.parser.SAXDXFParserBuilder;
import org.kabeja.entities.LW2DVertex;
import org.kabeja.entities.LWPolyline;
import org.kabeja.entities.Viewport;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;

import static java.nio.file.Files.newInputStream;

/**
 * Created by benjaminaaron on 12/16/2016.
 */
public class Dev {

    public static void main(String[] args) throws ParseException {

        Path devFile = Paths.get("samples/Drawing1-qcad.dxf");
        //System.out.println(Files.exists(devFile));

        try (BufferedInputStream bis = new BufferedInputStream(newInputStream(devFile))){


            Parser parser = DXFParserBuilder.createDefaultParser();
            DraftDocument doc = parser.parse(bis, new HashMap<>());


            for (Viewport viewport : doc.getViewports()) {
                System.out.println(viewport.getID());
            }

            Collection<Layer> layers = doc.getLayers();
            for (Layer layer : layers) {
                System.out.println(layer.getName());

                /*for (Type<? extends DraftEntity> type : layer.getEntityTypes()) {
                    System.out.println(type.getName());
                }*/

                if (!layer.isFrozen()) {

                    for (LWPolyline line : layer.getEntitiesByType(Type.TYPE_LWPOLYLINE)) {

                        for (LW2DVertex vertex : line.getVertices()) {
                            System.out.println(vertex.getPoint().toString());
                        }
                    }
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
