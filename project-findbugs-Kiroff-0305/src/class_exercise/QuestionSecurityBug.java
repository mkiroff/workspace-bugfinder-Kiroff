package class_exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class QuestionSecurityBug {
   Connection getConnection() throws SQLException {
      Connection connection = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
      } catch (final ClassNotFoundException e) {
         e.printStackTrace();
      }
      connection = DriverManager.getConnection(Util.URL(), Util.ID(), Util.PWD());
      return connection;
   }

   void question1_repaired_Kiroff(final String owner) {
	  PreparedStatement statement = null;
      ResultSet resultSet = null;
      try {
         final String query = "SELECT * FROM myTable WHERE owner = ?";
         statement = getConnection().prepareStatement(query);
         statement.setString(1, owner);
         resultSet = statement.executeQuery(query);
         while (resultSet.next()) {
            System.out.println(resultSet.getString("NAME"));
            System.out.println(resultSet.getString("PHONE"));
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

}
