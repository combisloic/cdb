package ui.cli;

import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import model.Company;
import model.CompanyFactory;
import model.Computer;
import model.ComputerFactory;
import ui.Presenter;
import ui.UIManager;

public class CLIManager extends UIManager {
	
	private CLIPresenter cliPresenter;
	private Scanner scanner;
	private String[] menu;
	private boolean shouldShowMenu = false;
	
	private  static final String LIST_COMPUTERS_CMD = "COMPUTERS";
	private  static final String LIST_COMPANIES_CMD = "COMPANIES";
	private  static final String SHOW_COMPUTER_CMD = "SHOW";
	private  static final String CREATE_COMPUTER_CMD = "CREATE";
	private  static final String UPDATE_COMPUTER_CMD = "UPDATE";
	private  static final String DELETE_COMPUTER_CMD = "DELETE";
	private  static final String SHOW_MENU_CMD = "MENU";
	private  static final String QUIT_CMD = "QUIT";
	
	public CLIManager(PropertyChangeListener pcl) {
		// TODO Auto-generated constructor stub
		super(pcl);
		cliPresenter = new CLIPresenter();
		scanner = new Scanner(System.in);
		menu = new String[]{
				"Show menu  : " 		+ SHOW_MENU_CMD,
				"List computers : " 	+ LIST_COMPUTERS_CMD,
				"List companies : " 	+ LIST_COMPANIES_CMD,
				"Show computer : " 		+ SHOW_COMPUTER_CMD,
				"Update computer : " 	+ UPDATE_COMPUTER_CMD,
				"Delete computer : " 	+ DELETE_COMPUTER_CMD,
				"Quit : " 				+ QUIT_CMD
			};
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		cliPresenter.notify("Welcome to the application !!!");
		cliPresenter.notify("Which action do you want to perform ?");
		this.showMenu();
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		cliPresenter.notify("Stopping the application...");
		System.exit(0);
	}
	
	@Override
	public Presenter getPresenter() {
		return cliPresenter;
	}
	
	/////////////////////
	/////////////////////
	//////// CLIPresenter instance methods.
	/////////////////////
	/////////////////////
	
	/**
	 * Display the main menu
	 */
	private void showMenu() {
		shouldShowMenu = false;
		for(String item : menu) {
			cliPresenter.notify(item);
		}
		while(true) {
			cliPresenter.notify("What do you want to do ? ");
			this.handleInput(this.nextLine());
		}
		
	}
	
	/**
	 * @param s String the input typed in the CLI
	 */
	private void handleInput(String s) {
		switch(s) {
		case LIST_COMPUTERS_CMD : this.handleList(Computer.class);break;
		case LIST_COMPANIES_CMD : this.handleList(Company.class);break;
		case CREATE_COMPUTER_CMD : this.handleCreate();break;
		case UPDATE_COMPUTER_CMD : this.handleUpdate();break;
		case SHOW_COMPUTER_CMD : this.handleShow();break;
		case DELETE_COMPUTER_CMD : this.handleDelete();break;
		default : System.out.println(UNKNOWN_ACTION);
		}
	}
	
	/**
	 * Triggers the show computer action
	 */
	private void handleShow() {
		long id = this.requestForId();
		pcs.firePropertyChange(FETCH_COMPUTER, null, id);
	}
	
	/**
	 * Triggers the delete computer action
	 */
	private void handleDelete() {
		long id = this.requestForId();
		pcs.firePropertyChange(DELETE_COMPUTER, null, id);
	}
	
	/**
	 * Triggers the update computer action
	 */
	private void handleUpdate() {
		long id = this.requestForId();
		Computer c = ComputerFactory.getInstance().createWithAll(id, this.requestForName(), null, null, null);
		c.setIntroductionDate(this.requestForDate("Introduction"));
		c.setDiscontinuationDate(this.requestForDate("Discontinuation"));
		c.setCompany(this.requestForCompany());
		this.pcs.firePropertyChange(UPDATE_COMPUTER, null, c);	
	}
	
	/**
	 * Triggers the create computer action
	 */
	private void handleCreate() {
		Computer c = ComputerFactory.getInstance().createWithName(this.requestForName());
		c.setIntroductionDate(this.requestForDate("Introduction"));
		c.setDiscontinuationDate(this.requestForDate("Discontinuation"));
		c.setCompany(this.requestForCompany());
		this.pcs.firePropertyChange(CREATE_COMPUTER, null, c);
	}
	
	/**
	 * Triggers the list computers / companies action
	 * @param c either Computer or Company class
	 */
	private void handleList(Class<?> c) {
		do {
			int page = this.requestForPageNumber();
			if(c == Computer.class) {
				this.pcs.firePropertyChange(LIST_COMPUTERS, null, page);
			}else {
				this.pcs.firePropertyChange(LIST_COMPANIES, null, page);
			}
		}while(!shouldShowMenu);
		this.showMenu();
	}
	
	
	private String nextLine() {
		String input  = scanner.nextLine();
		if(input.equals(QUIT_CMD)) {
			this.stop();
		}
		if(input.equals(SHOW_MENU_CMD)) {
			this.showMenu();
		}
		return input;
	}
	/**
	 * Prompt the user to enter a valid number
	 * @return number entered
	 */
	private Integer requestForPageNumber() {
		Integer number = null;
		boolean numberIsValid = false;
		do {
			cliPresenter.notify("Enter a page number : ");
			String input = this.nextLine();
			try {
				number = Integer.parseInt(input);
				numberIsValid = true;
			}catch(NumberFormatException nfe) {
				cliPresenter.notify("Page must be a number");
			}
		}
		while(!numberIsValid);
		return number;
	}
	
	/**
	 * Prompts the user to enter a valid Id
	 * @return long, the id
	 */
	private long requestForId() {
		Long id = null;
		boolean idIsValid = false;
		do {
			cliPresenter.notify("Enter the <ID> : ");
			String inputID = this.nextLine();
			try {
				id = Long.parseLong(inputID);
				idIsValid = true;
			}catch(NumberFormatException nfe) {
				cliPresenter.notify("<ID> must be a number");
			}
		}
		while(!idIsValid);
		return id;
	}
	
	/**
	 * Prompts the user to enter a non empty name
	 * @return
	 */
	private String requestForName() {
		String name;
		boolean nameIsValid = false;
		do {
			cliPresenter.notify("Enter the name : ");
			name = this.nextLine();
			if(name.equals("")) {
				cliPresenter.notify("Name cannot be empty.");
			}else {
				nameIsValid = true;
			}
		}while(!nameIsValid);
		return name;
	}
	
	/**
	 * Prompts the user to enter a valid date according to the format YYYY-MM-DD
	 */
	private Date requestForDate(String type) {
		Date d = null;
		boolean dateIsValid = false;
		do {
			cliPresenter.notify(type + " date (YYYY-MM-DD) : ");
			String inputDate = this.nextLine();
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
			try {
				if(!inputDate.equals("")) {
					d = df.parse(inputDate);
				}
				dateIsValid = true;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				cliPresenter.notify("Wrong date format.");
				e.printStackTrace();
			}
		} while(!dateIsValid);
		
		return d;
	}

	/**
	 * Prompts the user to enter a valid company id
	 * @return
	 */
	private Company requestForCompany() {
		Company comp = null;
		boolean companyIsValid = false;
		do {
			cliPresenter.notify("Company id : ");
			String companyId = this.nextLine();
			
			try {
				if(!companyId.equals("")) {
					long cId = Long.parseLong(companyId);
					comp = CompanyFactory.getInstance().create(cId);
				}
				companyIsValid = true;
			}
			catch(NumberFormatException nfe) {
				nfe.printStackTrace();
				cliPresenter.notify("<ID> Must be a number.");
			}
		} while(!companyIsValid);
		
		return comp;
	}

}
