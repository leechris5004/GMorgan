package banksy;
import static spark.Spark.*;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.SQLException;

import org.apache.log4j.Logger;

public class SimpleRestfulServer {

    private static final Logger logger = Logger.getLogger(SimpleRestfulServer.class.getName());
    private static Maria_DBManager db;
    private ConnectionSource connectionSource;

    public static void main(String[] args) throws SQLException {
        db = new Maria_DBManager();
        new SimpleRestfulServer();
    }

    private SimpleRestfulServer() throws SQLException {
        logger.info("initializing ...");
        setUpDB();
        Dao<BankUser, String> userDao = DaoManager.createDao(connectionSource, BankUser.class);
        setUp(userDao);
        TableUtils.createTableIfNotExists(connectionSource, BankUser.class);
        logger.info("initialized");
    }

    private void setUp(Dao<BankUser, String> userDao) {
        // http://localhost:4567/login
        post(new Route("/login") {
            @Override
            public Object handle(Request request, Response response) {
                logger.info("received post request /login");
                String email = request.queryParams("email");
                String password = request.queryParams("password");
                String msg;
                String success = "";
                try {
                    if (db.passwordCheck(email, password)) {
                        msg = String.format("SUCCESS: Login successful for user=%s password=%s", email, password);
                        success = "true";
                    } else {
                        msg = String.format("FAILURE: user=%s password=%s was not found", email, password);
                        success = "false";
                    }
                    response.status(201);
                    logger.info(msg);
                } catch (SQLException e) {
                    msg = String.format("FAILURE: Could not retrieve data from database");
                    response.status(500);
                    logger.error(msg, e);
                }
                return success;
            }
        });

        // http://localhost:4567/add
        post(new Route("/add") {
            @Override
            public Object handle(Request request, Response response) {
                logger.info("received post request /add");
                int account = Integer.parseInt(request.queryParams("account"));
                int amount = Integer.parseInt(request.queryParams("amount"));
                String positive = request.queryParams("positive");
                String msg;
                try {
                    db.changeFunds(account, amount * (positive.equals("true") ? 1 : -1));
                    msg = String.format("SUCCESS: Transaction successful for account=%s amount=%s", account, amount);
                    response.status(201);
                    logger.info(msg);
                } catch (SQLException e) {
                    msg = String.format("FAILURE: Could not change funds");
                    response.status(500);
                    logger.error(msg, e);
                }
                return "true";
            }
        });

        // http://localhost:4567/accounts
        get(new Route("/accounts") {
            @Override
            public Object handle(Request request, Response response) {
                logger.info("received get request /accounts");
                String email = request.queryParams("email");
                String msg;
                String success = "";
                try {
                    success = db.getAccountString(email);
                    msg = String.format("SUCCESS: Obtained account data from email=%s", email);
                    response.status(201);
                    logger.info(msg);
                } catch (SQLException e) {
                    msg = String.format("FAILURE: Could not retrieve data from email=%s", email);
                    response.status(500);
                    logger.error(msg, e);
                }
                return success;
            }
        });

        // http://localhost:4567/transactions
        get(new Route("/transactions") {
            @Override
            public Object handle(Request request, Response response) {
                logger.info("received get request /transactions");
                String email = request.queryParams("email");
                String msg;
                String success = "";
                /*try {
                    //success = db.getUserRecentTransaction(email);
                    msg = String.format("SUCCESS: Obtained transaction data from email=%s", email);
                    response.status(201);
                    logger.info(msg);
                } catch (SQLException e) {
                    msg = String.format("FAILURE: Could not retrieve transaction data from email=%s", email);
                    response.status(500);
                    logger.error(msg, e);
                }*/
                return success;
            }
        });
    }

    private void setUpDB() throws SQLException {
        String databaseUrl = "jdbc:mysql://ec2-52-202-114-229.compute-1.amazonaws.com:3306/banksy";
        connectionSource = new JdbcConnectionSource(databaseUrl);
        ((JdbcConnectionSource) connectionSource).setUsername("banksy");
        ((JdbcConnectionSource) connectionSource).setPassword("password");
    }
}