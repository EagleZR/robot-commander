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

import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

/**
 * Spawns a new instance of {@link ToolWindow} using the current working directory.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class ToolWindowFactory implements com.intellij.openapi.wm.ToolWindowFactory {

	@Override public void createToolWindowContent( @NotNull Project project, @NotNull com.intellij.openapi.wm.ToolWindow toolWindow ) {
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		VirtualFile virtualFile = ModuleRootManager.getInstance( ModuleManager.getInstance( project ).getModules()[0] )
				.getContentRoots()[0]; // TODO Resolve possible issues due to multiple modules or content roots
		File file = new File( Objects.requireNonNull( virtualFile.getCanonicalPath() ) );
		Content content = contentFactory.createContent( ToolWindow.getInstance( file ), "", false );
		toolWindow.getContentManager().addContent( content );
	}

}
