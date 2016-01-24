package vn.khmt.restful;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author LUONG The Nhan
 * @version 0.1
 */
public class ConnectToSQL {

    public static final String ERROR = "Error";
    public static final String NOTMATCH = "NotMatch";
    public static final String SQLSERVER = "sqlserver";
    public static final String SQLSERVERDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String MYSQL = "mysql";
    public static final String MYSQLDRIVER = "com.mysql.jdbc.Driver";
    public static final String POSTGRESQL = "postgresql";
    public static final String POSTGRESQLDRIVER = "org.postgresql.Driver";

    Connection dbConnection = null;

    public ConnectToSQL(String type, String host, String dbname, String user, String pwd) {
        this.dbConnection = getDBConnection(type, host, dbname, user, pwd);
    }

    private Connection getDBConnection(String type, String host, String dbname, String user, String pwd) {
        if (type != null && !type.isEmpty()) {
            try {
                if (type.equalsIgnoreCase(SQLSERVER)) {
                    Class.forName(SQLSERVERDRIVER);
                    dbConnection = DriverManager.getConnection(host + ";database=" + dbname + ";sendStringParametersAsUnicode=true;useUnicode=true;characterEncoding=UTF-8;", user, pwd);
                } else if (type.equalsIgnoreCase(MYSQL)) {
                    Class.forName(MYSQLDRIVER);
                    dbConnection = DriverManager.getConnection(host + "/" + dbname, user, pwd);
                } else if (type.equalsIgnoreCase(POSTGRESQL)) {
                    Class.forName(POSTGRESQLDRIVER);
                    dbConnection = DriverManager.getConnection("jdbc:postgresql://"+host + "/" + dbname + "?sslmode=require", user, pwd);
                }
                return dbConnection;
            } catch (ClassNotFoundException | SQLException ex) {
                  System.out.println("LOI te LE ROI NE");
               // System.err.println(ex.getMessage());
            }
        }
      //  if(dbConnection==null) System.out.println("LOI ROI NE");
        return dbConnection;
    }
    
    public String getUser(int id) {
        try {
            String n="";
            String e="";
            String SQL = "SELECT name,email FROM public.user WHERE id = " + id + ";";
            Statement stmt = this.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.  
            if (rs.next()) {
                n =rs.getString("name");
                e =rs.getString("email");
               // System.out.println("User exists");
                
            }
            return "Name:"+n+" Email:"+e;
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } finally {
            if (this.dbConnection != null) {
                try {
                    this.dbConnection.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
        }
        return "";
  }

    public String insertUser(int id,String username, String password, String email, int status, String name){
        try{
          
            String SQL="INSERT INTO public.user(id,username,password,email,status,name) VALUES ("+id+",'"+username+"','"+password
                    +"','"+email+"',"+status+",'"+name+"')";
            Statement stmt = this.dbConnection.createStatement();
            stmt.executeUpdate(SQL);
           return "Insert thanh cong";
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
        finally{
            if (this.dbConnection != null) {
                try {
                    this.dbConnection.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
        }
       return "Co loi xay ra";
    }
    public String checkUser(String username, String password) {
        try {
            if (username != null && password != null) {
                String SQL = "SELECT u.name, u.status FROM user u WHERE u.username = '" + username + "' AND u.password = '" + password + "';";
                Statement stmt = this.dbConnection.createStatement();
                ResultSet rs = stmt.executeQuery(SQL);

                // Iterate through the data in the result set and display it.  
                if (rs.next()) {
                    if (rs.getInt(2) == 1) {
                        return rs.getString(1);
                    } else {
                        return ERROR;
                    }
                } else {
                    return NOTMATCH;
                }
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } finally {
            if (this.dbConnection != null) {
                try {
                    this.dbConnection.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
        }
        return null;
    }

    private static Timestamp getTimeStampOfDate(Date date) {
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

    public boolean executeSQL(String sql) {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = this.dbConnection.prepareStatement(sql);
            // execute insert SQL stetement
            if (preparedStatement != null) {
                int res = preparedStatement.executeUpdate();
                return res == 1;
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException sqle) {
                    System.err.println(sqle.getMessage());
                }
            }
        }
        return false;
    }

}
