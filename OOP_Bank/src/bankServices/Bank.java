package bankServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Bank {
    Map<Integer, Account> accounts = new TreeMap<>();
    Map<Integer, Account> accountsDeleted = new TreeMap<>();

    private String name;

    public Bank(String name) {
	super();
	this.name = name;
    }

    public String getName() {
	return name;
    }

    /***
     * 
     * Il metodo createAccount()permette di aprire un nuovo conto corrente, e
     * riceve tre argomenti: il nome del correntista, la data, l’ammontare del
     * versamento iniziale; il metodo restituisce un intero corrispondente al
     * numero di conto corrente creato (i numeri partono da 1 e vengono
     * incrementati di 1 ad ogni creazione); l’apertura di un conto costituisce
     * la prima operazione su di esso
     * 
     */
    public int createAccount(String correntista, int data, float versamentoIniziale) {
	Account account = new Account(correntista, versamentoIniziale, data);
	accounts.put(account.getnConto(), account);
	return account.getnConto();
    }

    /**
     * 
     * Il metodo getAccount()riceve un numero di conto corrente e restituisce
     * l’oggetto di tipo Account corrispondente; se il numero del conto non è
     * tra quelli creati, solleva l’eccezione InvalidCode
     * 
     * @throws InvalidCode
     * 
     **/
    public Account getAccount(int nConto) throws InvalidCode {
	if (!accounts.containsKey(nConto))
	    throw new InvalidCode();
	return accounts.get(nConto);
    }

    /**
     * Il metodo deposit()permette di effettuare un versamento su di un conto
     * corrente; riceve come argomenti il numero del conto, la data del
     * versamento e l’importo da versare; se il numero del conto non è tra
     * quelli creati, solleva l’eccezione InvalidCode ; se la data indicata
     * precede quella dell’ultima operazione sul conto, il versamento viene
     * effettuato nella stessa data dell’ultima operazione
     * 
     * @throws InvalidCode
     * 
     **/
    public void deposit(int nConto, int date, float amount) throws InvalidCode {
	if (!accounts.containsKey(nConto))
	    throw new InvalidCode();
	Account account = accounts.get(nConto);
	account.deposit(date, amount);
    }

    /**
     * 
     * Il metodo withdraw()permette di effettuare un prelievo da un conto
     * corrente; riceve come argomenti il numero del conto, la data del prelievo
     * e l’importo da prelevare; se il numero del conto non è tra quelli creati,
     * solleva l’eccezione InvalidCode; se l’importo supera l’ammontare del
     * saldo corrente, solleva l’eccezione InvalidValue; se la data indicata
     * precede quella dell’ultima operazione sul conto, il prelievo viene
     * effettuato nella stessa data dell’ultima operazione.
     * 
     **/
    public void withdraw(int nConto, int date, float amount) throws InvalidCode, InvalidValue {
	if (!accounts.containsKey(nConto))
	    throw new InvalidCode();
	Account account = accounts.get(nConto);
	account.withdrawal(date, amount);
    }

    /***
     * Il metodo transfer()permette di effettuare un bonifico da un conto
     * corrente verso un altro conto corrente della stessa banca; riceve come
     * argomenti il numero del conto ordinante, il numero del conto
     * beneficiario, la data e l’importo; se i numeri dei conti non sono tra
     * quelli creati, solleva l’eccezione InvalidCode; se l’importo supera
     * l’ammontare del saldo sul conto ordinante, solleva l’eccezione
     * InvalidValue; le date dell’operazione sul conto beneficiario e sul conto
     * ordinante vengono stabilite con gli stessi criteri dei prelievi e dei
     * versamenti; in ogni caso la data dell’operazione sul conto beneficiario
     * deve essere maggiore o uguale a quella sul conto ordinante.
     * 
     * @throws InvalidValue
     * @throws InvalidCode
     * 
     */
    public void transfer(int nContoOrdinante, int nContoBeneficiario, int date, float amount)
	    throws InvalidCode, InvalidValue {
	withdraw(nContoOrdinante, date, amount);
	deposit(nContoBeneficiario, date, amount);
    }

    /**
     * Il metodo deleteAccount()permette di chiudere un conto corrente
     * prelevando tutto il denaro depositato; riceve come argomenti il numero
     * del conto e la data, restituendo l’Account chiuso; se il numero del conto
     * non è tra quelli creati, solleva l’eccezione InvalidCode; se la data
     * indicata precede quella dell’ultima operazione sul conto, la chiusura
     * viene effettuato nella stessa data dell’ultima operazione.
     * 
     * @throws InvalidCode
     * @throws InvalidValue 
     * 
     **/
    public Account deleteAccount(int nConto, int date) throws InvalidCode, InvalidValue {
	if (!accounts.containsKey(nConto))
	    throw new InvalidCode();
	Account accountToDelete = accounts.remove(nConto);
	accountToDelete.delete(date);
	accountsDeleted.put(nConto, accountToDelete);
	return accountToDelete;
    }

    /**
     * getTotalDeposit() restituisce l’ammontare di tutto il denaro attualmente
     * depositato presso la banca (somma dei saldi di ogni conto corrente);
     * 
     */
    public float getTotalDeposit() {
	double v = accounts.values().stream().collect(Collectors.summingDouble(Account::getBalance));
	return (float) v;
    }

    /**
     * getAccounts() restituisce la lista di tutti i conti correnti attualmente
     * aperti, ordinati per numero di conto crescente;
     */
    public List<Account> getAccounts() {
	List<Account> la = new ArrayList<Account>(accounts.values());
	return la;
    }

    /**
     *
     * getAccountsByBalance() riceve gli estremi di un intervallo di importi, e
     * restituisce la lista dei conti correnti con un saldo attuale compreso
     * nell’intervallo, ordinati per valori di saldo decrescenti;
     * 
     **/
    public List<Account> getAccountsByBalance(final int min, final int max) {
	return accounts.values().stream().filter(a -> a.getBalance() > min).filter(a -> a.getBalance() < max)
		.collect(Collectors.toList());
    }

    /***
     * getPerCentHigher() riceve un importo e restituisce la percentuale dei
     * conti correnti con un saldo attuale non inferiore all’importo dato.
     * 
     **/
    public float getPerCentHigher(float amount) {
	long nHigher = accounts.values().stream().filter(a -> a.getBalance() >= amount).collect(Collectors.counting());
	long tot = accounts.size();
	return 100.0F * nHigher / tot;
    }

}
