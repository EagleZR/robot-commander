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

package markz.robot_commander.plugin.configuration;

import markz.robot_commander.plugin.toolbar.OptionPanel;
import markz.robot_commander.plugin.toolbar.TestPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class ConfigurationPanel extends JPanel {

	private NamePanel namePanel;
	private OptionPanel optionPanel;
	private TestPanel testPanel;

	public ConfigurationPanel( File file ) {
		super( new BorderLayout() );
		this.namePanel = new NamePanel();
		this.add( namePanel, BorderLayout.NORTH );
		JPanel tempPanel = new JPanel( new BorderLayout() );
		this.optionPanel = new OptionPanel();
		tempPanel.add( this.optionPanel, BorderLayout.NORTH );
		this.testPanel = new TestPanel( file );
		tempPanel.add( this.testPanel, BorderLayout.CENTER );
		this.add( tempPanel, BorderLayout.CENTER );
	}

	public void updateWorkingDirectory( File workingDirectory ) {
		this.testPanel.updateWorkingDirectory( workingDirectory );
	}

	private class NamePanel extends JPanel {

		private TextField textField;

		private NamePanel() {
			super( new GridLayout( 2, 1 ) );

			// Label
			Label label = new Label( "Name:" );
			this.add( label );

			// Text Field
			this.textField = new TextField();
			this.add( this.textField );
		}
	}

}
