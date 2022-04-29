package models;
import java.io.Serializable;

public class Kpi implements Serializable {

    private int ttl;
    private double cost;
    private float chr;

    public Kpi(int ttl, double cost, float chr) {
        this.ttl = ttl;
        this.cost = cost;
        this.chr = chr;
    }

    public int getTtl() {
        return ttl;
    }

    public double getCost() {
        return cost;
    }

    public float getChr() {
        return chr;
    }

    @Override
    public String toString() {
        return "Kpi{" +
                "ttl=" + ttl +
                ", cost=" + cost +
                ", chr=" + chr +
                '}';
    }
}
