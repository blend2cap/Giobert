package sample.Repositories;

public class Gita {
    private String location;
    private Double cost;

    public Gita(String location, Double cost){
        this.location=location;
        this.cost=cost;
    }

    public Double getCost() {
        return cost;
    }

    public String getLocation() {
        return location;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
