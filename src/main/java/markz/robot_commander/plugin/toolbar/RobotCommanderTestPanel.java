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
import org.jdesktop.swingx.JXTree;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class RobotCommanderTestPanel extends JPanel {

	private static int vgap = 10;  // TODO Set via config file
	private static int hgap = 5;   // TODO Set via config file
	private static boolean debugColors = false;   // TODO Set via config file

	public RobotCommanderTestPanel() {
		if(debugColors) {
			this.setBackground( JBColor.RED );
		}
		// Configuration
		GridLayout layout = new GridLayout( 3, 1 );
		this.setLayout( layout );
		this.setMaximumSize( new Dimension( 1000000, 120 ) );
		layout.setHgap( hgap );
		layout.setVgap( vgap );
		// Initialize components

	}

	private static class TestTree extends JXTree {
		private TestTree() {

		}
	}

}
