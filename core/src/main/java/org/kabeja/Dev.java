package org.kabeja;

import org.kabeja.dxf.parser.SAXDXFParserBuilder;
import org.kabeja.parser.Parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.newInputStream;

/**
 * Created by benjaminaaron on 12/16/2016.
 */
public class Dev {

    public static void main(String[] args) {

        Path devFile = Paths.get("C:\\Users\\benjaminaaron\\git\\postvis\\testfiles\\dxf\\dev.dxf");
        //System.out.println(Files.exists(devFile));

        try (BufferedInputStream bis = new BufferedInputStream(newInputStream(devFile))){

            Parser parser = SAXDXFParserBuilder.buildFromStream(bis);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
