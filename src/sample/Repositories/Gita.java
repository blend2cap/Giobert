package sample.Repositories;

public class Gita {
    private String location;
    private Double cost;
    private String month;

    public Gita(String location, Double cost, String month){
        this.location=location;
        this.cost=cost;
        this.month=month;
    }

    public Double getCost() {
        return cost;
    }

    public String getLocation() {
        return location;
    }

    public String getMonth() {
        return month;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
