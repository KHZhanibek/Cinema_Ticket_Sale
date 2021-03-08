package com.company;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static boolean check_two_Strings(String a, String b){
		if(a.length() != b.length())
			return false;
		for(int i = 0; i < a.length(); i++){
			if(a.charAt(i) != b.charAt(i))
				return false;
		}
		return true;
	}
    public static void main(String[] args) {
    	Scanner scan = new Scanner(System.in);
    	String connectionUrl = "jdbc:postgresql://localhost:5432/cinema";
		Connection con = null;
		Statement state = null;
		try{

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(connectionUrl, "postgres", "1111");
			state = con.createStatement();
			boolean okk = true;
			while (okk == true) {
				System.out.println("Press 1 if you are customer, if you are administrator print 2 \nif you want exit, press 0");
				int x = scan.nextInt();
				String login, password;
				Customer cust = null;
				if (x == 0) {
					System.exit(0);
				} else {
					if (x == 1)
						System.out.println("Hello, thank you for using this service of buying ticket for cinema");
					System.out.println("Input 1, if you have not registered yet.");
					System.out.println("Input 2, if you have.");
					int is_registred = scan.nextInt();
					if(is_registred == 1){
						String reg_fname, reg_sname, reg_pass, reg_email;
						int reg_age, reg_cash = 5;
						System.out.println("Enter your firstname lastname: ");
						reg_fname = scan.next();
						reg_sname = scan.next();
						System.out.println("Enter your email: ");
						reg_email = scan.next();
						while(Validator.checkEmail(reg_email) == false){
							System.out.println("Something wrong. Retype it");
							reg_email = scan.next();
						}
						System.out.println("Enter your password: ");
						reg_pass = scan.next();
						while(Validator.checkPassword(reg_pass) == false){
							System.out.println("Something wrong. Your password should contain at least 1 uppercase letter, 1 lowercase letter, 1 character and 1 number. Also its size should not be less than 8");
							reg_pass = scan.next();
						}
						System.out.println("Retype your password:");
						String reg_pass_ver = scan.next();
						while(check_two_Strings(reg_pass_ver, reg_pass) == false){
							System.out.println("Your password is wrong");
							reg_pass_ver = scan.next();
						}
						System.out.println("Type your age: ");
						reg_age = scan.nextInt();
						boolean is_succ = false;
						try{
							PreparedStatement add_clinet = con.prepareStatement("INSERT into Customer VALUES (?, ?, ?, ?, ?, ?, ?)");
							Customer custt = new Customer(reg_fname, reg_sname, reg_email, reg_pass, reg_age, reg_cash);
							add_clinet.setInt(1, custt.getId());
							add_clinet.setString(2, custt.getFirstName());
							add_clinet.setString(3, custt.getLastName());
							add_clinet.setString(4, custt.getEmail());
							add_clinet.setString(5, custt.getPassword());
							add_clinet.setInt(6, custt.getAge());
							add_clinet.setInt(7, custt.getCash());
							System.out.println("You Successfully Registered!");
							add_clinet.executeUpdate();
						}
						catch (SQLException e) {
							System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					boolean ok = true;
					int counter = 0;
					String norm_log;

					while (ok == true) {
						counter++;
						if (counter == 10) {
							System.out.println("Too many wrong queries, try again soon");
							System.exit(0);
						}
						if (counter > 1) {
							System.out.println("It is wrong email or password, can you try again");
						}
						System.out.println("Enter login: ");
						login = scan.next();
						System.out.println("Enter password: ");
						password = scan.next();
						PreparedStatement findlog = null;
						if (x == 1) {
							findlog = con.prepareStatement("SELECT * FROM Customer WHERE Customer_Email = ?");
						} else {
							findlog = con.prepareStatement("SELECT * FROM Admin WHERE Customer_Email = ?");
						}
						findlog.setString(1, login);
						ResultSet res = findlog.executeQuery();
						while (res.next()) {
							//System.out.println(res.getString("Customer_password"));
							String sqllogin = res.getString("Customer_password");

							if (check_two_Strings(password, sqllogin)) {
								ok = false;
								cust = new Customer(res.getString("Customer_firstname"), res.getString("Customer_lastname"), res.getString("customer_email"), res.getString("customer_password"), res.getInt("customer_id"), res.getInt("customer_age"), res.getInt("customer_cash"));
								System.out.println("You succesfully entered to your account!");
								break;
							}
						}
					}
					ok = true;
					System.out.println("This is a list of movies which are aviable now, choose or exit");
					ResultSet moviesList = state.executeQuery("SELECT * FROM movie");
					while (moviesList.next()) {
						System.out.println(moviesList.getString("Movie_name"));
					}
				}
				if (x == 1) {

					ResultSet rs = state.executeQuery("SELECT * FROM movie");
					int i = 0;
					ArrayList<Movie>mov_arr = new ArrayList<>();
					while (rs.next()) {
						i++;
						System.out.println(i + ") " + rs.getString("movie_name"));
						Movie movv = new Movie(rs.getString("movie_name"));
						mov_arr.add(movv);
					}
					System.out.println("Please, choose several movies; input the number of movie; input the number of tickets for kids(under 16); input the number of tickets for adults(begins with 17 y.o.)");
					System.out.println("Tickets for kids is 1000, whereas, it is 1500 for adults");
					System.out.println("Print 3 zeros if you are already typed all tickets");
					boolean ok = true;
					ArrayList<Order> ords = new ArrayList<>();
					while (ok == true) {
						int pos = scan.nextInt();
						int kid_ticket = scan.nextInt();
						int adult_ticket = scan.nextInt();
						if(pos == 0 && kid_ticket == 0 && adult_ticket == 0) {
							break;
						}
						Order nord;
						nord = new Order(cust, mov_arr.get(pos), kid_ticket, adult_ticket);
						try {
							PreparedStatement add_order = con.prepareStatement("Insert into orders values (?, ?, ?, ?, ?, ?)");
							add_order.setInt(1, nord.getId());
							add_order.setInt(2, nord.getKids_ticket());
							add_order.setInt(3, nord.getAdult_ticket());
							add_order.setInt(4, nord.getDiscont());
							add_order.setInt(5, pos);
							add_order.setInt(6, nord.getCustId());
							add_order.executeUpdate();
							ords.add(nord);
						}
						catch (SQLException e) {
							System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					System.out.println("We have sales, it is growing if you buy tickets for different movies, at the beginning it 0 nut you can gain 20% sale if you buy 10 tickets of different movies");
					int total = 0;
					for(int k = 0; k < ords.size(); k++){
						System.out.println("Film + " +  ords.get(k).getMoviename() + " Discount: " + ords.get(k).getDiscont() + "    And total wage for this film is:  " + ords.get(k).getTotalWage());
						total += ords.get(k).getTotalWage();
					}
					System.out.println("The total wage is: " + total);

				} else {
					System.out.println("To be honest you are not admin)))");
					System.exit(0);
				}
			}
		}
		catch (Exception e){
			System.out.println("Wrong");
		}
    }
}
