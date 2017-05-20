package edu.lhc.krr.sase.history;


/**
 * Range of values. Min is inclusive. Max is inclusive.
 */
public class Range implements Comparable<Range> {
    private long max;
    private long min;

    public Range() {
    }

    public Range(long min, long max) {
        set(min, max);
    }

    public long calculateSize() {
        return (max - min) + 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Range range = (Range) obj;

        if (max != range.max) {
            return false;
        }
        return min == range.min;

    }

    @Override
    public int hashCode() {
        int result = (int) (max ^ (max >>> 32));
        result = (31 * result) + (int) (min ^ (min >>> 32));
        return result;
    }

    @Override
    public int compareTo(Range o) {
        return Long.valueOf(this.min).compareTo(o.getMin());
    }

    public long getMax() {
        return max;
    }

    public long getMin() {
        return min;
    }

    public boolean in(long l) {
        return (l >= min) && (l <= max);
    }

    public boolean isValid() {
        return (min >= 0) && (max >= 0);
    }

    public void setInvalid() {
        min = -1;
        max = -1;
    }

    public void set(long min, long max) {
        this.min = min;
        this.max = max;
        if (this.min > this.max) {
            throw new IllegalArgumentException("min is greater than max: " + min + " " + max);
        }
    }

    @Override
    public String toString() {
        return String.format("%d : %d", min, max);
    }
}
