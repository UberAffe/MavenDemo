package com.revature.employee;

import java.sql.*;

public class Main {
	public static void main(String[] args) {
		String url= "jdbc:postgresql://localhost/dvdrental";
		String username="postgres";
		String password="Eleven28Ten";
		String column="title";
		int pageSize=10;
		Statement stmnt = null;
		PreparedStatement []ps = new PreparedStatement[3];
		try (Connection connection = DriverManager.getConnection(url,username,password); ){
			System.out.println("Connected");
			
//			ps[1]= connection.prepareStatement("select * from student");
//			ps[2]= connection.prepareStatement("Delete From student where id=1");
//			ps[0].setInt(1, 1);
//			ps[0].setString(2, "'Matt'");
//			ps[0].setString(3, "'Keran'");
//			ps[0].setInt(4, 27);
//			ps[0].executeUpdate();
//			ps[0].setInt(1, 2);
//			ps[0].setString(2, "'Sarina'");
//			ps[0].setString(3, "'Keran'");
//			ps[0].setInt(4, 27);
//			ps[0].executeUpdate();
//			ResultSet rows = ps[1].executeQuery();
//			while(rows.next()) {
//				System.out.println("ID-"+rows.getInt(1)+" Name: "+rows.getString(2)+" "+rows.getString(3)+" age: "+rows.getInt(4));
//			}
//			ps[2].executeUpdate();
			stmnt = connection.createStatement();
			stmnt.executeUpdate("create view actor_movies as select "+column+" from actor inner join film_actor on actor.actor_id = film_actor.actor_id inner join film on film_actor.film_id = film.film_id where first_name like 'Penelope';");
			System.out.println("view created");
			ResultSet ct= stmnt.executeQuery("select count("+column+") from actor_movies");
			ct.next();
			int count= (int) Math.ceil(ct.getInt(1)/pageSize);
			for(int i=0;i<count;i++) {
				System.out.println("Page "+(i+1));
				ResultSet rows=stmnt.executeQuery("select * from actor_movies limit "+pageSize+" offset "+i*pageSize);
				while(rows.next()) {
					
					System.out.println(rows.getString(column));
				} 
			}
			stmnt.executeUpdate("drop view actor_movies");
			System.out.println("view deleted");
		}
		catch(SQLException e) {
			System.out.println("failed connection");
		}
	}
}
