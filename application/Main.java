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

public class Main {

	private static List<Nauczyciel> teachersList = Arrays.asList(new Nauczyciel(1, "Joanna", "Karwowska"),
			new Nauczyciel(2, "Henryk", "Lwowski"), new Nauczyciel(3, "Anna", "Pilecka"),
			new Nauczyciel(4, "Bogdan", "Krutkowidz"), new Nauczyciel(5, "Marzena", "Bogobojna"),
			new Nauczyciel(6, "Andrzej", "Fokowski"), new Nauczyciel(7, "Roman", "Marudny"),
			new Nauczyciel(8, "Krystian", "Sto³owy"));

	private static List<Uczen> studentsList = Arrays.asList(new Uczen(1, "Maciej", "Dok³adny"),
			new Uczen(2, "Antoni", "Wojenny"), new Uczen(3, "Barbara", "Lipnowska"),
			new Uczen(4, "Arkadiusz", "Pi³karz"), new Uczen(5, "Mieczys³aw", "Lekturowski"),
			new Uczen(6, "Joanna", "Kaczyñska"), new Uczen(7, "Przemys³aw", "Gracz"),
			new Uczen(8, "Bogus³aw", "Linda"));

	private static List<Ocena> grades = Arrays.asList(new Ocena(1, "Niedostateczny", 1),
			new Ocena(2, "Dopuszczaj¹cy", 2), new Ocena(3, "Dostateczny", 3), new Ocena(4, "Dobry", 4),
			new Ocena(5, "Bardzo dobry", 5), new Ocena(6, "Celuj¹cy", 6), new Ocena(7, "Niedostateczny plus", 1.5f),
			new Ocena(8, "Dopuszczaj¹cy plus", 2.5f), new Ocena(9, "Dostateczny plus", 3.5f),
			new Ocena(10, "Dobry plus", 4.5f), new Ocena(11, "Bardzo dobry plus", 5.5f));

	private static List<Przedmiot> subjects = Arrays.asList(new Przedmiot(1, "Jêzyk polski"),
			new Przedmiot(2, "Jêzyk angielski"), new Przedmiot(3, "Jêzyk niemiecki"), new Przedmiot(4, "Matematyka"),
			new Przedmiot(5, "Fizyka"), new Przedmiot(6, "Geografia"), new Przedmiot(7, "Historia"),
			new Przedmiot(8, "Informatyka"));

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

		// tworzenie tabel je¿eli nie istniej¹ w bazie danych
		dataBaseManager.createTable("PRZEDMIOT",
				"CREATE TABLE PRZEDMIOT (idp integer not null, nazwa_przedmiotu varchar2(20) not null)");
		dataBaseManager.createTable("NAUCZYCIEL",
				"CREATE TABLE NAUCZYCIEL (idn integer not null, nazwisko_nauczyciela varchar2(30) not null, imie_nauczyciela varchar2(20) not null)");
		dataBaseManager.createTable("UCZEN",
				"CREATE TABLE UCZEN (idu integer not null, nazwisko_ucznia varchar2(30) not null, imie_ucznia varchar2(20) not null)");
		dataBaseManager.createTable("OCENA",
				"CREATE TABLE OCENA (ido integer not null, wartosc_opisowa varchar2(20) not null, wartosc_numeryczna float not null)");
		dataBaseManager.createTable("OCENIANIE",
				"CREATE TABLE OCENIANIE (idu integer not null, idp integer not null, idn integer not null, ido integer not null, rodzaj_oceny varchar2(1) not null)");
		System.out.println();

		// wype³nienie tabel
		for (Nauczyciel n : teachersList) {
			dataBaseManager.insertIntoTeachers(n);
		}
		System.out.println();
		for (Uczen u : studentsList) {
			dataBaseManager.insertIntoStudents(u);
		}
		System.out.println();
		for (Przedmiot p : subjects) {
			dataBaseManager.insertIntoSubjects(p);
		}
		System.out.println();
		for (Ocena o : grades) {
			dataBaseManager.insertIntoGrades(o);
		}
		System.out.println();

		Scanner scn = new Scanner(System.in);
		System.out.println("\nWpisanie '0' koñczy program");
		// konwersacja
		conversation: while (true) {
			int idn = 0, ido = 0, idp = 0, idu = 0;
			String rodzaj = "";

			boolean iduExists = false;
			while (!iduExists) {
				System.out.println("\nPodaj identyifkator ucznia IDU: ");
				while (!scn.hasNextInt()) {
					System.out.println("Podaj identyifkator ucznia IDU: ");
					scn.next();
				}
				idu = scn.nextInt();
				if (idu == 0)
					break conversation;
				if (dataBaseManager.checkIfStudentExists(idu) == 1) {
					iduExists = true;
				} else if (dataBaseManager.checkIfStudentExists(idu) == 0) {
					iduExists = false;
				} else if (dataBaseManager.checkIfStudentExists(idu) == -1) {
					break conversation;
				}
			}

			boolean idpExists = false;
			while (!idpExists) {
				System.out.println("\nPodaj identyifkator przedmiotu IDP: ");
				while (!scn.hasNextInt()) {
					System.out.println("Podaj identyifkator przedmiotu IDP: ");
					scn.next();
				}
				idp = scn.nextInt();
				if (idp == 0)
					break conversation;
				if (dataBaseManager.checkIfSubjectExists(idp) == 1) {
					idpExists = true;
				} else if (dataBaseManager.checkIfSubjectExists(idp) == 0) {
					idpExists = false;
				} else if (dataBaseManager.checkIfSubjectExists(idp) == -1) {
					break conversation;
				}
			}

			boolean idnExists = false;
			while (!idnExists) {
				System.out.println("\nPodaj identyifkator nauczyciela IDN: ");
				while (!scn.hasNextInt()) {
					System.out.println("Podaj identyifkator nauczyciela IDN: ");
					scn.next();
				}
				idn = scn.nextInt();
				if (idn == 0)
					break conversation;
				if (dataBaseManager.checkIfTeacherExists(idn) == 1) {
					idnExists = true;
				} else if (dataBaseManager.checkIfTeacherExists(idn) == 0) {
					idnExists = false;
				} else if (dataBaseManager.checkIfTeacherExists(idn) == -1) {
					break conversation;
				}
			}

			boolean idoExists = false;
			while (!idoExists) {
				System.out.println("\nPodaj identyifkator oceny IDO: ");
				while (!scn.hasNextInt()) {
					System.out.println("Podaj identyifkator oceny IDO: ");
					scn.next();
				}
				ido = scn.nextInt();
				if (ido == 0)
					break conversation;
				if (dataBaseManager.checkIfGradeExists(ido) == 1) {
					idoExists = true;
				} else if (dataBaseManager.checkIfGradeExists(ido) == 0) {
					idoExists = false;
				} else if (dataBaseManager.checkIfGradeExists(ido) == -1) {
					break conversation;
				}
			}

			boolean dopuszczalnyRodzajOceny = false;
			System.out.println("\nPodaj rodzaj oceny - C lub S: ");
			while (!dopuszczalnyRodzajOceny) {
				rodzaj = scn.nextLine();
				if (rodzaj.equals("0"))
					break conversation;
				rodzaj = rodzaj.toUpperCase();
				for (RodzajOceny ro : RodzajOceny.values()) {
					if (rodzaj.equals(ro.toString()))
						dopuszczalnyRodzajOceny = true;
				}
			}
			System.out.println();

			if (!dataBaseManager.insertIntoOcenianie(idu, idp, idn, ido, rodzaj)) {
				break conversation;
			}
			dataBaseManager.selectOcenianie();
			System.out.println();
		}

		System.out.println("\nKONIEC PROGRAMU");

		// zamkniêcie po³¹czenia z baz¹ danych
		try {
			connection.close();
			scn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
