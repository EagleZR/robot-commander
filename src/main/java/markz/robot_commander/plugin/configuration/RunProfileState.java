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

import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.diagnostic.Logger;
import com.jetbrains.python.run.CommandLinePatcher;
import com.jetbrains.python.run.PythonCommandLineState;
import com.jetbrains.python.sdk.InvalidSdkException;
import markz.robot_commander.command.RobotCommandException;

import java.io.File;
import java.io.IOException;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class RunProfileState extends PythonCommandLineState {
	private static final Logger LOGGER = Logger.getInstance( RunProfileState.class );

	private final RunConfiguration runConfiguration;

	public RunProfileState( RunConfiguration runConfiguration, ExecutionEnvironment env ) {
		super( runConfiguration, env );
		this.runConfiguration = runConfiguration;
	}

	@Override
	public ExecutionResult execute( Executor executor, PythonProcessStarter processStarter,
		CommandLinePatcher... patchers ) throws ExecutionException {

		CommandLinePatcher[] newPatcherArray;

		try {
			newPatcherArray = processPatchers( patchers );
		} catch ( Exception e ) {
			LOGGER.error( e );
			throw new ExecutionException( e );
		}

		ProcessHandler handler = startProcess( processStarter, newPatcherArray );
		ConsoleView consoleView = createAndAttachConsole( this.runConfiguration.getProject(), handler, executor );
		return new DefaultExecutionResult( consoleView, handler ); // TODO Create RobotExecutionResult
	}

	private CommandLinePatcher[] processPatchers( CommandLinePatcher... patchers )
		throws InvalidSdkException, IOException, RobotCommandException {
		CommandLinePatcher[] configPatchers = this.runConfiguration.getPatchers();

		int newArraySize = 2 + patchers.length + configPatchers.length;
		CommandLinePatcher[] newPatcherArray = new CommandLinePatcher[newArraySize];

		int i = 0;
		newPatcherArray[i++] = getCommandPatcher();
		//		newPatcherArray[i++] = gcl -> gcl.

		File outputDir = this.runConfiguration.getRunOutputDirectory();

		newPatcherArray[i++] = gcl -> gcl.addParameters( "-d", outputDir.getAbsolutePath() );

		// Config Patchers
		for ( CommandLinePatcher patcher : configPatchers ) {
			newPatcherArray[i++] = patcher;
		}

		// Argument Patchers
		for ( CommandLinePatcher patcher : patchers ) {
			newPatcherArray[i++] = patcher;
		}
		return newPatcherArray;
	}

	private CommandLinePatcher getCommandPatcher() throws InvalidSdkException, IOException, RobotCommandException {
		File interpreterParentDir = getInterpreterDirectory();
		// Find the path to the file which matches the configured command's name
		File[] childFiles = interpreterParentDir.listFiles();
		if ( childFiles == null ) {
			throw new IOException(
				"There was an error reading the parent directory of the project interpreter's executable." );
		}
		for ( File file : childFiles ) {
			String halfName = file.getName().split( "\\." )[0];
			if ( file.getName().equals( this.runConfiguration.getCommandName() ) || ( halfName != null && halfName
				.equals( this.runConfiguration.getCommandName() ) ) ) {
				return getCommandPatcher( file );
			}
		}
		throw new RobotCommandException( "The configured robot command, " + this.runConfiguration.getCommandName()
			+ ", could not be located within the current Python interpreter's directory" );
	}

	private File getInterpreterDirectory() throws InvalidSdkException {
		// Find Interpreter's parent directory
		String interpreterPath = getInterpreterPath( getEnvironment().getProject(), this.runConfiguration );
		if ( interpreterPath == null ) {
			throw new InvalidSdkException( "The Python Interpreter could not be loaded" );
		}
		File interpreterFile = new File( interpreterPath );
		return interpreterFile.getParentFile();
	}

	private CommandLinePatcher getCommandPatcher( File file ) {
		// TODO Validate robot file
		return gcl -> gcl.setExePath( file.getAbsolutePath() );
	}
}
