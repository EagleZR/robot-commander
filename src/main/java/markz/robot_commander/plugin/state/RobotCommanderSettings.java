package markz.robot_commander.plugin.state;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class RobotCommanderSettings {

	private static final Object LOCK = new Object();
	private static RobotCommanderSettings instance;

	private List<RobotCommanderSettingsSubscriber> subscribers = new LinkedList<>(); // Should not persist
	private List<String> additionalCommands;

	private RobotCommanderSettings() {
		this.additionalCommands = new LinkedList<>(); // TODO Restore from PersistentStateComponent
	}

	public static RobotCommanderSettings getInstance() {
		if ( instance == null ) {
			synchronized ( LOCK ) {
				if ( instance == null ) {
					instance = new RobotCommanderSettings();
				}
			}
		}
		return instance;
	}

	public void subscribe( RobotCommanderSettingsSubscriber subscriber ) {
		if ( !this.subscribers.contains( subscriber ) ) {
			this.subscribers.add( subscriber );
		}
	}

	public String getDefaultCommand() {
		// TODO Update to be configurable through settings window
		for ( String command : getAvailableCommands() ) {
			if ( command.contains( "robot" ) ) {
				return command;
			}
		}
		return "";
	}

	public List<String> getAvailableCommands() {
		List<String> returnList = new LinkedList<>();
		for ( File command : RobotCommanderEnvironment.getInstance().getRobotCommandsOnPath() ) {
			returnList.add( command.getAbsolutePath() );
		}
		returnList.addAll( this.additionalCommands );
		return returnList;
	}

	public void addCommand( String command ) {
		this.additionalCommands.add( command );
	}

	public void removeCommand( String command ) {
		this.additionalCommands.remove( command );
	}
}
