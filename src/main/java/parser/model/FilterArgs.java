package parser.model;

/**
 * Represents an argument with minPrice and topN.
 * 
 * Immutable data class (record) used to arguments.
 * 
 * Fields:
 * <ul>
 *   <li>minPrice - arg for minimum price threshold</li>
 *   <li>topN - arg for top N products</li>
 * </ul>
 * 
 * @author Angelito Deocadiz
 * @version 1.0
 */

public record FilterArgs(double minPrice, int topN) {}
