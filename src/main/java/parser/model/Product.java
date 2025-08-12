package parser.model;

/**
 * Represents a product with productName, price, category, and stock status.
 * 
 * Immutable data class (record) used to hold product data parsed from CSV.
 * 
 * Fields:
 * <ul>
 *   <li>productName - name of the product</li>
 *   <li>price - price of the product</li>
 *   <li>category - product category</li>
 *   <li>inStock - whether the product is in stock</li>
 * </ul>
 * 
 * @author Angelito Deocadiz
 * @version 1.0
 */

public record Product(String productName, double price, String category, boolean inStock) {}

