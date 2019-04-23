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
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import markz.robot_commander.plugin.state.RobotCommanderSettings;
import markz.robot_commander.plugin.state.RobotCommanderSettingsSubscriber;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class OptionPanel extends JPanel {

	private static boolean debugColors = false;   // TODO Set via config file
	private JBTextField argumentsTextField;
	private JBTextField includeTagsTextField;
	private JBTextField excludeTagsTextField;
	private JComboBox<String> commandComboBox;

	public OptionPanel() {
		super( new GridLayout( 3, 1 ) );

		if ( debugColors ) {
			this.setBackground( JBColor.CYAN );
		}

		// Configuration

		// Initialize components
		createAndAddCommandPanel();
		createAndAddTagsPanel();
		createAndAddArgumentsPanel();
	}

	private void createAndAddCommandPanel() {
		CommandPanel commandPanel = new CommandPanel();
		this.add( commandPanel );
	}

	private void createAndAddTagsPanel() {
		TagsPanel tagsPanel = new TagsPanel();
		this.add( tagsPanel );
	}

	private void createAndAddArgumentsPanel() {
		ArgumentsPanel argumentsPanel = new ArgumentsPanel();
		this.add( argumentsPanel );
	}

	public String getArguments() {
		return this.argumentsTextField.getText();
	}

	public String getExcludedTags() {
		return this.excludeTagsTextField.getText();
	}

	public String getIncludedTags() {
		return this.includeTagsTextField.getText();
	}

	public String getCommand() {
		return this.commandComboBox == null ? "" : (String) this.commandComboBox.getSelectedItem();
	}

	private class CommandPanel extends JPanel implements RobotCommanderSettingsSubscriber {

		private CommandPanel() {
			if ( debugColors ) {
				this.setBackground( JBColor.CYAN );
			}

			RobotCommanderSettings.getInstance().subscribe( this );

			// Configuration
			GridLayout layout = new GridLayout( 2, 1 );
			this.setLayout( layout );

			// Initialize Components
			createAndAddCommandLabel();
			createAndAddCommandComboBox();
		}

		private void createAndAddCommandLabel() {
			JBLabel commandLabel = new JBLabel( "Select Command:" );
			this.add( commandLabel );
		}

		private void createAndAddCommandComboBox() {
			commandComboBox = new JComboBox<>();
			updateCommands();
			this.add( commandComboBox );
		}

		private void updateCommands() {
			Object selected = commandComboBox.getSelectedItem();
			commandComboBox.removeAllItems();
			for ( String command : RobotCommanderSettings.getInstance().getAvailableCommands() ) {
				commandComboBox.addItem( command );
			}
			if ( selected != null && !selected.equals( "" ) ) {
				commandComboBox.setSelectedItem( selected );
			}
		}

		@Override
		public void notifySettingsChanged() {
			updateCommands();
		}
	}

	private class ArgumentsPanel extends JPanel {

		private ArgumentsPanel() {
			if ( debugColors ) {
				this.setBackground( JBColor.CYAN );
			}

			// Configuration
			GridLayout layout = new GridLayout( 2, 1 );
			//			layout.setHgap( hgap );
			//			layout.setVgap( vgap );
			this.setLayout( layout );

			// Initialize components
			createAndAddArgumentsLabel();
			createAndAddArgumentsTextField();
		}

		// Method to help clean up the constructor
		private void createAndAddArgumentsLabel() {
			JBLabel argumentsLabel = new JBLabel( "Additional Arguments:" );
			this.add( argumentsLabel );
		}

		// Method to help clean up the constructor
		private void createAndAddArgumentsTextField() {
			argumentsTextField = new JBTextField();
			argumentsTextField.setMaximumSize( new Dimension( 1000000, 40 ) );
			//			argumentsTextField.addTextListener( e -> {
			//				// TODO Audit input data for validity
			//			} );
			this.add( argumentsTextField );
		}
	}

	private class TagsPanel extends JPanel {

		private TagsPanel() {
			if ( debugColors ) {
				this.setBackground( JBColor.CYAN );
			}
			// Configuration
			GridLayout layout = new GridLayout( 2, 2 );
			//			layout.setHgap( hgap );
			//			layout.setVgap( vgap );
			this.setLayout( layout );
			this.setMaximumSize( new Dimension( 1000000, 40 ) );

			// Initialize components
			createAndAddIncludeTagsLabel();
			createAndAddExcludeTagsLabel();
			createAndAddIncludeTagsTextField();
			createAndAddExcludeTagsTextField();
		}

		// Method to help clean up the constructor
		private void createAndAddIncludeTagsLabel() {
			JBLabel includeTagsLabel = new JBLabel( "Include Tags:" );
			this.add( includeTagsLabel );
		}

		// Method to help clean up the constructor
		private void createAndAddIncludeTagsTextField() {
			includeTagsTextField = new JBTextField();
			includeTagsTextField.setMaximumSize( new Dimension( 1000000, 40 ) );
			this.add( includeTagsTextField );
		}

		// Method to help clean up the constructor
		private void createAndAddExcludeTagsLabel() {
			JBLabel excludeTagsLabel = new JBLabel( "Exclude Tags:" );
			this.add( excludeTagsLabel );
		}

		// Method to help clean up the constructor
		private void createAndAddExcludeTagsTextField() {
			excludeTagsTextField = new JBTextField();
			excludeTagsTextField.setMaximumSize( new Dimension( 1000000, 40 ) );
			this.add( excludeTagsTextField );
		}
	}
}
