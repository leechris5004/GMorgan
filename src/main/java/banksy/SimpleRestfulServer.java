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
        Dao<User, String> userDao = DaoManager.createDao(connectionSource, User.class);
        setUp(userDao);
        TableUtils.createTableIfNotExists(connectionSource, User.class);
        logger.info("initialized");
    }

    private void setUp(Dao<User, String> userDao) {

        // http://localhost:4567/retrieveUser/1

        get(new Route("/retrieveUser/:id") {
            @Override
            public Object handle(Request request, Response response) {
                logger.info("received get request /retrieveUser/");
                User user = null;
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
                User user = new User(username, email);
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
        String databaseUrl = "jdbc:mysql://localhost/testDB";
        connectionSource = new JdbcConnectionSource(databaseUrl);
        ((JdbcConnectionSource) connectionSource).setUsername("root");
        ((JdbcConnectionSource) connectionSource).setPassword("");
    }
}