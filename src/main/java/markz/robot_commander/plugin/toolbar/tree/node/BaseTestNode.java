package markz.robot_commander.plugin.toolbar.tree.node;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class BaseTestNode extends DefaultMutableTreeNode {

	/**
	 * Creates a tree node with no parent, no children, initialized with
	 * the specified user object, and that allows children only if
	 * specified.
	 *
	 * @param userObject     an Object provided by the user that constitutes
	 *                       the node's data
	 * @param allowsChildren if true, the node is allowed to have child
	 */
	public BaseTestNode( Object userObject, boolean allowsChildren ) {
		super( userObject, allowsChildren );
	}
}
