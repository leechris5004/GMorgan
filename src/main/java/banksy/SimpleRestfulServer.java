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
                String email = request.queryParams("email");
                String amount = request.queryParams("amount");
                String msg;
                String success = "";
                try {
                    if (db.passwordCheck(email, password)) {
                        msg = String.format("SUCCESS: Transaction successful for user=%s amount=%s", email, amount);
                        success = "true";
                    } else {
                        msg = String.format("FAILURE: user=%s amount=%s was not found", email, amount);
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

        // http://localhost:4567/retrieveUser/1

        get(new Route("/retrieveUser/:id") {
            @Override
            public Object handle(Request request, Response response) {
                logger.info("received get request /retrieveUser/");
                BankUser user = null;
                try {
                    user = userDao.queryForId(request.params(":id"));
                    logger.info(String.format("retrieved user from DB: user=%s", user));
                } catch (SQLException e) {
                    logger.error(String.format("FAILED to retrieved user from DB: %s", e.getMessage()), e);
                }
                if (user != null) {
                    return "User: " + user; // or JSON? :-)
                } else {
                    response.status(404); // 404 Not found
                    return "404: User not found";
                }
            }
        });


        // http://localhost:4567/addUser?username=ron&email=ron@m3.com

        get(new Route("/addUser") {
            @Override
            public Object handle(Request request, Response response) {
                logger.info("received post request /addUser");
                String username = request.queryParams("username");
                String email = request.queryParams("email");
                BankUser user = new BankUser(username, email);
                logger.info("about to create: user=" + user);
                int code;
                String mesg;
                try {
                    userDao.create(user);
                    mesg = String.format("SUCCESS: created new user in DB: user=%s", user);
                    logger.info(mesg);
                    code = 201;
                } catch (SQLException e) {
                    mesg = String.format("FAILED to create new user in DB: user=%s", user);
                    logger.error(mesg, e);
                    code = 500;
                }
                response.status(code);
                return mesg;
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