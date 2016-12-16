package org.kabeja;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by benja on 16/12/2016.
 */
public class Dev {

	public static void main(String[] args) {

		Path devFile = Paths.get("samples/dxf/draft1.dxf");
		System.out.println(Files.exists(devFile));


	}

}
