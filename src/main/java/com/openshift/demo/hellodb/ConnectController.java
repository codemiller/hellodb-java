package com.openshift.demo.hellodb;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;

@Named
public class ConnectController {

    public final static String ERROR = "Could not connect to database: ";
    public final static String SUCCESS = "Connected successfully \\o/";
    public final static String JNDI_BASE = "java:jboss/datasources/";

    public String testConnection(String datasourceName) {
        try {
            DataSource dataSource = (DataSource) new InitialContext().lookup(JNDI_BASE + datasourceName);
            dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            return ERROR + e.getMessage();
        }
        return SUCCESS;
    }

    public String testMongoDbConnection() {
        String host = System.getenv("EXT_MONGODB_DB_HOST");
        String port = System.getenv("EXT_MONGODB_DB_PORT");
        String username = System.getenv("EXT_MONGODB_DB_USERNAME");
        String password = System.getenv("EXT_MONGODB_DB_PASSWORD");
        String dbName = System.getenv("EXT_MONGODB_DB_NAME");
        Mongo mongo = null;
        if (entryNullOrEmpty(asList(host, port, username, password, dbName))) {
            return ERROR + "Unset environment variable";
        }
        try {
            mongo = new Mongo(host, parseInt(port));
        } catch (IOException e) {
            return ERROR + e.getMessage();
        }
        DB mongoDB = mongo.getDB(dbName);
        try {
            if (!mongoDB.authenticate(username, password.toCharArray())) {
                return ERROR + "Failed to authenticate against database";
            }
        } catch (MongoException e) {
            return ERROR + e.getMessage();
        }
        return SUCCESS;
    }

    private static boolean entryNullOrEmpty(List<String> list) {
        for (String str : list) {
            if (str == null || str.isEmpty()) return true;
        }
        return false;
    }
}
