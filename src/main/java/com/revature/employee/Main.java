package com.revature.employee;

import java.sql.*;

public class Main {
	public static void main(String[] args) {
		String url= "jdbc:postgresql://localhost/dvdrental";
		String username="postgres";
		String password="Eleven28Ten";
		String column="title";
		int pageSize=10;
		Statement s1 = null;
		try (Connection connection = DriverManager.getConnection(url,username,password);){
			System.out.println("Connected");
			s1= connection.createStatement();
			ResultSet ct= s1.executeQuery("select count("+column+") from actor inner join film_actor on actor.actor_id = film_actor.actor_id inner join film on film_actor.film_id = film.film_id where first_name like 'Penelope';");
			ct.next();
			int count= (int) Math.ceil(ct.getInt("count")/pageSize);
			for(int i=0;i<count;i++) {
				System.out.println("Page "+(i+1));
				ResultSet rows=s1.executeQuery("select "+column+" from actor inner join film_actor on actor.actor_id = film_actor.actor_id inner join film on film_actor.film_id = film.film_id where first_name like 'Penelope' limit "+pageSize+" offset "+i*pageSize);
				
				while(rows.next()) {
					System.out.println(rows.getString(column));
				} 
			}
		}
		catch(SQLException e) {
			System.out.println("failed connection");
		}
	}
}
