package edu.lhc.krr.sase.history;


/**
 * //TODO: Prototype -- Requires things like basic error checking.
 * 
 * This class is re-entrant but not thread safe.
 */
public final class ZValue {
    private final long[] b;
    private final int depth;
    private final int dimensions;
    private final double[] min;
    private final double[] max;
    private final long range;

    /**
     * Creates a new ZValue calculator
     * 
     * @param dimensions
     *            Number of dimensions in the data
     * @param depth
     *            Depth in bits to calculate. depth * dimensions must be less
     *            than 63.
     */
    public ZValue(int dimensions, int depth, double[] min, double[] max) {
        this.depth = depth;
        this.dimensions = dimensions;
        this.min = min;
        this.max = max;
     //   System.out.println((1 << (this.depth)));
        // 0001 ==>> 00010000 = 16
        range = (1 << (this.depth)) - 1;
        b = new long[dimensions];

    }

    public long calculate(double[] point) {
        for (int i = 0; i < dimensions; i++) {
            b[i] = calculateComponent(point[i], i);
        }
        return calculate(b);
    }

    /**
     * Calculates the z value by interleaving values that are already scaled to
     * the proper space.
     */
    public long calculate(long[] point) {
        long bitRead = 1 << (depth - 1);
        long result = 0;
        for (int depth = 0; depth < this.depth; depth++) {
            // reverse the order so it looks like a "z" and makes it consistent
            // with the Wikipedia definition.
            for (int i = dimensions - 1; i >= 0; i--) {
                long bit = ((point[i] & bitRead) != 0) ? 1 : 0;
                result = (result << 1) | bit;
             //System.out.println(   (point[i] & bitRead));
            }
            bitRead >>= 1;
        }

        return result;
    }

    /**
     * Calculates the non-interleaved component for one dimension.
     */
    public long calculateComponent(double v, int d) {
        return Math.round(((v - min[d]) / (max[d] - min[d])) * range);
    }

    public void decompose(long v, long[] point) {
        for (int i = 0; i < point.length; i++) {
            point[i] = 0;
        }

        long bitRead = 1 << ((depth * dimensions) - 1);
        for (int depth = 0; depth < this.depth; depth++) {
            // reverse the order so it looks like a "z" and makes it consistent
            // with
            // the Wikipedia definition.
            for (int i = dimensions - 1; i >= 0; i--) {
                long bit = ((v & bitRead) != 0) ? 1 : 0;
                point[i] = (point[i] << 1) | bit;
                bitRead >>= 1;
            }
        }
    }

    public int getDepth() {
        return depth;
    }

    public int getDimensions() {
        return dimensions;
    }

    public double getMax(int d) {
        return max[d];
    }

    /**
     * Returns the maximum value in any one dimension (number of bins in that
     * direction - 1).
     */
    public long getMaxDimensionRange() {
        return range;
    }

    public double getMin(int d) {
        return min[d];
    }
}