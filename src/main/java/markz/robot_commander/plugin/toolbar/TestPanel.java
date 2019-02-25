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

import org.jdesktop.swingx.JXTree;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class TestPanel extends JPanel {

	private static final Object LOCK = new Object();
	private static TestPanel instance;
	private static TextField argumentsTextField;
	private static TextField includeTagsTextField;
	private static TextField excludeTagsTextField;
	private static int vgap = 10;  // TODO Set via config file
	private static int hgap = 5;   // TODO Set via config file

	private TestPanel() {
		// Configuration
		GridLayout layout = new GridLayout( 3, 1 );
		this.setLayout( layout );
		this.setMaximumSize( new Dimension( 1000000, 120 ) );
		layout.setHgap( hgap );
		layout.setVgap( vgap );
		// Initialize components
		createAndAddButtonPanel();
		createAndAddOptionPanel();
		createAndAddTestTreePanel();
	}

	public static TestPanel getInstance() {
		if ( instance == null ) {
			synchronized ( LOCK ) {
				if ( instance == null ) {
					instance = new TestPanel();
				}
			}
		}
		return instance;
	}

	private void createAndAddButtonPanel() {
		ButtonPanel buttonPanel = new ButtonPanel();
		this.add( buttonPanel );
	}

	private void createAndAddOptionPanel() {
		OptionPanel optionsPanel = new OptionPanel();
		this.add( optionsPanel );
	}

	private void createAndAddTestTreePanel() {
		TestTreePanel testTreePanel = new TestTreePanel();
		this.add( testTreePanel );
	}

	private static class ButtonPanel extends JPanel {

		private static JButton startButton;
		private static JButton stopButton;

		private ButtonPanel() {
			createAndAddStartButton();
			createAndAddStopButton();
		}

		// Method to help clean up the constructor
		private void createAndAddStartButton() {
			startButton = new JButton( "Start" );  // TODO Find how to reference IntelliJ's start button
			startButton.setVerticalTextPosition( AbstractButton.CENTER );
			startButton.setHorizontalAlignment( AbstractButton.LEADING );
			startButton.addActionListener( e -> {
				System.out.println( "Start clicked! Arguments:" + argumentsTextField.getText() );
			} );
			this.add( startButton );
		}

		// Method to help clean up the constructor
		private void createAndAddStopButton() {
			stopButton = new JButton( "Stop" );  // TODO Find how to reference IntelliJ's stop button
			stopButton.setVerticalTextPosition( AbstractButton.CENTER );
			stopButton.setHorizontalAlignment( AbstractButton.LEADING );
			stopButton.addActionListener( e -> {
				System.out.println( "Stop clicked!" );
			} );
			this.add( stopButton );
		}
	}

	private static class OptionPanel extends JPanel {
		private OptionPanel() {
			// Configuration
			GridLayout layout = new GridLayout( 3, 1 );
			this.setLayout( layout );
			layout.setHgap( hgap );
			layout.setVgap( vgap );
			// Initialize components
			createAndAddArgumentsLabel();
			createAndAddArgumentsTextField();
			createAndAddTagsPanel();
		}

		// Method to help clean up the constructor
		private void createAndAddArgumentsLabel() {
			Label argumentsLabel = new Label( "Arguments:" );
			this.add( argumentsLabel );
		}

		// Method to help clean up the constructor
		private void createAndAddArgumentsTextField() {
			argumentsTextField = new TextField();
			argumentsTextField.setMinimumSize( new Dimension( 400, 40 ) );
			argumentsTextField.setMaximumSize( new Dimension( 1000000, 40 ) );
			argumentsTextField.addTextListener( e -> {
				// TODO Audit input data for validity
			} );
			this.add( argumentsTextField );
		}

		private void createAndAddTagsPanel() {
			TagsPane tagsPane = new TagsPane();
			this.add( tagsPane );
		}
	}

	private static class TagsPane extends JPanel {
		private TagsPane() {
			// Configuration
			this.setLayout( new GridLayout( 2, 2 ) );
			this.setMaximumSize( new Dimension( 1000000, 40 ) );
			// Initialize components
			createAndAddIncludeTagsLabel();
			createAndAddExcludeTagsLabel();
			createAndAddIncludeTagsTextField();
			createAndAddExcludeTagsTextField();
		}

		// Method to help clean up the constructor
		private void createAndAddIncludeTagsLabel() {
			Label includeTagsLabel = new Label( "Include Tags:" );
			this.add( includeTagsLabel );
		}

		// Method to help clean up the constructor
		private void createAndAddIncludeTagsTextField() {
			includeTagsTextField = new TextField();
			includeTagsTextField.setMinimumSize( new Dimension( 400, 40 ) );
			includeTagsTextField.setMaximumSize( new Dimension( 1000000, 40 ) );
			this.add( includeTagsTextField );
		}

		// Method to help clean up the constructor
		private void createAndAddExcludeTagsLabel() {
			Label excludeTagsLabel = new Label( "Exclude Tags:" );
			this.add( excludeTagsLabel );
		}

		// Method to help clean up the constructor
		private void createAndAddExcludeTagsTextField() {
			excludeTagsTextField = new TextField();
			excludeTagsTextField.setMinimumSize( new Dimension( 400, 40 ) );
			excludeTagsTextField.setMaximumSize( new Dimension( 1000000, 40 ) );
			this.add( excludeTagsTextField );
		}
	}

	private static class TestTreePanel extends JPanel {
		private TestTreePanel() {
			this.setMinimumSize( new Dimension( 1000000, 300 ) );
		}
	}

	private static class TestTree extends JXTree {
		private TestTree() {

		}
	}

}
