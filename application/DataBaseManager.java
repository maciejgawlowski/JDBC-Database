package application;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Nauczyciel;
import model.Ocena;
import model.Przedmiot;
import model.Uczen;

public class DataBaseManager {

	private Connection connection;

	public DataBaseManager(Connection connection) {
		this.connection = connection;
	}

	public void createTable(String tableName, String sql1) {
		try {
			DatabaseMetaData dbm = connection.getMetaData();
			// sprawdz czy jest tabela juz w bazie danych
			ResultSet tables = dbm.getTables(null, null, tableName, null);
			if (tables.next()) {
				System.out.println("Tabela: " + tableName + " istnieje w bazie danych.");
			} else {
				// tworzenie tabeli
				Statement statement = connection.createStatement();
				statement.execute(sql1);
				System.out.println("Utworzono tabelê: " + tableName);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void dropTable(String tableName) {
		try {
			Statement statement = connection.createStatement();
			statement.execute("DROP TABLE " + tableName.toUpperCase());
			System.out.println("Usuniêto tabelê: " + tableName.toUpperCase());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertIntoTeachers(Nauczyciel n) {
		try {
			Statement statement = connection.createStatement();
			String sql1 = "INSERT INTO NAUCZYCIEL VALUES (" + n.getIdn() + ", '" + n.getNazwisko() + "', '"
					+ n.getImie() + "')";
			statement.execute(sql1);
			System.out.println("Wstawiono w tabelê NAUCZYCIEL: " + n.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertIntoStudents(Uczen u) {
		try {
			Statement statement = connection.createStatement();
			String sql1 = "INSERT INTO UCZEN VALUES (" + u.getIdu() + ", '" + u.getNazwisko() + "', '" + u.getImie()
					+ "')";
			statement.execute(sql1);
			System.out.println("Wstawiono w tabelê UCZEN: " + u.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertIntoSubjects(Przedmiot p) {
		try {
			Statement statement = connection.createStatement();
			String sql1 = "INSERT INTO PRZEDMIOT VALUES (" + p.getIdp() + ", '" + p.getNazwaPrzedmiotu() + "')";
			statement.execute(sql1);
			System.out.println("Wstawiono w tabelê PRZEDMIOT: " + p.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertIntoGrades(Ocena o) {
		try {
			Statement statement = connection.createStatement();
			String sql1 = "INSERT INTO OCENA VALUES (" + o.getIdo() + ", '" + o.getWartoscOpisowa() + "', "
					+ o.getWartoscNumeryczna() + ")";
			statement.execute(sql1);
			System.out.println("Wstawiono w tabelê OCENA: " + o.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int checkIfTeacherExists(int idn) {
		try {
			Statement statement = connection.createStatement();
			String sql1 = "SELECT 1 FROM NAUCZYCIEL WHERE idn=" + idn;
			ResultSet rs = statement.executeQuery(sql1);
			if (!rs.next()) {
				System.out.println("Nie ma nauczyciela o identyfikatorze: " + idn);
			} else {
				System.out.println("Wybrano nauczyciela: " + this.selectRowFromTeachers(idn));
				return 1;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int checkIfStudentExists(int idu) {
		try {
			Statement statement = connection.createStatement();
			String sql1 = "SELECT 1 FROM UCZEN WHERE idu=" + idu;
			ResultSet rs = statement.executeQuery(sql1);
			if (!rs.next()) {
				System.out.println("Nie ma ucznia o identyfikatorze: " + idu);
			} else {
				System.out.println("Wybrano ucznia: " + this.selectRowFromStudents(idu));
				return 1;
			}
			rs.close();
		} catch (SQLException e) {			
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int checkIfGradeExists(int ido) {
		try {
			Statement statement = connection.createStatement();
			String sql1 = "SELECT 1 FROM OCENA WHERE ido=" + ido;
			ResultSet rs = statement.executeQuery(sql1);
			if (!rs.next()) {
				System.out.println("Nie ma oceny o identyfikatorze: " + ido);
			} else {
				System.out.println("Wybrano ocenê: " + this.selectRowFromDegrees(ido));
				return 1;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int checkIfSubjectExists(int idp) {
		try {
			Statement statement = connection.createStatement();
			String sql1 = "SELECT 1 FROM PRZEDMIOT WHERE idp=" + idp;
			ResultSet rs = statement.executeQuery(sql1);
			if (!rs.next()) {
				System.out.println("Nie ma przedmiotu o identyfikatorze: " + idp);
			} else {
				System.out.println("Wybrano przedmiot: " + this.selectRowFromSubjects(idp));
				return 1;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public boolean insertIntoOcenianie(int idu, int idp, int idn, int ido, String rodzaj) {
		try {
			Statement statement = connection.createStatement();
			String sql1 = "INSERT INTO OCENIANIE VALUES (" + idu + ", " + idp + ", " + idn + ", " + ido + ", '" + rodzaj
					+ "')";
			statement.execute(sql1);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void selectOcenianie() {
		try {
			System.out.println("Zawartoœæ tabeli OCENIANIE: ");
			Statement statement = connection.createStatement();
			String sql1 = "SELECT * FROM OCENIANIE";
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " | " + rs.getInt(2) + " | " + rs.getInt(3) + " | " + rs.getInt(4)
						+ " | " + rs.getString(5));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String selectRowFromStudents(int idu) {
		String result = "";
		try {
			Statement statement = connection.createStatement();
			String sql1 = "SELECT * FROM UCZEN WHERE idu=" + idu;
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				result = rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String selectRowFromTeachers(int idn) {
		String result = "";
		try {
			Statement statement = connection.createStatement();
			String sql1 = "SELECT * FROM NAUCZYCIEL WHERE idn=" + idn;
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				result = rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String selectRowFromSubjects(int idp) {
		String result = "";
		try {
			Statement statement = connection.createStatement();
			String sql1 = "SELECT * FROM PRZEDMIOT WHERE idp=" + idp;
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				result = rs.getInt(1) + " | " + rs.getString(2);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String selectRowFromDegrees(int ido) {
		String result = "";
		try {
			Statement statement = connection.createStatement();
			String sql1 = "SELECT * FROM OCENA WHERE ido=" + ido;
			ResultSet rs = statement.executeQuery(sql1);
			while (rs.next()) {
				result = rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getFloat(3);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
