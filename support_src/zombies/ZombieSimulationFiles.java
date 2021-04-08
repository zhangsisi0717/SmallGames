package zombies;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import support.cse131.ArgsProcessor;
import support.cse131.FileChoosers;
import support.cse131.Scanners;

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class ZombieSimulationFiles {
	private static File toFile(URL url) {
		try {
			URI uri = url.toURI();
			return new File(uri);
		} catch (URISyntaxException urise) {
			return null;
		}
	}

	public static ArgsProcessor createArgsProcessorFromFile(String[] argsFromMain) {
		URL url = ZombieSimulationFiles.class.getResource("resources");
		File directory = toFile(url);
		File file = FileChoosers.chooseFile(argsFromMain, directory);
		if (file != null && file.exists()) {
			try {
				return Scanners.createArgsProcessorFromFile(file);
			} catch (FileNotFoundException fnfe) {
				throw new Error(fnfe);
			}
		} else {
			return new ArgsProcessor(new String[] {});
		}
	}
}
