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

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import markz.robot_commander.command.CommandFactoryInterface;
import markz.robot_commander.command.ConfigurationApplicator;
import markz.robot_commander.plugin.toolbar.BaseConfigurationPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class NamedConfigurationPanel extends JPanel implements ConfigurationApplicator {

	private NamePanel namePanel;
	private BaseConfigurationPanel baseConfigurationPanel;

	public NamedConfigurationPanel( File file ) {
		super( new BorderLayout() );
		this.namePanel = new NamePanel();
		this.add( namePanel, BorderLayout.NORTH );
		this.baseConfigurationPanel = new BaseConfigurationPanel( file );
		this.add( baseConfigurationPanel, BorderLayout.CENTER );
	}

	public void updateWorkingDirectory( File workingDirectory ) {
		this.baseConfigurationPanel.updateWorkingDirectory( workingDirectory );
	}

	@Override public void applyConfiguration( CommandFactoryInterface factory ) {
		this.baseConfigurationPanel.applyConfiguration( factory );
	}

	private class NamePanel extends JPanel {

		private JBTextField textField;

		private NamePanel() {
			super( new GridLayout( 2, 1 ) );

			// Label
			JBLabel label = new JBLabel( "Name:" );
			this.add( label );

			// Text Field
			this.textField = new JBTextField();
			this.add( this.textField );
		}
	}

}
