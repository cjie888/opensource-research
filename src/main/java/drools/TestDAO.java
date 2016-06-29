package drools;

import java.util.Collection;

public interface TestDAO {

	Test findByKey(Integer id);

	Collection findAll();

}
