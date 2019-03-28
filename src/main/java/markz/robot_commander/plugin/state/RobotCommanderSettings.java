package markz.robot_commander.plugin.state;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.util.xmlb.XmlSerializer;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.CollectionBean;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

@State( name = "markz.robot_commander.state" )
public class RobotCommanderSettings implements PersistentStateComponent<RobotCommanderSettings.State> {

	private static final Object LOCK = new Object();
	private static RobotCommanderSettings instance;

	private List<RobotCommanderSettingsSubscriber> subscribers = new LinkedList<>(); // Should not persist
	private State state;

	private RobotCommanderSettings() {

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
		//		for ( String command : getAvailableCommands() ) {
		//			if ( command.contains( "robot" ) ) {
		//				return command;
		//			}
		//		}
		return "robot";
	}

	public List<String> getAvailableCommands() {
		List<String> returnList = new LinkedList<>();
		for ( File command : RobotCommanderEnvironment.getInstance().getRobotCommandsOnPath() ) {
			returnList.add( command.getAbsolutePath() );
		}
		returnList.addAll( getState().additionalCommands );
		return returnList;
	}

	/**
	 * @return a component state. All properties, public and annotated fields are serialized. Only values, which differ
	 * from default (i.e. the value of newly instantiated class) are serialized. {@code null} value indicates
	 * that the returned state won't be stored, as a result previously stored state will be used.
	 * @see XmlSerializer
	 */
	@Nullable
	@Override
	public State getState() {
		if ( this.state == null ) {
			loadNewState();
		}
		return this.state;
	}

	/**
	 * This method is called when new component state is loaded. The method can and will be called several times, if
	 * config files were externally changed while IDEA running.
	 *
	 * @param state loaded component state
	 * @see XmlSerializerUtil#copyBean(Object, Object)
	 */
	@Override
	public void loadState( State state ) {
		this.state = state;
	}

	/**
	 * This method is called when component is initialized but no state is persisted.
	 */
	@Override
	public void noStateLoaded() {
		loadNewState();
	}

	private void loadNewState() {
		// Wrap it to make sure accidentally calling it doesn't reset the state
		if ( this.state == null ) {
			synchronized ( LOCK ) {
				if ( this.state == null ) {
					this.state = new State();
				}
			}
		}
	}

	public void addCommand( String command ) {
		getState().additionalCommands.add( command );
	}

	public void removeCommand( String command ) {
		getState().additionalCommands.remove( command );
	}

	public static class State {

		@CollectionBean private List<String> additionalCommands;

		private State() {
			additionalCommands = new LinkedList<>();
		}
	}
}
