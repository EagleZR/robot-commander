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

import com.intellij.execution.configurations.ConfigurationTypeBase;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class ConfigurationType extends ConfigurationTypeBase {

	private static final String ID = "robot-framework-config";
	private static final String DISPLAY_NAME = "Robot Framework";
	private static final String DESCRIPTION = "Execute Robot Framework tests";
	private static final Icon ICON = loadIcon();

	public ConfigurationType() {
		super( ID, DISPLAY_NAME, DESCRIPTION, ICON );
		addFactory( new ConfigurationFactory( this ) );
	}

	private static Icon loadIcon() {
		return IconLoader.getIcon( "/META-INF/robot_framework_11x16.png" );
	}


}
