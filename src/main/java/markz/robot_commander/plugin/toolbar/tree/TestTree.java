package markz.robot_commander.plugin.toolbar.tree;

import com.intellij.ui.treeStructure.Tree;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.renderer.IconValues;
import org.jdesktop.swingx.renderer.StringValues;

import java.io.File;
import java.io.IOException;

// https://www.jetbrains.org/intellij/sdk/docs/user_interface_components/lists_and_trees.html
public class TestTree extends Tree {

	public TestTree( File rootFile ) throws IOException {
		super( new TestTreeModel( rootFile ) );
		this.setCellRenderer( new DefaultTreeRenderer( IconValues.FILE_ICON, StringValues.FILE_NAME ) );
	}

}
