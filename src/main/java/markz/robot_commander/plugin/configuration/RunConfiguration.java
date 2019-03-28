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

import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RefactoringListenerProvider;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import com.jetbrains.python.run.AbstractPythonRunConfiguration;
import com.jetbrains.python.run.CommandLinePatcher;
import markz.robot_commander.command.CommandFactory;
import markz.robot_commander.command.CommandFactoryInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class RunConfiguration extends AbstractPythonRunConfiguration
	implements CommandFactoryInterface, RefactoringListenerProvider {
	private static final Logger LOGGER = Logger.getInstance( RunConfiguration.class );

	private final CommandFactory factory;

	RunConfiguration( @NotNull Project project, @NotNull ConfigurationFactory factory ) {
		super( project, factory );

		this.factory = new CommandFactory();

		this.factory.setWorkingDirectory( project.getBasePath() );

		this.factory.setCommandName( "robot" );

		this.setUseModuleSdk( true );
	}

	//	/**
	//	 * Returns the UI control for editing the run configuration settings. If additional control over validation is
	//	 * required, the object returned from this method may also implement {@link CheckableRunConfigurationEditor}. The
	//	 * returned object can also implement {@link SettingsEditorGroup} if the settings it provides need to be displayed
	//	 * in multiple tabs.
	//	 *
	//	 * @return the settings editor component.
	//	 */
	//	@NotNull
	//	@Override
	//	public com.intellij.openapi.options.SettingsEditor<RunConfiguration> getConfigurationEditor() {
	//		return new RunConfigurationSettingsEditor( this.getProject() );
	//	}

	@Override
	protected SettingsEditor createConfigurationEditor() {
		return new RunConfigurationSettingsEditor( this.getProject() );
	}

	@Override
	public String getWorkingDirectory() {
		return this.factory.getWorkingDirectory();
	}

	@Override
	public void setWorkingDirectory( String workingDirectory ) {
		this.factory.setWorkingDirectory( workingDirectory );
	}

	/**
	 * Prepares for executing a specific instance of the run configuration.
	 *
	 * @param executor    the execution mode selected by the user (run, debug, profile etc.)
	 * @param environment the environment object containing additional settings for executing the configuration.
	 * @return the RunProfileState describing the process which is about to be started, or null if it's impossible to
	 * start the process.
	 */
	@Nullable
	@Override
	public com.intellij.execution.configurations.RunProfileState getState( @NotNull Executor executor,
		@NotNull ExecutionEnvironment environment ) {
		return new RunProfileState( this, environment );
	}

	@Override
	public String getCommandName() {
		return this.factory.getCommandName();
	}

	@Override
	public void setCommandName( String commandName ) {
		this.factory.setCommandName( commandName );
	}

	@Override
	public String generateCommand() {
		return this.factory.generateCommand();
	}

	@Override
	public List<String> getIncludedTags() {
		return this.factory.getIncludedTags();
	}

	@Override
	public List<String> getExcludedTags() {
		return this.factory.getExcludedTags();
	}

	@Override
	public List<String> getTests() {
		return this.factory.getTests();
	}

	@Override
	public List<String> getSuites() {
		return this.factory.getSuites();
	}

	/**
	 * Returns a listener to handle a rename or move refactoring of the specified PSI element.
	 *
	 * @param element the element on which a refactoring was invoked.
	 * @return the listener to handle the refactoring, or null if the run configuration doesn't care about refactoring
	 * of this element.
	 */
	@Nullable
	@Override
	public RefactoringElementListener getRefactoringElementListener( PsiElement element ) {
		return null;  // TODO https://www.jetbrains.org/intellij/sdk/docs/basics/run_configurations/run_configuration_management.html#refactoring-support
	}

	public CommandLinePatcher[] getPatchers() {
		synchronized ( this.factory ) {
			int numPatchers = 1 + getExcludedTags().size() + getIncludedTags().size(); // TODO Add the others
			CommandLinePatcher[] patchers = new CommandLinePatcher[numPatchers];
			int i = 0;
			// Add excluded tags patchers
			for ( String tag : getExcludedTags() ) {
				if ( i >= patchers.length ) {
					LOGGER.error( "There was an indexing error while adding excluded tags to the patchers." );
				}
				patchers[i++] = gcl -> gcl.addParameters( "-e", tag );
			}
			// Add included tags patchers
			for ( String tag : getIncludedTags() ) {
				if ( i >= patchers.length ) {
					LOGGER.error( "There was an indexing error while adding included tags to the patchers." );
				}
				patchers[i++] = gcl -> gcl.addParameters( "-i", tag );
			}
			patchers[i++] = gcl -> gcl.addParameter( getWorkingDirectory() );
			return patchers;
		}
	}

	public File getRunOutputDirectory() throws IOException {
		String name = UUID.randomUUID().toString();
		File outputDir = new File( getRobotOutputDirectory() + "/" + name );
		LOGGER.info( "Creating Robot Run Directory: " + outputDir.getAbsolutePath() );
		if ( outputDir.mkdir() ) {
			return outputDir;
		} else {
			throw new IOException(
				"The robot run output directory could not be created: " + outputDir.getAbsolutePath() );
		}
	}

	private File getRobotOutputDirectory() throws IOException {
		File robotOutputDirectory = new File( getTempDirectory(), "robot_outputs/" );
		if ( !robotOutputDirectory.exists() ) {
			LOGGER.info( "Creating System-wide Robot Directory: " + robotOutputDirectory.getAbsolutePath() );
			if ( !robotOutputDirectory.mkdir() ) {
				throw new IOException(
					"The system-wide robot output directory could not be created: " + robotOutputDirectory
						.getAbsolutePath() );
			}
		}
		return robotOutputDirectory;
	}

	private File getTempDirectory() {
		if ( System.getProperty( "os.name" ).contains( "Windows" ) ) {
			return getWindowsTempDirectory();
		} else {
			return getUnixTempDirectory();
		}
	}

	private File getWindowsTempDirectory() {
		return new File( System.getenv( "temp" ) );
	}

	private File getUnixTempDirectory() {
		return new File( "/tmp/" );
	}
}
