package consumable;

/**
 * The Interface Visitor.
 */
public interface Visitor {
	public void visit(Special special);
	public void visit(Starter starter);
	public void visit(Mains main);
	public void visit(Desert desert);
	public void visit(Drink drink);
}
