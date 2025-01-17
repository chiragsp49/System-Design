package cachedebouncing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
	static ConcurrentHashMap<Integer, Employee> cache = new ConcurrentHashMap<Integer, Employee>();
	static AtomicInteger counter = new AtomicInteger(0);
	static int numberOfRequestsForServer = 10;
	static int keyToFetch = 2;

	public static void main(String[] args) {
		fetchWithOutCacheDebounce();
		System.out.println(counter);

	}
	
	public static void fetchWithCacheDebounce() {
		final Semaphore lock1 = new Semaphore(1);
		Runnable task = new Runnable() {
			public void run() {
				try {
					lock1.acquire();
					if (cache.containsKey(keyToFetch)) {
						Employee emp = cache.get(keyToFetch);
					}else {
						System.out.println("Thread going to DB "+ Thread.currentThread().getName());
						counter.getAndAdd(1);
						fetchEmployeeDetails(keyToFetch);
					}
					lock1.release();
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		
		Thread[] threads = new Thread[numberOfRequestsForServer];
		for(int i=0;i<numberOfRequestsForServer;i++) {
			threads[i]=new Thread(task);
			threads[i].start();
		}
		
		for(int i=0;i<numberOfRequestsForServer;i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void fetchWithOutCacheDebounce() {		
		Runnable task = new Runnable() {	
			public void run() {
				try {
					counter.getAndAdd(1);
					if (cache.containsKey(keyToFetch)) {
						Employee emp = cache.get(keyToFetch);
					}else {
						System.out.println("Thread going to DB "+ Thread.currentThread().getName());
						fetchEmployeeDetails(keyToFetch);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		
		Thread[] threads = new Thread[numberOfRequestsForServer];
		for(int i=0;i<numberOfRequestsForServer;i++) {
			threads[i]=new Thread(task);
			threads[i].start();
		}
		
        for (int i = 0; i < numberOfRequestsForServer; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		
	}

	public static Employee fetchEmployeeDetails(int key) throws ClassNotFoundException, SQLException {
		Employee emp = fetchFromDB(key);
		cache.put(key, emp);
		return emp;
		
	}

	public static Employee fetchFromDB(int key) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/office", "root", "admin");
			Statement statement = conn.createStatement();
			ResultSet rst = statement.executeQuery("SELECT * from employees where id = " + key);

			Employee emp = new Employee();
			while (rst.next()) {
				emp.setId(rst.getInt("id"));
				emp.setDept(rst.getString("dept"));
				emp.setName(rst.getString("name"));
			}
			return emp;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			throw new ClassNotFoundException(e.getMessage());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SQLException(e.getMessage());

		}

	}
}
