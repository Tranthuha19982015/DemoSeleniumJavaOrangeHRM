package hatester.common;

import org.testng.annotations.DataProvider;

public class DataProviderFactory {

    @DataProvider(name="login1")
    public Object[][] loginSuccess(){
        return new Object[][]{
                {},
                {}
        };
    }
}
