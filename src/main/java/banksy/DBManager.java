package banksy;

import java.io.IOException;

public interface DBManager {
	void createRegistrationTable(String fileName) throws IOException;
}