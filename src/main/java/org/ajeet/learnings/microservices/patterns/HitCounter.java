package org.ajeet.learnings.microservices.patterns;

import java.util.concurrent.atomic.AtomicIntegerArray;

public final class HitCounter {
    private final int minutes;
    private final int maxSeconds;
    private final int[] timestamps;
    private final long[] counts;

    public HitCounter(int minutes) {
        if (minutes <= 0)
            throw new IllegalArgumentException("minutes must be greater than 0.");
        this.minutes = minutes;
        this.maxSeconds = minutes * 60;
        this.timestamps = new int[maxSeconds];
        this.counts = new long[maxSeconds];
    }

    public void hit(int seconds){
        int index = seconds % maxSeconds;
        if (timestamps[index] == seconds)
            ++counts[index];
        else {
            timestamps[index] = seconds;
            counts[index] = 1;
        }
    }

    private long countWithSeconds(int seconds) {
        if(seconds > maxSeconds)
            throw new IllegalArgumentException("Counting only for " + this.minutes);

        long result = 0;
        for (int i=0; i< maxSeconds; i++) {
            if (seconds - timestamps[i] < maxSeconds) {
                result += counts[i];
            }
        }
        return result;
    }

    public long count(int minutes) {
        return countWithSeconds(minutes * 60);
    }

    public static void main(String[] args) {
        AtomicIntegerArray integerArray = new AtomicIntegerArray(10);

        HitCounter counter = new HitCounter(2);
        counter.hit(1 * 60);
        counter.hit(1 * 60);
        counter.hit(1 * 60);

        counter.hit(1 * 60);

        counter.hit(2 * 60);
        counter.hit(2 * 60);
        counter.hit(2 * 60);
        counter.hit(2 * 60);
        counter.hit(2 * 60);
        counter.hit(2 * 60);
        counter.hit(2 * 60);
        counter.hit(2 * 60);

/*
        counter.hit(3 * 60);
        counter.hit(3 * 60);
        counter.hit(3 * 60);
        counter.hit(3 * 60);
*/

        System.out.println(counter.count(1));
     //   System.out.println(counter.count(2));
     //   System.out.println(counter.countWithSeconds(3));
    }
}
