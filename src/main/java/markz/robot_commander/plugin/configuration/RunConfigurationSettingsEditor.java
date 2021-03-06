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

import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Mark Zeagler
 * @version 1.0
 */
public class RunConfigurationSettingsEditor extends SettingsEditor<RunConfiguration> {

	private static final Object LOCK = new Object();
	private Project project;
	private NamedConfigurationPanel settingsPanel;

	public RunConfigurationSettingsEditor( Project project ) {
		this.project = project;
	}

	@Override
	protected void resetEditorFrom(
		@NotNull
			RunConfiguration s ) {
		// TODO Apply the configuration to the Panel components
	}

	@Override
	protected void applyEditorTo(
		@NotNull
			RunConfiguration s ) throws ConfigurationException {
		this.settingsPanel.applyConfiguration( s );
	}

	@NotNull
	@Override
	protected JComponent createEditor() {
		VirtualFile virtualFile = ModuleRootManager.getInstance( ModuleManager.getInstance( project ).getModules()[0] )
			.getContentRoots()[0];
		File file = new File( Objects.requireNonNull( virtualFile.getCanonicalPath() ) );

		if ( this.settingsPanel == null ) {
			synchronized ( LOCK ) {
				if ( this.settingsPanel == null ) {
					try {
						this.settingsPanel = new NamedConfigurationPanel( file );
					} catch ( IOException e ) {
						e.printStackTrace();
					}
				}
			}
		}

		try {
			this.settingsPanel.updateWorkingDirectory( file );
		} catch ( IOException e ) {
			e.printStackTrace();
		}

		return settingsPanel;
	}
}
