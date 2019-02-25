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

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class RobotCommanderToolWindow extends JPanel {

	private static final Object lock = new Object();
	private static RobotCommanderToolWindow instance;

	private RobotCommanderToolWindow() {
		super( new BorderLayout() );
		this.add( RobotCommanderTestPanel.getInstance() );
	}

	public static RobotCommanderToolWindow getInstance() {
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
