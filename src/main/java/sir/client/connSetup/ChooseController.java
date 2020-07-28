package sir.client.connSetup;


public class ChooseController {

    private ChoosePageService choosePageService;


    public void initialize () {
        choosePageService = new ChoosePageService();
    }




    public void setMysqlConnection() throws Exception {
        choosePageService.setMysqlConnection();
    }


    public void setPostGresConnection() throws Exception {
        choosePageService.setPostGresConnection();
    }


    public void setOracleConnection() throws Exception {
        choosePageService.setOracleConnection();
    }


}
