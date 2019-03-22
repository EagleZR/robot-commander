package markz.robot_commander.plugin.state;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RobotCommanderEnvironment {

	private static final Object LOCK = new Object();
	private static final String[] linuxCommandNames = { "robot", "pybot", "ipybot", "jybot" };
	private static final String[] windowsCommandNames = { "robot.bat", "pybot.bat", "ipybot.bat", "jybot.bat" };
	private static RobotCommanderEnvironment instance;
	private List<File> robotCommandsOnPath = new LinkedList<>();

	private RobotCommanderEnvironment() {
		// Parse through PATH looking for robot installations
		String path = System.getenv( "PATH" );
		String[] pathLocations = path.split( ";" );
		for ( String pathLocation : pathLocations ) {
			File pathLocationFile = new File( pathLocation );
			if ( pathLocationFile.isDirectory() ) {
				for ( File file : pathLocationFile.listFiles() ) {
					if ( isRobotCommand( file ) ) {
						robotCommandsOnPath.add( file );
					}
				}
			} else { // If the pathLocationFile is not a directory
				if ( isRobotCommand( pathLocationFile ) ) {
					robotCommandsOnPath.add( pathLocationFile );
				}
			}
		}
	}

	private static boolean isRobotCommand( File file ) {
		String[] commandNames = System.getProperty( "os.name" ).contains( "Windows" ) ?
			windowsCommandNames :
			linuxCommandNames;
		for ( String commandName : commandNames ) {
			if ( file.getName().equals( commandName ) ) {
				return true;
			}
		}
		return false;
	}

	protected static RobotCommanderEnvironment getInstance() {
		if ( instance == null ) {
			synchronized ( LOCK ) {
				if ( instance == null ) {
					instance = new RobotCommanderEnvironment();
				}
			}
		}
		return instance;
	}

	public List<File> getRobotCommandsOnPath() {
		return Collections.unmodifiableList( robotCommandsOnPath );
	}
}
