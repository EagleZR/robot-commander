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

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class RunProfileState extends CommandLineState {

	private final RunConfiguration runConfiguration;
	private GeneralCommandLine generalCommandLine;

	protected RunProfileState( ExecutionEnvironment environment, RunConfiguration runConfiguration ) {
		super( environment );
		this.runConfiguration = runConfiguration;
	}

	/**
	 * Starts the process.
	 *
	 * @return the handler for the running process
	 * @throws ExecutionException if the execution failed.
	 * @see GeneralCommandLine
	 * @see OSProcessHandler
	 */
	@NotNull @Override protected ProcessHandler startProcess() throws ExecutionException {
		this.generalCommandLine = new GeneralCommandLine( this.runConfiguration.generateCommand() );

		return new OSProcessHandler( this.generalCommandLine.createProcess(),
				this.runConfiguration.generateCommand() );
	}
}
