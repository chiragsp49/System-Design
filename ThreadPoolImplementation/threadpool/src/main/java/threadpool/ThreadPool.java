package threadpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	static int quequeSize = 10;
	static ArrayBlockingQueue<Statement> queque = new ArrayBlockingQueue<Statement>(quequeSize);
	public static void main(String[] args) {
		try {
			connectWithOutThreadPool(); //16131ms
			//addConnectionsToQueque(); // 7863ms
			//connectWithThreadPool();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Statement createConnection() throws SQLException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/office","root","admin");
			Statement statemnet = con.createStatement();
			return statemnet;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ClassNotFoundException(e.toString());
		}
	}
	
	public static void connectWithOutThreadPool() throws ClassNotFoundException, SQLException {
		long start = System.nanoTime();
		for(int i=0;i<100000;i++) {
			Statement statemnet = createConnection();
			statemnet.executeQuery("SELECT * from employees");
			ResultSet rset = statemnet.executeQuery("SELECT * from employees");
			 while(rset.next()) { 
				// System.out.println(rset.getString("name"));
			 }
		}
		long end = System.nanoTime();
		long elapsedTime = end - start;
		System.out.println(TimeUnit.NANOSECONDS.toMillis(elapsedTime)+"ms");
		
	}
	
	
	public static void addConnectionsToQueque() throws ClassNotFoundException, InterruptedException, SQLException {
		for(int i=0;i<quequeSize;i++) {
			queque.put(createConnection());
		}
		
	}
	
	public static void connectWithThreadPool() throws SQLException, InterruptedException {
		long start = System.nanoTime();
		for(int i=0;i<100000;i++) {
			Statement statemnet = queque.poll();
			ResultSet rset = statemnet.executeQuery("SELECT * from employees");
			 while(rset.next()) { 
				// System.out.println(rset.getString("name"));
			 }
			queque.put(statemnet);
		}
		
		long end = System.nanoTime();
		long elapsedTime = end - start;
		System.out.println(TimeUnit.NANOSECONDS.toMillis(elapsedTime)+"ms");

	}
}
