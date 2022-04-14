import org.jfree.ui.RefineryUtilities;

import java.util.*;
import java.util.function.Function;


public class Main {

    private static final int NUMBER_OF_SIMULATIONS_TO_RUN = 1;
    private static final int MIN_TTL_IN_DAYS = 1;
    private static final int MAX_TTL_IN_DAYS = 30;
    private static final int HOURS_IN_DAY = 24;
    private static final int MIN_ACCEPTABLE_CACHE_HIT_PERCENTAGE = 45;
    private static final float MIN_ACCEPTABLE_COST_IMPROVEMENT_PERCENTAGE = 0.05f;
    private static final int NUMBER_OF_DAYS_IN_WEEK = 7;
    private static ArrayList<Integer> ttlQueue = new ArrayList<>();
    private static int bestTTL = 0;
    private static double bestCost = Double.MAX_VALUE;
    private static ArrayList<Integer> usedTTLs = new ArrayList<>();
    private static HashMap<Integer, Double> ttlCosts = new HashMap<>();
    private static HashMap<Integer, Float> ttlCacheHitRatios = new HashMap<>();
    private static int iter = 300;
    private static Map<Integer, Double> memo = new HashMap<>();
    // order first by cost, then CHR, used to get the lowest cost with acceptable CHR
    private static Queue<AbstractMap.SimpleEntry<Integer, double[]>> pq = new PriorityQueue<>(
            (p1, p2) -> {
                double[] d1 = p1.getValue();
                double[] d2 = p2.getValue();
                if (d1[0] != d2[0]) {
                    return d1[0] - d2[0] > 0 ? 1 : -1;
                }
                return d1[1] - d2[1] > 0 ? 1 : -1;
            });

    public static void main(String[] args) throws Exception {
//        bruteForce();
        qLearning();
//        double ttl = gradientDescent();
//        System.out.println("result is " + (ttl > 0 ? ttl : "no qualified ttl"));
    }

    private static void bruteForce() throws Exception {
        for (int i = 1; i < MAX_TTL_IN_DAYS; i++) {
            for (int j = 0; j < NUMBER_OF_SIMULATIONS_TO_RUN; j++) {
                Simulation simulation = new Simulation(i * HOURS_IN_DAY);
                simulation.run();
                ttlCosts.put(i, simulation.getCost());
                ttlCacheHitRatios.put(i, simulation.getCacheHitRatio());
            }
        }

        System.out.println("TTL cost: $" + ttlCosts);
        System.out.println("TTL Cache Hit Ratios: $" + ttlCacheHitRatios);

        plotChart("Brute Force");
    }

    private static void qLearning() throws Exception {
        int initialTTL = getRandomIntegerInRange(MIN_TTL_IN_DAYS, MAX_TTL_IN_DAYS);

        bestTTL = initialTTL;
        ttlQueue.add(initialTTL);

        while (!ttlQueue.isEmpty()) {
            int ttl = ttlQueue.remove(0);

            usedTTLs.add(ttl);

            // run simulation with ttl as hours
            Simulation simulation = new Simulation(ttl * HOURS_IN_DAY);
            simulation.run();

            double reward;
            double cost = simulation.getCost();
            ttlCosts.put(ttl, cost);
            ttlCacheHitRatios.put(ttl, simulation.getCacheHitRatio());

            if (bestCost == Double.MAX_VALUE) {
                reward = 1;
                bestCost = cost;
                bestTTL = ttl;
            } else if (simulation.getCacheHitRatio() < MIN_ACCEPTABLE_CACHE_HIT_PERCENTAGE) {
                reward = -1000;
            } else if (cost > bestCost) {
                reward = -10;
            } else if (cost > (1 - MIN_ACCEPTABLE_COST_IMPROVEMENT_PERCENTAGE) * bestCost) {
                reward = 0;
            } else {
                reward = bestCost - cost;
                bestCost = cost;
                bestTTL = ttl;
            }

            if (reward > 0) {
                explore(ttl, 1, 1);

                explore(ttl, NUMBER_OF_DAYS_IN_WEEK, NUMBER_OF_DAYS_IN_WEEK);

                explore(ttl, (int) Math.floor((ttl + MAX_TTL_IN_DAYS) / 2.0),
                        (int) Math.floor((ttl + MIN_TTL_IN_DAYS) / 2.0));
            }
        }

        System.out.println("Best TTL in days: " + bestTTL);
        System.out.println("Lowest Cost per 100000 request: $" + String.format("%.2f", bestCost));
        System.out.println("TTL Costs per 100000 request: $" + ttlCosts);
        System.out.println("TTL Cache Hit Ratios: $" + ttlCosts);

        plotChart("Q-Learning");
    }


    private static void explore(int ttl, int stepSizeUp, int StepSizeDown) {
        if (!usedTTLs.contains(ttl + stepSizeUp) && ttl + stepSizeUp <= MAX_TTL_IN_DAYS) {
            ttlQueue.add(ttl + stepSizeUp);
            usedTTLs.add(ttl + stepSizeUp);
        }

        if (!usedTTLs.contains(ttl - StepSizeDown) && ttl - StepSizeDown >= MIN_TTL_IN_DAYS) {
            ttlQueue.add(ttl - StepSizeDown);
            usedTTLs.add(ttl - StepSizeDown);
        }
    }

    private static int getRandomIntegerInRange(int min, int max) {
        return new Random().nextInt(max + 1 - min) + min;
    }

    /**
     * Plot charts for BF and Q-Learning
     */
    private static void plotChart(String windowTitle) {
        ArrayList<double[]> ttlNCost = new ArrayList<>();
        ArrayList<double[]> ttlNCHR = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : ttlCosts.entrySet()) {
            ttlNCost.add(new double[]{entry.getKey(), entry.getValue()});
        }
        for (Map.Entry<Integer, Float> entry : ttlCacheHitRatios.entrySet()) {
            ttlNCHR.add(new double[]{entry.getKey(), entry.getValue()});
        }
        ttlNCost.sort((a, b) -> (int) (a[0] - b[0]));
        ttlNCHR.sort((a, b) -> (int) (a[0] - b[0]));
        Chart costChart = new Chart(windowTitle, "TTL - Cost", "TTL/day", "Cost", ttlNCost);
        Chart chrChart = new Chart(windowTitle, "TTL - Cache Hit Ratio", "TTL/day", "Cache Hit Ratio", ttlNCHR);
        costChart.plot();
        chrChart.plot();
    }

    // function to calculate cost and cache hit ratio
    private static Function<Integer, double[]> f = i -> {
        Simulation simulation = new Simulation(i * HOURS_IN_DAY);
        try {
            simulation.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // getCost instead of getCostPer100000Requests()
        return new double[]{simulation.getCost(), simulation.getCacheHitRatio()};
    };

    // calculate cost and CHR for one ttl
    private static double calculate(int x) {
        //if already existed, get from map
        if (memo.containsKey(x)) {
            return memo.get(x);
        }
        double[] d = f.apply(x);
        double cost = d[0];
        double cacheHitRatio = d[1];
        memo.put(x, cost);
        pq.add(new AbstractMap.SimpleEntry<>(x, new double[]{cost, cacheHitRatio}));
        return cost;
    }

    // calculate derivative between two TTLs
    private static double derivative(int x, int delta) {
        return (calculate(x + delta) - calculate(x - delta)) / (2 * delta);
    }

    //check whether a ttl is validated
    private static boolean inRange(double x) {
        return x <= MAX_TTL_IN_DAYS && x >= MIN_TTL_IN_DAYS;
    }

    // find local minimum of cost
    private static double findLocalMinimum(double initialX) {
        double diff = 10;
        double currentX = initialX;
        double previousX;
        // when we use totalCost as reference, adjust learningRate from 10 to 1(TBD).
        double learningRate = 1;
        int delta = 1;
        double precision = 0.0001;
        for (int i = 0;
             i < iter && diff > precision && inRange(currentX + delta) && inRange(currentX - delta);
             i++) {

            double derivative = derivative((int) Math.floor(currentX), delta);
            previousX = currentX;
            currentX -= learningRate * derivative;
            diff = Math.abs(currentX - previousX);
            System.out.println();
            System.out.println("change: " + learningRate * derivative);
            System.out.println("current x: " + currentX);
            System.out.println("difference: " + diff);
            System.out.println();
        }
        ArrayList<double[]> ttlNCost = new ArrayList<>();
        ArrayList<double[]> ttlNCHR = new ArrayList<>();
        double res = -1;
        while (!pq.isEmpty()) {
            AbstractMap.SimpleEntry<Integer, double[]> pair = pq.poll();
            ttlNCost.add(new double[]{pair.getKey(), pair.getValue()[0]});
            ttlNCHR.add(new double[]{pair.getKey(), pair.getValue()[1]});
            if (pair.getValue()[1] >= MIN_ACCEPTABLE_CACHE_HIT_PERCENTAGE) {
                res = pair.getKey();
                break;
            }
        }
        while (!pq.isEmpty()) {
            AbstractMap.SimpleEntry<Integer, double[]> pair = pq.poll();
            ttlNCost.add(new double[]{pair.getKey(), pair.getValue()[0]});
            ttlNCHR.add(new double[]{pair.getKey(), pair.getValue()[1]});
        }
        ttlNCost.sort((a, b) -> (int) (a[0] - b[0]));
        ttlNCHR.sort((a, b) -> (int) (a[0] - b[0]));
        Chart costChart = new Chart("Gradient Descent", "TTL - Cost", "TTL/day", "Cost", ttlNCost);
        costChart.plot();
        Chart chrChart = new Chart("Gradient Descent", "TTL - Cache Hit Ratio", "TTL/day", "Cache Hit Ratio", ttlNCHR);
        chrChart.plot();
        return res;
    }

    // if there is no acceptable CHR, iterate other time
    private static double gradientDescent() {
        Random rand = new Random();
        Set<Double> failedTry = new HashSet<>();
        for (int i = 0; i < iter; i++) {
            double initialX = rand.nextInt(MAX_TTL_IN_DAYS) + MIN_TTL_IN_DAYS;
            if (failedTry.contains(initialX)) {
                continue;
            }
            double tryOneTtl = findLocalMinimum(initialX);
            if (tryOneTtl != -1) {
                return tryOneTtl;
            }
            failedTry.add(tryOneTtl);
        }
        return -1;
    }

}
