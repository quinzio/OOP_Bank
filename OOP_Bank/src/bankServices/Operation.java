package bankServices;

public abstract class Operation {
    int date;
    double value;

    public Operation(int d, double v) {
	date = d;
	value = v;
    }

    public int getDate() {
	return date;
    }

    public double getValue() {
	return value;
    }

    public abstract String toString();
}
