package markz.robot_commander.toolbar;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Spawns a new instance of {@link RobotCommanderToolWindow}.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class RobotCommanderToolWindowFactory implements ToolWindowFactory {

	@Override public void createToolWindowContent( @NotNull Project project, @NotNull ToolWindow toolWindow ) {
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		Content content = contentFactory.createContent( RobotCommanderToolWindow.getInstance(), "", false );
		toolWindow.getContentManager().addContent( content );
	}

}
