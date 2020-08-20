package org.se2.hausarbeit.model.dao;

import org.postgresql.util.PSQLException;
import org.se2.hausarbeit.process.exception.DatabaseException;
import org.se2.hausarbeit.services.db.DatabaseConnection;
import org.se2.hausarbeit.services.db.QueryContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
    private DatabaseConnection connection;
    private PreparedStatement statement;
    private int index = 1;

    BaseDao() throws DatabaseException {
        this.ensureConnection();
    }

    BaseDao setSQL(String sql) throws DatabaseException {
        try{
            this.statement = this.connection.getPreparedStatement(sql, QueryContext.RETURN_GENERATES_KEYS);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Fehler beim erstellen der Datenbankanfrage");
        }
        return this;
    }
    BaseDao setString(String s) throws DatabaseException {
        try{
            this.statement.setString(index++, s);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Fehler beim Erstellen der Datenbankanfrage");
        }
        return this;
    }
    ResultSet executeQuerry() throws DatabaseException {
        ResultSet result = null;
        try {
            result = this.statement.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Fehler beim Ausführen der Datenbankanfrage");
        }
        finally {
            this.clear();
        }
        return result;
    }
    int executeUpdate() throws DatabaseException {
        return executeUpdate(null);
    }
    int executeUpdate(String idColumn) throws DatabaseException {
        int result = -1;
        try {
            result = this.statement.executeUpdate();
            if(idColumn != null) {
                ResultSet rs = this.statement.getGeneratedKeys();
                if(rs.next()) {
                    result = rs.getInt(idColumn);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Fehler beim Ausführen der Datenbankanfrage");
        }
        finally {
            this.clear();
        }
        return result;
    }

    void clear() {
        try {
            this.statement.close();
            this.statement = null;
            this.index = 1;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ensureConnection() throws DatabaseException {
        try {
            if(this.connection == null || this.connection.connectionIsClosed()) {
                this.connection = new DatabaseConnection();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Keine Verbindung zur Datenbank");
        }
    }
}
