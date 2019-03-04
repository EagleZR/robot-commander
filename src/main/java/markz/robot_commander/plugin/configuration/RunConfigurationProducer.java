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

import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class RunConfigurationProducer
		extends com.intellij.execution.actions.RunConfigurationProducer<RunConfiguration> {

	protected RunConfigurationProducer( ConfigurationFactory configurationFactory ) {
		super( configurationFactory );
	}

	protected RunConfigurationProducer( ConfigurationType configurationType ) {
		super( configurationType );
	}

	/**
	 * Sets up a configuration based on the specified context.
	 *
	 * @param configuration a clone of the template run configuration of the specified type
	 * @param context       contains the information about a location in the source code.
	 * @param sourceElement a reference to the source element for the run configuration (by default contains the element
	 *                      at caret, can be updated by the producer to point to a higher-level element in the tree).
	 * @return true if the context is applicable to this run configuration producer, false if the context is not
	 * applicable and the configuration should be discarded.
	 */
	@Override protected boolean setupConfigurationFromContext( RunConfiguration configuration,
			ConfigurationContext context, Ref<PsiElement> sourceElement ) {
		try {
			if ( context.getLocation().getVirtualFile().getName().endsWith( ".robot" ) ) {
				configuration.setName( context.getLocation().getVirtualFile().getName() );
				// TODO Set up rest of configuration
				return true;
			} // TODO Check for individual test name, too
		} catch ( Exception e ) {
			return false;
		}
		return false;  // TODO https://www.jetbrains.org/intellij/sdk/docs/basics/run_configurations/run_configuration_management.html#creating-configurations-from-context
	}

	/**
	 * Checks if the specified configuration was created from the specified context.
	 *
	 * @param configuration a configuration instance.
	 * @param context       contains the information about a location in the source code.
	 * @return true if this configuration was created from the specified context, false otherwise.
	 */
	@Override public boolean isConfigurationFromContext( RunConfiguration configuration,
			ConfigurationContext context ) {
		return false;  // TODO https://www.jetbrains.org/intellij/sdk/docs/basics/run_configurations/run_configuration_management.html#creating-configurations-from-context
	}
}
