package simpleFindBugs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class SecurityBug {
	/**
	 * Detect connection with constant DB password.
	 */
	Connection getConnectionConstantDbPasswordWRONG() throws SQLException {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		connection = DriverManager.getConnection( //
				"jdbc:mysql://localhost:3306/myDB", "user", "my-secret-password");
		return connection;
	}

	/**
	 * Correct - Detect connection with constant DB password.
	 */
	Connection getConnectionConstantDbPasswordCORRECT() throws SQLException {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		connection = DriverManager.getConnection(//
				"jdbc:mysql://localhost:3306/myDB", "user", getSecurePassword());
		return connection;
	}

	/**
	 * Here we should fetch and decode the password from a secured resource.
	 */
	static String getSecurePassword() {
		return Util.readPassword(); // Decode the password from a secured resource.
	}

	/**
	 * Detect when this method passes a nonconstant String to an execute method
	 * on an SQL statement. SQL injection vulnerabilities allow an attacker to
	 * inject (or execute) SQL commands within an application. It is one of the
	 * most wide spread and dangerous application vulnerability.
	 */
	void sqlNonconstantStringPassedToExecuteWRONG(final String owner) {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			// If the value of ``owner'' is "someValue or 1=1", it will return 
			// all rows from the table, since WHERE 1=1 is always true.
			final String query = //
					"SELECT answer FROM T_ADVICE WHERE owner= '" + owner + "'"; 
			statement = getConnectionConstantDbPasswordCORRECT().createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				// Iteration.
				System.out.println(resultSet.getString("ANSWER"));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (final SQLException e) {
				}
			}
			if (null != statement) {
				try {
					statement.close();
				} catch (final SQLException e) {
				}
			}
		}
	}

	/**
	 * Correct - Detect when this method passes a nonconstant String to an
	 * execute method on an SQL statement.
	 */
	void sqlNonconstantStringPassedToExecuteCORRECT(final String owner) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			final String query = "SELECT answer FROM T_ADVICE WHERE owner = ?";
			statement = getConnectionConstantDbPasswordCORRECT().prepareStatement(query);
			statement.setString(1, owner);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				// Iteration.
				System.out.println(resultSet.getString("ANSWER"));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (final SQLException e) {
				}
			}
			if (null != statement) {
				try {
					statement.close();
				} catch (final SQLException e) {
				}
			}
		}
	}

	/**
	 * Detect when this method may fail to clean up (close, dispose of) a
	 * stream, database object, or other resource requiring an explicit cleanup
	 * operation
	 */
	void oblUnsatisfiedObligationWRONG(final String owner) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			final String query = "SELECT answer FROM T_ADVICE WHERE owner = ?";
			statement = getConnectionConstantDbPasswordCORRECT().prepareStatement(query);
			statement.setString(1, owner);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				// Iteration.
				System.out.println(resultSet.getString("ANSWER"));
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != statement) {
				try {
					statement.close();
				} catch (final SQLException e) {
				}
			}
		}
	}

	/**
	 * Correct - Detect when this method may fail to clean up (close, dispose
	 * of) a stream, database object, or other resource requiring an explicit
	 * cleanup operation
	 */
	void oblUnsatisfiedObligationCORRECT(final String owner) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			final String query = "SELECT answer FROM T_ADVICE WHERE owner = ?";
			statement = getConnectionConstantDbPasswordCORRECT().prepareStatement(query);
			statement.setString(1, owner);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				// Iteration.
				System.out.println(resultSet.getString("ANSWER"));
			}
		} catch (final SQLException e) {
			System.out.println("   - ERROR:" + e.getMessage());
		} finally {
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (final SQLException e) {
				}
			}
			if (null != statement) {
				try {
					statement.close();
				} catch (final SQLException e) {
				}
			}
		}
	}
}
