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

import com.intellij.ui.components.JBPanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class ToolWindow extends JBPanel {

	private static final Object lock = new Object();
	private static ToolWindow instance;
	private BaseConfigurationPanel baseConfigurationPanel;

	private ToolWindow( File file ) throws IOException {
		super( new BorderLayout() );
		this.add( new ButtonPanel(), BorderLayout.NORTH );
		this.baseConfigurationPanel = new BaseConfigurationPanel( file );
		this.add( this.baseConfigurationPanel, BorderLayout.CENTER );
	}

	public static ToolWindow getInstance( File file ) throws IOException {
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

	public void updateWorkingDirectory( File workingDirectory ) throws IOException {
		this.baseConfigurationPanel.updateWorkingDirectory( workingDirectory );
	}

}
