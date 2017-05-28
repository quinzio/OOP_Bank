package bankServices;

public class Withdrawal extends Operation {

    public Withdrawal(double value, int date) {
	super(date, value );
    }

    @Override
    public String toString() {
	return String.format("%d, %d-", date, value );
    }

}
