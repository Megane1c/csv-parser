package parser.util;

import parser.model.*;

public class ParseArgs {

    public static FilterArgs parseArgs(String[] args) throws IllegalArgumentException {
        if (args.length < 2) {
            throw new IllegalArgumentException("Usage: <minPrice> <topN>");
        }
        try {
            double minPrice = Double.parseDouble(args[0]);
            int topN = Integer.parseInt(args[1]);

            if (minPrice < 0) {
            throw new IllegalArgumentException("minPrice must be nonnegative.");
            }
            
            if (topN < 0) {
                throw new IllegalArgumentException("topN must be nonnegative.");
            }

            return new FilterArgs(minPrice, topN);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format.", e);
        }
    }
}
