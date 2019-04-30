package markz.robot_commander.plugin.toolbar.tree.node;

public class TestNode extends BaseTestNode {

	private final String name;

	public TestNode( String name ) {
		super( name, false );
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Determines whether or not this node is allowed to have children.
	 * If <code>allows</code> is false, all of this node's children are
	 * removed.
	 * <p>
	 * Note: By default, a node allows children.
	 *
	 * @param allows true if this node is allowed to have children
	 */
	@Override
	public void setAllowsChildren( boolean allows ) {

	}

	/**
	 * Returns true if this node is allowed to have children.
	 *
	 * @return true if this node allows children, else false
	 */
	@Override
	public boolean getAllowsChildren() {
		return false;
	}
}
