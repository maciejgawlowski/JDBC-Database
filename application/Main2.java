package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import model.Nauczyciel;
import model.Ocena;
import model.Przedmiot;
import model.RodzajOceny;
import model.Uczen;

public class Main2 {

		public static void main(String[] args) {
		String driverName = "oracle.jdbc.driver.OracleDriver";

		// sprawdŸ czy sterownik jest dostêpny
		try {
			Class c = Class.forName(driverName);
			System.out.println("Pakiet     : " + c.getPackage());
			System.out.println("Nazwa klasy: " + c.getName());
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		System.out.println("Sterownik jest dostêpny.\n");

		Connection connection;
		// pod³¹czanie siê do bazy danych
		try {
			String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
			String user = "xmgawlow";
			String password = "xmgawlow";

			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Autocommit: " + connection.getAutoCommit());
		} catch (SQLException e) {
			System.out.println("Nieudane po³¹czenie z baz¹ danych!");
			e.printStackTrace();
			return;
		}
		System.out.println("Po³¹czono z baz¹ danych.\n");

		DataBaseManager dataBaseManager = new DataBaseManager(connection);

		// usuniêcie istniej¹cych tabel
		dataBaseManager.dropTable("PRZEDMIOT");
		dataBaseManager.dropTable("NAUCZYCIEL");
		dataBaseManager.dropTable("UCZEN");
		dataBaseManager.dropTable("OCENA");
		dataBaseManager.dropTable("OCENIANIE");

	
		// zamkniêcie po³¹czenia z baz¹ danych
		try {
			connection.close();
				} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
