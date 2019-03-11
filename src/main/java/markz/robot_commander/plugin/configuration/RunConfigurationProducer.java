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
import com.intellij.psi.PsiFile;
import markz.robot_commander.sifter.RobotTestSifter;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
		PsiElement element = sourceElement.get();
		// TODO https://www.jetbrains.org/intellij/sdk/docs/basics/run_configurations/run_configuration_management.html#creating-configurations-from-context
		try {
			// If it's a robot file
			File file;
			if ( ( file = isFile( element ) ) != null ) {
				if ( file.isFile() && isTestFile( file ) ) {
					String name = file.getName();
					configuration.setWorkingDirectory( file );
					configuration.setName( name );
					// TODO Set up rest of configuration
					return true;
				}
			}
			// If it's a robot suite
			File dir;
			if ( ( dir = isDirectory( element ) ) != null ) {
				if ( isTestSuite( dir ) ) {
					configuration.setWorkingDirectory( dir );
					configuration.setName( dir.getName() );
					// TODO Set up rest of configuration
					return true;
				}
			}
			// TODO Check for individual test name, too
			System.out.println( "Check for individual tests" );
		} catch ( Exception e ) {
			e.printStackTrace();
			return false;
		}
		return false;
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

	/**
	 * Checks if the given {@link PsiElement} corresponds to a {@link File}.
	 *
	 * @param element
	 * @return
	 */
	private File isFile( PsiElement element ) {
		try {
			PsiFile psiFile = element.getContainingFile();
			if ( psiFile != null ) {
				return new File( psiFile.getVirtualFile().getCanonicalPath() );
			}
		} catch ( NullPointerException e ) {

		}
		return null;
	}

	/**
	 * Checks if the given {@link PsiElement} corresponds to a directory {@link File}.
	 *
	 * @param element
	 * @return
	 */
	private File isDirectory( PsiElement element ) {
		try {
			for ( PsiElement child : element.getChildren() ) {
				if ( child.getContainingFile().getName().equals( "__init__.robot" ) ) {
					File childFile = new File( child.getContainingFile().getVirtualFile().getCanonicalPath() );
					return childFile.getParentFile();
				}
			}
		} catch ( NullPointerException e ) {

		}
		return null;
	}

	/**
	 * Checks if the given {@link File} is a Robot Framework test file.
	 *
	 * @param file
	 * @return
	 */
	private boolean isTestFile( File file ) {
		if ( file == null ) {
			return false;
		}
		String name = file.getName();
		try {
			if ( !name.equals( "__init__.robot" ) && name.endsWith( ".robot" )
					&& RobotTestSifter.getFileTests( file ).size() > 0 ) {
				return true;
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks if the given {@link File} is a Robot Framework test suite.
	 *
	 * @param file
	 * @return
	 */
	private boolean isTestSuite( @Nullable File file ) {
		if ( file == null ) {
			return false;
		}
		if ( file.isDirectory() ) {
			for ( File child : Objects.requireNonNull( file.listFiles() ) ) {
				System.out.println( child.getAbsolutePath() );
				if ( isTestFile( child ) ) {
					return true;
				}
			}
		}
		return false;
	}
}
