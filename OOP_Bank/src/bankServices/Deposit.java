package bankServices;

public class Deposit extends Operation {

    public Deposit(double value, int date) {
	super(date, value);
    }

    @Override
    public String toString() {
	return String.format("%d, %d+", date, value);
    }

}
