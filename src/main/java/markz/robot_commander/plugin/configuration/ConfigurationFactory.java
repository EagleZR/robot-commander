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

import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class ConfigurationFactory extends com.intellij.execution.configurations.ConfigurationFactory {

	private static ConfigurationFactory instance;

	protected ConfigurationFactory(
		@NotNull
			ConfigurationType type ) {
		super( type );
		instance = this;
	}

	public static ConfigurationFactory getInstance() {
		return instance;
	}

	/**
	 * Creates a new template run configuration within the context of the specified project.
	 *
	 * @param project the project in which the run configuration will be used
	 * @return the run configuration instance.
	 */
	@NotNull
	@Override
	public com.intellij.execution.configurations.RunConfiguration createTemplateConfiguration(
		@NotNull
			Project project ) {
		return new RunConfiguration( project, this );
	}
}
