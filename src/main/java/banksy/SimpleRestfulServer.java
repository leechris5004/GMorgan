package banksy;

import static spark.Spark.get;
import static spark.Spark.post;

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

    private ConnectionSource connectionSource;

    public static void main(String[] args) throws SQLException {
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
        // http://localhost:4567/loginUser?email=ron&password=ron@m3.com

        post(new Route("/loginUser") {
            @Override
            public Object handle(Request request, Response response) {
                response.type("application/json");
                logger.info("received post request /loginUser/");
                String email = request.queryParams("email");
                String password = request.queryParams("password");
                int code;
                String mesg = String.format("SUCCESS: Obtained login info user=%s password=%s", email, password);
                logger.info(mesg);
                code = 201;
                response.status(code);
                return mesg;
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