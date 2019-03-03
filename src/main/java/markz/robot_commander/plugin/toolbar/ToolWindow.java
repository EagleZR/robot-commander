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

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class ToolWindow extends JPanel {

	private static final Object lock = new Object();
	private static ToolWindow instance;
	private OptionPanel optionPanel;
	private TestPanel testPanel;

	private ToolWindow( File file ) {
		super( new BorderLayout() );
		this.add( new ButtonPanel(), BorderLayout.NORTH );
		JPanel tempPanel = new JPanel( new BorderLayout() );
		this.optionPanel = new OptionPanel();
		tempPanel.add( this.optionPanel, BorderLayout.NORTH );
		this.testPanel = new TestPanel( file );
		tempPanel.add( this.testPanel, BorderLayout.CENTER );
		this.add( tempPanel, BorderLayout.CENTER );
	}

	public static ToolWindow getInstance( File file ) {
		if ( instance == null ) {
			synchronized ( lock ) {
				if ( instance == null ) {
					instance = new ToolWindow( file );
				}
			}
		}
		instance.updateWorkingDirectory( file );
		return instance;
	}

	public void updateWorkingDirectory( File workingDirectory ) {
		this.testPanel.updateWorkingDirectory( workingDirectory );
	}

}
