package markz.robot_commander.toolbar;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class RobotCommanderToolWindow extends JPanel {

	private static final Object lock = new Object();
	private static RobotCommanderToolWindow instance;

	private RobotCommanderToolWindow() {
		super( new BorderLayout() );
	}

	static RobotCommanderToolWindow getInstance() {
		if ( instance == null ) {
			synchronized ( lock ) {
				if ( instance == null ) {
					instance = new RobotCommanderToolWindow();
				}
			}
		}
		return instance;
	}

}
