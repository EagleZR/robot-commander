/*
 * Copyright (c) 2019. Mark Zeagler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package markz.robot_commander.plugin.toolbar;

import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Provides a Control Panel with 'Start' and 'Stop' buttons to control the execution of Robot Framework tests.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class ButtonPanel extends JBPanel {

	private static boolean debugColors = false;   // TODO Set via config file

	private Runnable startAction = () -> System.out.println( "Start Clicked!" );
	private Runnable stopAction = () -> System.out.println( "Stop clicked!" );

	public ButtonPanel() {
		super( new FlowLayout( FlowLayout.LEFT ) );

		if ( debugColors ) {
			this.setBackground( JBColor.GREEN );
		}

		// Configuration
		this.setMaximumSize( new Dimension( 500, 50 ) );

		// Start Button
		JButton startButton = new JButton( "Start" );  // TODO Find how to reference IntelliJ's start button
		startButton.setVerticalTextPosition( AbstractButton.CENTER );
		startButton.setHorizontalAlignment( AbstractButton.LEADING );
		startButton.addActionListener( e -> start() );
		this.add( startButton );

		// Stop Button
		JButton stopButton = new JButton( "Stop" );  // TODO Find how to reference IntelliJ's stop button
		stopButton.setVerticalTextPosition( AbstractButton.CENTER );
		stopButton.setHorizontalAlignment( AbstractButton.LEADING );
		stopButton.addActionListener( e -> stop() );
		this.add( stopButton );

		// Pause Button

	}

	private void start() {
		this.startAction.run();
	}

	private void stop() {
		this.stopAction.run();
	}

	/**
	 * Sets the action to be performed when the Start Button is pressed
	 *
	 * @param r The {@link Runnable} that will execute when the 'Start' button is clicked.
	 */
	public void setStartAction( Runnable r ) {
		this.startAction = r;
	}

	/**
	 * Sets the action to be performed when the Start Button is pressed
	 *
	 * @param r The {@link Runnable} that will execute when the 'Stop' button is clicked.
	 */
	public void setStopAction( Runnable r ) {
		this.stopAction = r;
	}
}
