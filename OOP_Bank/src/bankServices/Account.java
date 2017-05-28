package bankServices;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Account {

    private String correntista;
    private float importoIniziale;
    private float balance;
    private int dataApertura;
    private int lastDate;
    private int nConto;
    private List<Operation> operations = new ArrayList<>();

    private static int progressivoNConto = 1;

    public Account(String correntista, float importoIniziale, int dataApertura) {
	super();
	this.correntista = correntista;
	this.importoIniziale = importoIniziale;
	this.dataApertura = dataApertura;
	this.nConto = progressivoNConto++;
	this.deposit(dataApertura, importoIniziale);
    }

    public int getnConto() {
	return nConto;
    }

    public void deposit(int date, float amount) {
	if (date < lastDate)
	    date = lastDate;
	Deposit deposit = new Deposit(amount, date);
	operations.add(deposit);
	lastDate = date;
	balance += amount;
    }

    public void withdrawal(int date, float amount) throws InvalidValue {
	if (date < lastDate)
	    date = lastDate;
	if (amount > balance)
	    throw new InvalidValue();
	Withdrawal withdrawal = new Withdrawal(amount, date);
	operations.add(withdrawal);
	lastDate = date;
	balance -= amount;
    }

    public void delete(int date) throws InvalidValue {
	this.withdrawal(date, balance);
    }

    /**
     * La classe Account implementa il metodo toString(), che restituisce una
     * stringa costituita da: il numero di conto corrente, il nome del
     * correntista, la data dell’ultima operazione, il saldo attuale, tutti
     * separati da una virgola e senza spazi intermedi (Esempio:
     * 4,Paul,35,522.3).
     */

    @Override
    public String toString() {
	return String.format("%d,%s,%d", nConto, correntista, lastDate);
    }

    /**
     * getMovements() restituisce la lista di tutte le operazioni effettuate,
     * ordinate per date decrescenti;
     */
    public List<Operation> getMovements() {
	return operations.stream().sorted(Comparator.comparing(Operation::getDate)).collect(Collectors.toList());
    }

    /**
     * getDeposits() restituisce la lista di tutti i versamenti effettuati,
     * ordinati per importi decrescenti;
     * 
     * @return
     */
    public List<Operation> getDeposits() {
	return operations.stream().filter(o -> {
	    return o instanceof Deposit;
	}).sorted(Comparator.comparing(Operation::getValue, Comparator.reverseOrder())).collect(Collectors.toList());
    }

    /**
     * getWithdrawals() restituisce la lista di tutti i prelievi effettuati,
     * ordinati per importi decrescenti.
     * 
     * @return
     */
    public List<Operation> getWithdrawals() {
	return operations.stream().filter(o -> {
	    return o instanceof Withdrawal;
	}).sorted(Comparator.comparing(Operation::getValue, Comparator.reverseOrder())).collect(Collectors.toList());
    }

    public int getDataApertura() {
	return dataApertura;
    }

    /**
     * Valore depositato sul conto
     */
    public float getBalance() {
	return balance;
    }

    // public boolean dateAfterlastOperation(int date) {
    // return date > lastDate;
    // }

}
