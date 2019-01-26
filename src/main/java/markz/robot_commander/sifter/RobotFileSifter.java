package markz.robot_commander.sifter;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class RobotFileSifter {

	private final static String[] fileExtensions;
	private final static List<String> IGNORED_FILE_NAMES;

	static {
		fileExtensions = new String[] { ".robot" };  // TODO Move to config file
		IGNORED_FILE_NAMES = new LinkedList<>( Arrays.asList( "__init__.robot" ) );  // TODO Move to config file
	}

	public static List<File> getRobotFiles( File workingDirectory ) {
		List<File> returnList = new LinkedList<>();
		for ( File child : Objects.requireNonNull( workingDirectory.listFiles() ) ) {
			if ( child.isDirectory() ) {
				returnList.addAll( getRobotFiles( child ) );
			} else {
				for ( String fileExtension : fileExtensions ) {
					if ( child.getName().endsWith( fileExtension ) && !IGNORED_FILE_NAMES
							.contains( child.getName() ) ) {
						returnList.add( child );
					}
				}
			}
		}
		return returnList;
	}
}
