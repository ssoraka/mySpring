package my.test.reflection;

public abstract class Gear {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    int count;

    public  Gear(){}

    abstract void nextGear();
    abstract void prevGear();

    @Override
    public String toString() {
        return "Gear{" +
                "type='" + type + '\'' +
                ", count=" + count +
                '}';
    }
}
